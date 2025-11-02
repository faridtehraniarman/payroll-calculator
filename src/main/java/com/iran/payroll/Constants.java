package com.iran.payroll;

public final class Constants {
    private Constants() {}

    // --- ثابت‌های محاسباتی و نرخ‌های قانونی (سال 1404) ---
    public static final long MIN_DAILY_WAGE = 3463656L;
    public static final double DAILY_WORK_HOURS = 7.33;
    public static final double HOURLY_WAGE_RATE = MIN_DAILY_WAGE / DAILY_WORK_HOURS;
    public static final double OVERTIME_MULTIPLIER = 1.4;
    public static final long HOUSING_ALLOWANCE = 9000000L;
    public static final double WORK_HOURS_PER_MONTH = 192.0;
    public static final long WORKERS_BENEFIT = 22000000L;
    public static final long TAX_EXEMPTION_MONTHLY = 240000000L;
    public static final double SSO_EMPLOYEE_RATE = 0.07;

    // tax brackets (monthly, Rials)
    public static final long BRACKET1_START = 240_000_001L;
    public static final long BRACKET2_START = 300_000_001L;
    public static final long BRACKET3_START = 380_000_001L;
    public static final long BRACKET4_START = 500_000_001L;
    public static final long BRACKET5_START = 666_667_001L;

    public static final long BRACKET1_MAX = 300_000_000L;
    public static final long BRACKET2_MAX = 380_000_000L;
    public static final long BRACKET3_MAX = 500_000_000L;
    public static final long BRACKET4_MAX = 666_666_670L;
}
