//package com.iran.payroll.ui;
//
//import com.iran.payroll.*;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.NumberField;
//import com.vaadin.flow.router.Route;
//
//import java.awt.*;
//
//@Route("")
//public class MainView extends VerticalLayout {
//
//    private final NumberField baseSalary = new NumberField("حقوق پایه ماهانه (ریال)");
//    private final NumberField overtimeHours = new NumberField("ساعات اضافه کاری در ماه");
//    private final NumberField expertiseBonus = new NumberField("فوق العاده تخصص (ریال)");
//    private final NumberField attractionBonus = new NumberField("حق جذب (ریال)");
//    private final NumberField marriageAllowance = new NumberField("حق تاهل (ریال)");
//
//    private final com.vaadin.flow.component.button.Button calculateBtn = new Button("محاسبه فیش حقوقی");
//    private final Grid<PayslipResult> grid = new Grid<>(PayslipResult.class);
//
//    private final PayrollCalculator calculator =
//            new PayrollCalculator(new ProgressiveTaxCalculator());
//
//    private final NewCalculator calc = new NewCalculator();
//    public MainView() {
//        configureFields();
//        configureGrid();
//        calculateBtn.addClickListener(e -> calculate());
//        FormLayout form = new FormLayout(
//                baseSalary,
//                overtimeHours,
//                expertiseBonus,
//                attractionBonus,
//                marriageAllowance,
//                calculateBtn
//        );
//        add(form, grid);
//        setSizeFull();
//    }
//
//    private void configureFields() {
//        baseSalary.setStep(1);
//        expertiseBonus.setStep(1);
//        attractionBonus.setStep(1);
//        marriageAllowance.setStep(1);
//        overtimeHours.setStep(0.5);
//        baseSalary.setRequiredIndicatorVisible(true);
//    }
//
//    private void configureGrid() {
//        grid.setWidthFull();
//        grid.setColumns(
//                "baseSalary",
//                "overtimeHours",
//                "expertiseBonus",
//                "attractionBonus",
//                "marriageAllowance",
//                "overtimePay",
//                "grossIncome",
//                "taxableIncome",
//                "taxAmount",
//                "netPay"
//        );
//    }
//
//    private void calculate() {
//        try {
//            long base = safeLong(baseSalary);
//            double ot = safeDouble(overtimeHours);
//            long exp = safeLong(expertiseBonus);
//            long attr = safeLong(attractionBonus);
//            long marr = safeLong(marriageAllowance);
//
//            EmployeeInput input = new EmployeeInput(base, ot, exp, attr, marr);
//            Payslip payslip = calculator.calculate(input);
//
//            PayslipResult result = PayslipResult.from(input, payslip);
//            grid.setItems(result);
//            Notification.show("محاسبه انجام شد", 3000, Notification.Position.TOP_CENTER);
//        } catch (Exception ex) {
//            Notification.show("خطا در ورودی ها", 4000, Notification.Position.TOP_CENTER);
//        }
//    }
//
//    private long safeLong(NumberField f) {
//        Double v = f.getValue();
//        return v == null ? 0L : v.longValue();
//    }
//
//    private double safeDouble(NumberField f) {
//        Double v = f.getValue();
//        return v == null ? 0.0 : v;
//    }
//
//    public static class PayslipResult {
//        private long baseSalary;
//        private double overtimeHours;
//        private long expertiseBonus;
//        private long attractionBonus;
//        private long marriageAllowance;
//
//        private long overtimePay;
//        private long grossIncome;
//        private long taxableIncome;
//        private long taxAmount;
//        private long netPay;
//
//        public static PayslipResult from(EmployeeInput input, Payslip payslip) {
//            PayslipResult r = new PayslipResult();
//            r.baseSalary = input.getBaseSalary();
//            r.overtimeHours = input.getOvertimeHours();
//            r.expertiseBonus = input.getExpertiseBonus();
//            r.attractionBonus = input.getAttractionBonus();
//            r.marriageAllowance = input.getMarriageAllowance();
//
//            // Values available from current API
//            r.overtimePay = payslip.getOvertimePay();
//
//            // Derive display values without relying on missing getters
//            r.grossIncome = r.baseSalary + r.expertiseBonus + r.attractionBonus + r.marriageAllowance + r.overtimePay;
//            r.taxableIncome = r.grossIncome; // adjust if your domain differs
//            r.taxAmount = 0; // set to 0 if not exposed by Payslip; replace when a getter exists
//            r.netPay = r.grossIncome - r.taxAmount;
//
//            return r;
//        }
//
//        public long getBaseSalary() { return baseSalary; }
//        public double getOvertimeHours() { return overtimeHours; }
//        public long getExpertiseBonus() { return expertiseBonus; }
//        public long getAttractionBonus() { return attractionBonus; }
//        public long getMarriageAllowance() { return marriageAllowance; }
//        public long getOvertimePay() { return overtimePay; }
//        public long getGrossIncome() { return grossIncome; }
//        public long getTaxableIncome() { return taxableIncome; }
//        public long getTaxAmount() { return taxAmount; }
//        public long getNetPay() { return netPay; }
//    }
//}