package com.iran.payroll;

import java.util.Scanner;

public class NewCalculator {
    // --- ثابت‌های محاسباتی و نرخ‌های قانونی (سال 1404) ---
    // مبالغ بر اساس ریال است.
    private static final long MIN_DAILY_WAGE = 3463656L; // حداقل دستمزد روزانه 1404 [1]
    private static final double DAILY_WORK_HOURS = 7.33; // ساعات کار موظفی روزانه (تقریباً 44 ساعت در 6 روز) [2]
    private static final double HOURLY_WAGE_RATE = MIN_DAILY_WAGE / DAILY_WORK_HOURS;
    private static final double OVERTIME_MULTIPLIER = 1.4; // ضریب اضافه‌کاری [3]
    private static final long HOUSING_ALLOWANCE = 9000000L; // حق مسکن 1404 [1]
    private static final double WORK_HOURS_PER_MONTH = 192.0;   // ساعت کار موظفی در ماه
    private static final long WORKERS_BENEFIT = 22000000L; // بن کارگری/خواروبار 1404 [1]
    private static final long TAX_EXEMPTION_MONTHLY = 240000000L; // سقف معافیت مالیاتی ماهانه (288,000,000 ریال سالانه / 12) [4, 5]
    private static final double SSO_EMPLOYEE_RATE = 0.07; // سهم کارمند از بیمه تامین اجتماعی (7٪) [6]


    // --- ثابت‌های مالیات پلکانی (ماهانه، بر حسب ریال) ---
    // پلکان‌های مالیاتی رسمی سال 1404
    private static final long BRACKET1_START = 240_000_001;
    private static final long BRACKET2_START = 300_000_001;
    private static final long BRACKET3_START = 380_000_001;
    private static final long BRACKET4_START = 500_000_001;
    private static final long BRACKET5_START = 666_667_001;

    private static final long BRACKET1_MAX = 300_000_000;
    private static final long BRACKET2_MAX = 380_000_000;
    private static final long BRACKET3_MAX = 500_000_000;
    private static final long BRACKET4_MAX = 666_666_670;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- دریافت ورودی از کاربر ---
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


        System.out.println("----------------------------------------");

        // --- محاسبات ---

        // 1. محاسبه دستمزد ساعتی اضافه‌کاری
        double hourlyRate = (baseSalary + expertiseBonus +  attractionBonus) / WORK_HOURS_PER_MONTH;
        long overtimePay = (long) (hourlyRate * OVERTIME_MULTIPLIER * overtimeHours);

        // 2. محاسبه کل دریافتی‌ها (حقوق ناخالص)
        long totalEarnings = baseSalary + HOUSING_ALLOWANCE + WORKERS_BENEFIT +
                expertiseBonus + attractionBonus + marriageAllowance + overtimePay;

        // 3. محاسبه کسورات

        // الف) محاسبه حق بیمه سهم کارمند (7٪ از حقوق و مزایای مشمول بیمه)
        // فرض بر این است که کلیه اقلام فوق مشمول بیمه هستند.[7, 8]
        long insuredWage = baseSalary + HOUSING_ALLOWANCE + WORKERS_BENEFIT +
                expertiseBonus + attractionBonus + marriageAllowance + overtimePay;

        // در این مثال، سقف بیمه لحاظ نشده است. در محاسبات واقعی باید سقف بیمه ماهانه (ماده 35 قانون تأمین اجتماعی) کنترل شود.[9]
        long ssoDeduction = (long) (insuredWage * SSO_EMPLOYEE_RATE);

        long extraInsurance = 24499890L;
        // ب) محاسبه مالیات بر درآمد حقوق (پلکانی)
        long taxableIncome = insuredWage; // کلیه اقلام حقوقی مشمول مالیات هستند.[10]
        long taxDeduction = calculateProgressiveTax(taxableIncome - ssoDeduction - extraInsurance);

        // 4. محاسبه حقوق خالص (خالص پرداختی)
        long totalDeductions = ssoDeduction + taxDeduction + extraInsurance;
        long netSalary = totalEarnings - totalDeductions;

        // --- نمایش فیش حقوقی ---
        displayPayslip(baseSalary, HOUSING_ALLOWANCE, WORKERS_BENEFIT, expertiseBonus,
                attractionBonus, marriageAllowance, overtimePay,
                totalEarnings, ssoDeduction, taxDeduction, netSalary,
                overtimeHours, overtimePay, extraInsurance);

        scanner.close();
    }

    public static PayslipModel calculate( long baseSalary,double overtimeHours, long expertiseBonus,
                                  long attractionBonus, long marriageAllowance) {


        // --- محاسبات ---

        // 1. محاسبه دستمزد ساعتی اضافه‌کاری
        double hourlyRate = (baseSalary + expertiseBonus +  attractionBonus) / WORK_HOURS_PER_MONTH;
        long overtimePay = (long) (hourlyRate * OVERTIME_MULTIPLIER * overtimeHours);

        // 2. محاسبه کل دریافتی‌ها (حقوق ناخالص)
        long totalEarnings = baseSalary + HOUSING_ALLOWANCE + WORKERS_BENEFIT +
                expertiseBonus + attractionBonus + marriageAllowance + overtimePay;

        // 3. محاسبه کسورات

        // الف) محاسبه حق بیمه سهم کارمند (7٪ از حقوق و مزایای مشمول بیمه)
        // فرض بر این است که کلیه اقلام فوق مشمول بیمه هستند.[7, 8]
        long insuredWage = baseSalary + HOUSING_ALLOWANCE + WORKERS_BENEFIT +
                expertiseBonus + attractionBonus + marriageAllowance + overtimePay;

        // در این مثال، سقف بیمه لحاظ نشده است. در محاسبات واقعی باید سقف بیمه ماهانه (ماده 35 قانون تأمین اجتماعی) کنترل شود.[9]
        long ssoDeduction = (long) (insuredWage * SSO_EMPLOYEE_RATE);

        long extraInsurance = 24499890L;
        // ب) محاسبه مالیات بر درآمد حقوق (پلکانی)
        long taxableIncome = insuredWage; // کلیه اقلام حقوقی مشمول مالیات هستند.[10]
        long taxDeduction = calculateProgressiveTax(taxableIncome - ssoDeduction - extraInsurance);

        // 4. محاسبه حقوق خالص (خالص پرداختی)
        long totalDeductions = ssoDeduction + taxDeduction + extraInsurance;
        long netSalary = totalEarnings - totalDeductions;

        // --- نمایش فیش حقوقی ---
//        displayPayslip(baseSalary, HOUSING_ALLOWANCE, WORKERS_BENEFIT, expertiseBonus,
//                attractionBonus, marriageAllowance, overtimePay,
//                totalEarnings, ssoDeduction, taxDeduction, netSalary,
//                overtimeHours, overtimePay, extraInsurance);

        return new PayslipModel(baseSalary, HOUSING_ALLOWANCE, WORKERS_BENEFIT, expertiseBonus,
                attractionBonus, marriageAllowance, overtimePay,
                totalEarnings, ssoDeduction, taxDeduction, netSalary,
                overtimeHours, overtimePay, extraInsurance);

    }


    private static long calculateProgressiveTax(long incomeForTax) {
        if (incomeForTax <= TAX_EXEMPTION_MONTHLY) {
            return 0; // معاف از مالیات
        }

        long totalTax = 0;
        long remainingTaxable = incomeForTax;

        // 1. معافیت (0%)
        long exemptAmount = Math.min(remainingTaxable, TAX_EXEMPTION_MONTHLY);
        remainingTaxable -= exemptAmount;

        // 2. پلکان 10% (از 240,000,000 تا 300,000,000 ریال)
        long bracket1Limit = BRACKET1_MAX - TAX_EXEMPTION_MONTHLY; // 60,000,000
        if (remainingTaxable > 0) {
            long taxableInBracket = Math.min(remainingTaxable, bracket1Limit);
            totalTax += (long) Math.round(taxableInBracket * 0.10);
            remainingTaxable -= taxableInBracket;
        }

        // 3. پلکان 15% (از 300,000,000 تا 380,000,000 ریال)
        long bracket2Limit = BRACKET2_MAX - BRACKET1_MAX; // 80,000,000
        if (remainingTaxable > 0) {
            long taxableInBracket = Math.min(remainingTaxable, bracket2Limit);
            totalTax += (long) Math.round(taxableInBracket * 0.15);
            remainingTaxable -= taxableInBracket;
        }

        // 4. پلکان 20% (از 380,000,000 تا 500,000,000 ریال)
        long bracket3Limit = BRACKET3_MAX - BRACKET2_MAX; // 120,000,000
        if (remainingTaxable > 0) {
            long taxableInBracket = Math.min(remainingTaxable, bracket3Limit);
            totalTax += (long) Math.round(taxableInBracket * 0.20);
            remainingTaxable -= taxableInBracket;
        }

        // 5. پلکان 25% (از 500,000,000 تا 666,667,000 ریال)
        long bracket4Limit = BRACKET4_MAX - BRACKET3_MAX; // 166,667,000
        if (remainingTaxable > 0) {
            long taxableInBracket = Math.min(remainingTaxable, bracket4Limit);
            totalTax += (long) Math.round(taxableInBracket * 0.25);
            remainingTaxable -= taxableInBracket;
        }

        // 6. پلکان 30% (مازاد بر 666,667,000 ریال)
        if (remainingTaxable > 0) {
            totalTax += (long) Math.round(remainingTaxable * 0.30);
        }

        return totalTax;
    }

    /**
     * نمایش فیش حقوقی
     */
    private static void displayPayslip(long baseSalary, long housingAllowance, long workersBenefit,
                                       long expertiseBonus, long attractionBonus, long marriageAllowance,
                                       long overtimePay, long totalEarnings, long ssoDeduction,
                                       long taxDeduction, long netSalary,
                                       double overtimeHours, double overtimeHourlyRate, long extraInsurance) {

        System.out.println("\n========================================");
        System.out.println("          فیش حقوقی (ریال) 1404           ");
        System.out.println("========================================");

        // --- بخش دریافتی‌ها (مزایا) ---
        System.out.println("         ** بخش دریافتی‌ها **           ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %-16s |\n", "شرح مزایا", "مبلغ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %,16d |\n", "1. حقوق پایه", baseSalary);
        System.out.printf("| %-20s | %,16d |\n", "2. حق مسکن", housingAllowance);
        System.out.printf("| %-20s | %,16d |\n", "3. بن کارگری/خواروبار", workersBenefit);
        System.out.printf("| %-20s | %,16d |\n", "4. فوق العاده تخصص", expertiseBonus);
        System.out.printf("| %-20s | %,16d |\n", "5. حق جذب", attractionBonus);
        System.out.printf("| %-20s | %,16d |\n", "6. حق تاهل", marriageAllowance);
        System.out.printf("| %-20s | %,16d |\n", "7. اضافه‌کاری", overtimePay);
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %,16d |\n", "جمع ناخالص دریافتی", totalEarnings);
        System.out.println("----------------------------------------");

        // --- بخش کسورات ---
        System.out.println("\n          ** بخش کسورات **             ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %-16s |\n", "شرح کسورات", "مبلغ");
        System.out.println("----------------------------------------");
        System.out.printf("| %-20s | %,16d |\n", "1. بیمه تامین اجتماعی (7%)", ssoDeduction);
        System.out.printf("| %-20s | %,16d |\n", "2. مالیات بر درآمد", taxDeduction);
        System.out.printf("| %-20s | %,16d |\n", "3. بیمه تکمیلی", extraInsurance);
        System.out.println("----------------------------------------");
        long totalDeductions = ssoDeduction + taxDeduction + extraInsurance;
        System.out.printf("| %-20s | %,16d |\n", "جمع کل کسورات", totalDeductions);
        System.out.println("----------------------------------------");

        // --- حقوق خالص ---
        System.out.printf("\n| %-20s | %,16d |\n", "** حقوق خالص پرداختی **", netSalary);
        System.out.println("========================================");

        // --- جزئیات اضافه‌کاری ---
        System.out.println("\n---------- جزئیات محاسبات ----------");
        System.out.printf("ساعات اضافه‌کاری: %.2f ساعت\n", overtimeHours);
        System.out.printf("نرخ ساعتی پایه (مبنای قانون کار): %,.2f ریال\n", HOURLY_WAGE_RATE);
        System.out.printf("نرخ ساعتی اضافه‌کاری (با ضریب 1.4): %,.2f ریال\n", overtimeHourlyRate);
        System.out.printf("معافیت ماهانه مالیاتی: %,d ریال\n", TAX_EXEMPTION_MONTHLY);
        System.out.println("------------------------------------");
    }
}
