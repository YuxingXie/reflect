package com.lingyun.generics;

public class Employee implements Comparable<Employee>{
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(Employee o) {
        if (salary>o.salary) return 1;
        if (salary<o.salary) return -1;
        return 0;
    }
}
