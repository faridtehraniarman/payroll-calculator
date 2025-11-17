package com.iran.payroll;

public class PayslipEntry {
    private final String label;
    private final String value;

    public PayslipEntry(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public String getValue() { return value; }
}
