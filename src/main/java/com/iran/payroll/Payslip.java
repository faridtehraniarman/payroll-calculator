package com.iran.payroll;

public class Payslip {
    private final long baseSalary;
    private final long housingAllowance;
    private final long workersBenefit;
    private final long expertiseBonus;
    private final long attractionBonus;
    private final long marriageAllowance;
    private final long overtimePay;
    private final long totalEarnings;
    private final long ssoDeduction;
    private final long taxDeduction;
    private final long extraInsurance;
    private final long netSalary;
    private final double overtimeHours;
    private final double overtimeHourlyRate;

    public Payslip(long baseSalary, long housingAllowance, long workersBenefit,
                   long expertiseBonus, long attractionBonus, long marriageAllowance,
                   long overtimePay, long totalEarnings, long ssoDeduction,
                   long taxDeduction, long extraInsurance, long netSalary,
                   double overtimeHours, double overtimeHourlyRate) {
        this.baseSalary = baseSalary;
        this.housingAllowance = housingAllowance;
        this.workersBenefit = workersBenefit;
        this.expertiseBonus = expertiseBonus;
        this.attractionBonus = attractionBonus;
        this.marriageAllowance = marriageAllowance;
        this.overtimePay = overtimePay;
        this.totalEarnings = totalEarnings;
        this.ssoDeduction = ssoDeduction;
        this.taxDeduction = taxDeduction;
        this.extraInsurance = extraInsurance;
        this.netSalary = netSalary;
        this.overtimeHours = overtimeHours;
        this.overtimeHourlyRate = overtimeHourlyRate;
    }

    public long getBaseSalary() { return baseSalary; }
    public long getHousingAllowance() { return housingAllowance; }
    public long getWorkersBenefit() { return workersBenefit; }
    public long getExpertiseBonus() { return expertiseBonus; }
    public long getAttractionBonus() { return attractionBonus; }
    public long getMarriageAllowance() { return marriageAllowance; }
    public long getOvertimePay() { return overtimePay; }
    public long getTotalEarnings() { return totalEarnings; }
    public long getSsoDeduction() { return ssoDeduction; }
    public long getTaxDeduction() { return taxDeduction; }
    public long getExtraInsurance() { return extraInsurance; }
    public long getNetSalary() { return netSalary; }
    public double getOvertimeHours() { return overtimeHours; }
    public double getOvertimeHourlyRate() { return overtimeHourlyRate; }
}
