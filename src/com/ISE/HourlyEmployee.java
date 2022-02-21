package com.ISE;
import java.util.*;


public class HourlyEmployee extends Employee {
    protected int hours;
    protected float wage;

    public HourlyEmployee() {
        super();
        this.hours = 0;
        this.wage = 0;
    }

    public HourlyEmployee(int id, String firstName, String lastName, int hours, float wage) {
        super(id, firstName, lastName);
        this.hours = hours;
        this.wage = wage;
    }

    public int getHours() {
        return hours;
    }

    public float getWage() {
        return wage;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setWage() {
        this.wage = wage;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof HourlyEmployee &&
                ((HourlyEmployee) o).wage == this.wage &&
                ((HourlyEmployee) o).hours == this.hours &&
                Objects.equals(((Employee) o), (Employee) this);
    }

    @Override
    public String toString() {
        return
                super.toString() + ", " +
                        "Wage: " + this.wage + ", " +
                        "Hours: " + this.hours + ", ";
    }

    @Override
    public float earnings() {
        return (this.hours * this.wage);
    }

}
