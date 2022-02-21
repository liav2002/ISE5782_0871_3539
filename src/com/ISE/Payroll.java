package com.ISE;
import java.util.*;

public class Payroll {
    public static void main(String[] args) {
        Employee[] employees = new Employee[3];
        employees[0] = new HourlyEmployee(1, "Bob", "Levi", 5, 5);
        employees[1] = new CommissionEmployee(2, "Dan", "Silven", 7, 4);
        employees[2] = new BasePlusComissionEmployee(3, "Yossi", "Cohen", 5,
                (float) 2.2, (float) 3.3);

        for (Employee employee : employees) {
            System.out.println(employee);
            if (employee instanceof BasePlusComissionEmployee) {
                System.out.println(employee.earnings() * 1.1);
            } else {
                System.out.println(employee.earnings());
            }
        }
    }

}
