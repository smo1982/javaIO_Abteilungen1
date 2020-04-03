package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args)  {
        //habe ein department erstellt(firma) wo ich dann alles unterordnen möchte
        Person chef = new Person("iBinDaChef");
        Department company = new Department("Company" , chef);
        File file = new File("C:\\Users\\DCV\\Desktop\\Abteilungen1.txt");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        //counter um die erste erklärungszeile zu ignorieren
            int counter = 0;
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (counter != 0) {
                    String[] splittedValues = line.split(";");
                    Person newPerson = new Person(splittedValues[0].trim());
                    String dep = splittedValues[1].trim();
                    String parentDep = "";
                //versuchen 3. textteil zuzuweisen falls es einen gibt
                    try {
                        parentDep = splittedValues[2].trim();
                    } catch (ArrayIndexOutOfBoundsException ex) {

                    }
                    Department newDep;
                    //checken ob es schon ein department mit diesem namen gibt
                    // -gedanke war mit rekursion alle unterabteilungen durchzugehen und den namen zu vergleichen falls
                    //      es diesen schon gibt nur den Mitarbeiter hinzufügen
                    // - jedoch mag das nicht so wie ich mir das gedacht habe
                    Boolean checkDepExist = equalsSupDep(dep, company, newPerson);
                    // wenn es das unterdepartment noch nicht gibt dann eines erstellen
                    if (!checkDepExist) {
                        newDep = new Department(dep, newPerson);
                    } else {
                        counter++;
                        continue;
                    }

                //checken ob es schon unterdepartments gibt
                    if (company.supDep.size() != 0){
                        checkSupDep(company, parentDep, newDep);
                    } else {
                        company.supDep.add(newDep);
                    }
                }
                counter++;
            }

            printSubDep(company,"");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
//todo kommt immer false heraus - da ich nur die aktuelle schleife abbrechen kann
    public static Boolean equalsSupDep(String text, Department department, Person person) {
        Boolean existDep = false;
        for (Department i : department.supDep) {
            if (i.description.equalsIgnoreCase(text)) {
                i.employers.add(person);
                existDep = true;
                break;
            } else {
                if (i.supDep.size() == 0) {
                   break;
                } else {
                    equalsSupDep(text, i, person);
                }
            }
        }
        return existDep;
    }


    public static void checkSupDep(Department company, String parentDep, Department newDep) {
        for (Department i : company.supDep) {
            if (i.description.equalsIgnoreCase(parentDep)) {
                i.supDep.add(newDep);
                break;
            } else {
                checkSupDep(i,parentDep,newDep);
            }

        }
    }

    public static void printSubDep(Department department, String tabstring) {
        tabstring += "\t";
        System.out.println(tabstring + department.description + ":" + department.employers);
        for (Department i : department.supDep) {
            printSubDep(i, tabstring);
        }


    }
}
