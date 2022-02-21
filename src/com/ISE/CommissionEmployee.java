package com.ISE;
import java.util.*;

public class CommissionEmployee extends Employee {
    public int commision;
    public float grossSales;


    public CommissionEmployee() {
        super();
        this.grossSales = 0;
        this.commision = 0;
    }

    public CommissionEmployee(int id, String firstName, String lastName, int commision, float grossSales) {
        super(id, firstName, lastName);
        this.commision = commision;
        this.grossSales = grossSales;
    }

    public int getCommision() {
        return commision;
    }

    public float getGrossSales() {
        return grossSales;
    }

    public void setCommision(int commision) {
        this.commision = commision;
    }

    public void setGrossSales(float grossSales) {
        this.grossSales = grossSales;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof HourlyEmployee &&
                ((CommissionEmployee) o).grossSales == this.grossSales &&
                ((CommissionEmployee) o).commision == this.commision &&
                Objects.equals(((Employee) o), (Employee) this);
    }

    @Override
    public String toString() {
        return
                super.toString()  + ", " +
                        "GrossSales: " + this.grossSales + ", " +
                        "Commision: " + this.commision + ", ";
    }

    @Override
    public float earnings() {
        return (this.commision * this.grossSales) / 100;
    }

}
