package com.iran.payroll;

public class ProgressiveTaxCalculator implements TaxCalculator {
    @Override
    public long calculateTax(long incomeForTax) {
        if (incomeForTax <= Constants.TAX_EXEMPTION_MONTHLY) {
            return 0;
        }

        long totalTax = 0;
        long remaining = incomeForTax;

        // exempt portion
        long exempt = Math.min(remaining, Constants.TAX_EXEMPTION_MONTHLY);
        remaining -= exempt;

        // bracket 1: 10% (240,000,001 - 300,000,000) -> effective range after exemption
        long bracket1Limit = Constants.BRACKET1_MAX - Constants.TAX_EXEMPTION_MONTHLY;
        if (remaining > 0) {
            long amount = Math.min(remaining, bracket1Limit);
            totalTax += Math.round(amount * 0.10);
            remaining -= amount;
        }

        // bracket 2: 15%
        long bracket2Limit = Constants.BRACKET2_MAX - Constants.BRACKET1_MAX;
        if (remaining > 0) {
            long amount = Math.min(remaining, bracket2Limit);
            totalTax += Math.round(amount * 0.15);
            remaining -= amount;
        }

        // bracket 3: 20%
        long bracket3Limit = Constants.BRACKET3_MAX - Constants.BRACKET2_MAX;
        if (remaining > 0) {
            long amount = Math.min(remaining, bracket3Limit);
            totalTax += Math.round(amount * 0.20);
            remaining -= amount;
        }

        // bracket 4: 25%
        long bracket4Limit = Constants.BRACKET4_MAX - Constants.BRACKET3_MAX;
        if (remaining > 0) {
            long amount = Math.min(remaining, bracket4Limit);
            totalTax += Math.round(amount * 0.25);
            remaining -= amount;
        }

        // remainder: 30%
        if (remaining > 0) {
            totalTax += Math.round(remaining * 0.30);
        }

        return totalTax;
    }
}
