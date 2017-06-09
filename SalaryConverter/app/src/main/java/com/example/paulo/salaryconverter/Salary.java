package com.example.paulo.salaryconverter;

/**
 * Created by kaorihirata on 2017-06-07.
 */

public class Salary {
    Country salaryCountry;
    CharSequence AverageSalary;
    Job salaryJobName;

    public Salary(Country salaryCountry, CharSequence averageSalary, Job salaryJobName) {
        this.salaryCountry = salaryCountry;
        AverageSalary = averageSalary;
        this.salaryJobName = salaryJobName;
    }

    public CharSequence getAverageSalary() {
        return AverageSalary;
    }

    public void setAverageSalary(CharSequence averageSalary) {
        AverageSalary = averageSalary;
    }

    public Country getSalaryCountry() {
        return salaryCountry;
    }

    public void setSalaryCountry(Country salaryCountry) {
        this.salaryCountry = salaryCountry;
    }

    public Job getSalaryJobName() {
        return salaryJobName;
    }

    public void setSalaryJobName(Job salaryJobName) {
        this.salaryJobName = salaryJobName;
    }
}
