package com.iran.payroll;


import java.util.Scanner;

public class PayrollApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- محاسبه گر فیش حقوقی (سال 1404) ---");
        System.out.print("1. حقوق پایه ماهانه (ریال): ");
        long baseSalary = scanner.nextLong();

        System.out.print("2. ساعات اضافه کاری در ماه: ");
        double overtimeHours = scanner.nextDouble();

        System.out.print("3. فوق العاده تخصص (ریال، در صورت عدم وجود وارد 0 کنید): ");
        long expertiseBonus = scanner.nextLong();

        System.out.print("4. حق جذب (ریال، در صورت عدم وجود وارد 0 کنید): ");
        long attractionBonus = scanner.nextLong();

        System.out.print("5. حق تاهل (ریال، در صورت عدم وجود وارد 0 کنید): ");
        long marriageAllowance = scanner.nextLong();

        EmployeeInput input = new EmployeeInput(baseSalary, overtimeHours, expertiseBonus, attractionBonus, marriageAllowance);

        TaxCalculator taxCalc = new ProgressiveTaxCalculator();
        PayrollCalculator calculator = new PayrollCalculator(taxCalc);
        Payslip payslip = calculator.calculate(input);

        PayslipPrinter printer = new PayslipPrinter();
        printer.print(payslip);

        scanner.close();
    }
}
