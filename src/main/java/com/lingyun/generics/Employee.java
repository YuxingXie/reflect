package com.lingyun.generics;

public class Employee implements Comparable<Employee>{

    private String name;
    private double salary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    public static void printBuddies(Pair<Employee> p){
        Employee first=p.getFirst();
        Employee second=p.getSecond();
        System.out.println(first.getName()+ " and "+second.getName());
    }
    public static void main(String[] args){
        Pair<Manager> managerPair=new Pair<>();
//        printBuddies(managerPair);//error
    }
}
