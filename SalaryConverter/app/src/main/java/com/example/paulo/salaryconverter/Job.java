package com.example.paulo.salaryconverter;

/**
 * Created by kaorihirata on 2017-06-07.
 */

public class Job {
    private String jobName;
    private Country jobCountry;
    private String salary;

    public Country getJobCountry() {
        return jobCountry;
    }

    public void setJobCountry(Country jobCountry) {
        this.jobCountry = jobCountry;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Job(String jobName){
        this.jobName=jobName;

    }
}
