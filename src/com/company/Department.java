package com.company;

import java.util.ArrayList;

public class Department {
    String description;
    ArrayList<Department> supDep = new ArrayList<>();
    ArrayList<Person> employers = new ArrayList<>();

    public Department(String name, Person employer) {
        this.description = name;
        this.employers.add(employer);
    }

    @Override
    public String toString() {
        return "Department{" +
                "description='" + description + '\'' +
                ", supDep=" + supDep +
                ", employers=" + employers +
                '}';
    }
}
