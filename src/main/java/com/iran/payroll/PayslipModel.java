package com.iran.payroll;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayslipModel {
    private long baseSalary;
    private long housingAllowance;
    private long workersBenefit;
    private long expertiseBonus;
    private long attractionBonus;
    private long marriageAllowance;
    private long overtimePay;
    private long totalEarnings;
    private long ssoDeduction;
    private long taxDeduction;
    private long netSalary;
    private double overtimeHours;
    private double overtimeHourlyRate;
    private long extraInsurance;

}
