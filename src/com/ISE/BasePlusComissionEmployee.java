package com.ISE;
import java.util.*;


public class BasePlusComissionEmployee extends CommissionEmployee {
    public float baseSalary;

    public BasePlusComissionEmployee() {
        super();
        this.baseSalary = 0;
    }

    public BasePlusComissionEmployee(int id, String firstName, String lastName,
                                      int commision, float grossSales, float baseSalary) {
        super(id, firstName, lastName, commision, grossSales);
        this.baseSalary = baseSalary;

    }

    public float getBaseSalary() {
        return this.baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof BasePlusComissionEmployee &&
                ((BasePlusComissionEmployee) o).baseSalary == this.baseSalary &&
                Objects.equals(((CommissionEmployee) o), (CommissionEmployee) this);
    }

    @Override
    public String toString() {
        return
                super.toString()  + ", " +
                        "BaseSalary: " + this.baseSalary + ", ";
    }

    @Override
    public float earnings() {
        return super.earnings() + baseSalary;
    }
}
