package com.company;

public class Project {
    private String name;
    private int capacity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                '}'+'\n';
    }
}
