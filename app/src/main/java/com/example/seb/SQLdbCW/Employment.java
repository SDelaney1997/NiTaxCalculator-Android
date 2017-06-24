package com.example.seb.SQLdbCW;

public class Employment {
    private String niNumber;
    private String jobNature;
    private String employer;
    private double grossWeekly;
    private double niPaid;

    public Employment(String niNumber, String jobNature, String employer,
                      double grossWeekly) {
        this.niNumber = niNumber;
        this.jobNature = jobNature;
        this.employer = employer;
        this.grossWeekly = grossWeekly;
    }
    public String getNiNumber() {
        return niNumber;
    }

    public String getJobNature() {
        return jobNature;
    }

    public String getEmployer() { return employer; }

    public double getGrossWeekly() { return grossWeekly; }

    public double niPayable(double weeklyEarnings){
        if (weeklyEarnings> 112){   //LEL
            if (weeklyEarnings > 155 && weeklyEarnings <= 827){ //PT
                niPaid= (weeklyEarnings - 155) * 0.12;
            }
            else if(weeklyEarnings>827){    //UEL
                niPaid= ((827 - 155) * 0.12) + ((weeklyEarnings - 827) * 0.02);
            }
            else{ niPaid= 0; }
        }
        else{ niPaid=0; }
        return Double.parseDouble(String.format("%.2f",niPaid));
    }

    @Override
    public String toString() {
        return "Job: "+jobNature + " - "+ employer +
                "\nPay(£): " + grossWeekly +"   NIC Payable(£): " + niPayable(grossWeekly);
    }
}
