package com.ISE;
import java.util.*;


public abstract class Employee {
    protected String firstName;
    protected String lastName;
    protected int id;

    public Employee() {
        this.id = 0;
        this.firstName = "plony";
        this.lastName = "almony";
    }

    public Employee(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof Employee &&
                ((Employee) o).id == this.id &&
                Objects.equals(((Employee) o).firstName, this.firstName) &&
                Objects.equals(((Employee) o).lastName, this.lastName);
    }

    @Override
    public String toString() {
        return
                "Id: " + this.id + ", " +
                        "FirstName: " + this.firstName + ", " +
                        "LastName: " + this.lastName + ", ";
    }

    public abstract float earnings();
}
