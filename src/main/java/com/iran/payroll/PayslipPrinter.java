package com.iran.payroll;

public class PayslipPrinter {
    public void print(Payslip p) {
        System.out.println("\n========================================");
        System.out.println("          فیش حقوقی (ریال) 1404           ");
        System.out.println("========================================");

        System.out.println("         ** بخش دریافتی‌ها **           ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %-16s |\n", "شرح مزایا", "مبلغ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %,16d |\n", "1. حقوق پایه", p.getBaseSalary());
        System.out.printf("| %-20s | %,16d |\n", "2. حق مسکن", p.getHousingAllowance());
        System.out.printf("| %-20s | %,16d |\n", "3. بن کارگری/خواروبار", p.getWorkersBenefit());
        System.out.printf("| %-20s | %,16d |\n", "4. فوق العاده تخصص", p.getExpertiseBonus());
        System.out.printf("| %-20s | %,16d |\n", "5. حق جذب", p.getAttractionBonus());
        System.out.printf("| %-20s | %,16d |\n", "6. حق تاهل", p.getMarriageAllowance());
        System.out.printf("| %-20s | %,16d |\n", "7. اضافه‌کاری", p.getOvertimePay());
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %,16d |\n", "جمع ناخالص دریافتی", p.getTotalEarnings());
        System.out.println("----------------------------------------");

        System.out.println("\n          ** بخش کسورات **             ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %-16s |\n", "شرح کسورات", "مبلغ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %,16d |\n", "1. بیمه تامین اجتماعی (7%)", p.getSsoDeduction());
        System.out.printf("| %-20s | %,16d |\n", "2. مالیات بر درآمد", p.getTaxDeduction());
        System.out.printf("| %-20s | %,16d |\n", "3. بیمه تکمیلی", p.getExtraInsurance());
        System.out.println("----------------------------------------");
        long totalDeductions = p.getSsoDeduction() + p.getTaxDeduction() + p.getExtraInsurance();
        System.out.printf("| %-20s | %,16d |\n", "جمع کل کسورات", totalDeductions);
        System.out.println("----------------------------------------");

        System.out.printf("\n| %-20s | %,16d |\n", "** حقوق خالص پرداختی **", p.getNetSalary());
        System.out.println("========================================");

        System.out.println("\n---------- جزئیات محاسبات ----------");
        System.out.printf("ساعات اضافه‌کاری: %.2f ساعت\n", p.getOvertimeHours());
        System.out.printf("نرخ ساعتی پایه (مبنای قانون کار): %,.2f ریال\n", Constants.HOURLY_WAGE_RATE);
        System.out.printf("نرخ ساعتی اضافه‌کاری (با ضریب 1.4): %,.2f ریال\n", p.getOvertimeHourlyRate());
        System.out.printf("معافیت ماهانه مالیاتی: %,d ریال\n", Constants.TAX_EXEMPTION_MONTHLY);
        System.out.println("------------------------------------");
    }
}