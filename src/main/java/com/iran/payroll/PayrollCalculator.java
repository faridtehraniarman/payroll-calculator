package com.iran.payroll;

public class PayrollCalculator {
    private final TaxCalculator taxCalculator;
    private final long extraInsurance = 24_499_890L; // kept as example constant per original code

    public PayrollCalculator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }

    public Payslip calculate(EmployeeInput input) {
        long base = input.getBaseSalary();
        long expertise = input.getExpertiseBonus();
        long attraction = input.getAttractionBonus();
        long marriage = input.getMarriageAllowance();
        double overtimeHours = input.getOvertimeHours();

        double hourlyRate = (base + expertise + attraction) / Constants.WORK_HOURS_PER_MONTH;
        long overtimePay = (long) (hourlyRate * Constants.OVERTIME_MULTIPLIER * overtimeHours);

        long totalEarnings = base + Constants.HOUSING_ALLOWANCE + Constants.WORKERS_BENEFIT
                + expertise + attraction + marriage + overtimePay;

        long insuredWage = base + Constants.HOUSING_ALLOWANCE + Constants.WORKERS_BENEFIT
                + expertise + attraction + marriage + overtimePay;

        long ssoDeduction = (long) (insuredWage * Constants.SSO_EMPLOYEE_RATE);

        long taxable = insuredWage - ssoDeduction - extraInsurance;
        if (taxable < 0) taxable = 0;

        long taxDeduction = taxCalculator.calculateTax(taxable);

        long totalDeductions = ssoDeduction + taxDeduction + extraInsurance;
        long net = totalEarnings - totalDeductions;

        return new Payslip(
                base,
                Constants.HOUSING_ALLOWANCE,
                Constants.WORKERS_BENEFIT,
                expertise,
                attraction,
                marriage,
                overtimePay,
                totalEarnings,
                ssoDeduction,
                taxDeduction,
                extraInsurance,
                net,
                overtimeHours,
                hourlyRate * Constants.OVERTIME_MULTIPLIER // overtime hourly rate
        );
    }
}
