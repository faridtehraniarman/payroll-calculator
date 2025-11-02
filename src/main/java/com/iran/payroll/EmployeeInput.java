package com.iran.payroll;

public class EmployeeInput {
    private final long baseSalary;
    private final double overtimeHours;
    private final long expertiseBonus;
    private final long attractionBonus;
    private final long marriageAllowance;

    public EmployeeInput(long baseSalary, double overtimeHours, long expertiseBonus,
                         long attractionBonus, long marriageAllowance) {
        this.baseSalary = baseSalary;
        this.overtimeHours = overtimeHours;
        this.expertiseBonus = expertiseBonus;
        this.attractionBonus = attractionBonus;
        this.marriageAllowance = marriageAllowance;
    }

    public long getBaseSalary() { return baseSalary; }
    public double getOvertimeHours() { return overtimeHours; }
    public long getExpertiseBonus() { return expertiseBonus; }
    public long getAttractionBonus() { return attractionBonus; }
    public long getMarriageAllowance() { return marriageAllowance; }
}
