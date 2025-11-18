package com.iran.payroll.ui;

import com.iran.payroll.PayslipEntry;
import com.iran.payroll.PayslipModel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

//public class PayslipView {
//
//    private static final DecimalFormat LONG_FMT = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
//    private static final DecimalFormat DOUBLE_FMT = new DecimalFormat("#,##0.00");
//
//    static {
//        LONG_FMT.setGroupingUsed(true);
//        LONG_FMT.setMaximumFractionDigits(0);
//    }
//
//    public static VerticalLayout buildPayslipComponent(PayslipModel p) {
//        VerticalLayout layout = new VerticalLayout();
//        layout.setPadding(true);
//        layout.setSpacing(true);
//        layout.getStyle().set("border-top", "1px solid var(--lumo-contrast-10pct)");
//        layout.getStyle().set("margin-top", "1em");
//
//        layout.add(new H2("فیش حقوقی (ریال)"));
//
//        Grid<PayslipEntry> grid = new Grid<>();
//
//        // Use a ComponentRenderer to style summary rows
//        grid.addColumn(new ComponentRenderer<>(entry -> {
//            Span label = new Span(entry.getLabel());
//            if (entry.getLabel().startsWith("جمع") || entry.getLabel().startsWith("حقوق خالص")) {
//                label.getStyle().set("font-weight", "bold");
//            }
//            return label;
//        })).setHeader("شرح").setAutoWidth(true);
//
//        grid.addColumn(PayslipEntry::getValue).setHeader("مبلغ").setAutoWidth(true).setFlexGrow(1);
//
//        List<PayslipEntry> items = Arrays.asList(
//                new PayslipEntry("1. حقوق پایه", formatLong(p.getBaseSalary())),
//                new PayslipEntry("2. حق مسکن", formatLong(p.getHousingAllowance())),
//                new PayslipEntry("3. بن کارگری/خواروبار", formatLong(p.getWorkersBenefit())),
//                new PayslipEntry("4. فوق العاده تخصص", formatLong(p.getExpertiseBonus())),
//                new PayslipEntry("5. حق جذب", formatLong(p.getAttractionBonus())),
//                new PayslipEntry("6. حق تاهل", formatLong(p.getMarriageAllowance())),
//                new PayslipEntry("7. اضافه‌کاری", formatLong(p.getOvertimePay())),
//                new PayslipEntry("جمع ناخالص دریافتی", formatLong(p.getTotalEarnings())),
//                new PayslipEntry("1. بیمه تامین اجتماعی (7%)", formatLong(p.getSsoDeduction())),
//                new PayslipEntry("2. مالیات بر درآمد", formatLong(p.getTaxDeduction())),
//                new PayslipEntry("3. بیمه تکمیلی", formatLong(p.getExtraInsurance())),
//                new PayslipEntry("جمع کل کسورات", formatLong(p.getSsoDeduction() + p.getTaxDeduction() + p.getExtraInsurance())),
//                new PayslipEntry("حقوق خالص پرداختی", formatLong(p.getNetSalary())),
//                new PayslipEntry("ساعات اضافه‌کاری", DOUBLE_FMT.format(p.getOvertimeHours())),
//                new PayslipEntry("نرخ ساعتی اضافه‌کاری", DOUBLE_FMT.format(p.getOvertimeHourlyRate()))
//        );
//
//        grid.setItems(items);
//        grid.setAllRowsVisible(true); // Modern way to make the grid fit its content
//        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
//
//        layout.add(grid);
//        return layout;
//    }
//
//    private static String formatLong(long value) {
//        return LONG_FMT.format(value);
//    }
//}

public class PayslipView {

    private static final DecimalFormat CURRENCY_FMT = (DecimalFormat) DecimalFormat.getInstance(new Locale("fa", "IR"));

    static {
        CURRENCY_FMT.setMaximumFractionDigits(0);
    }

    public static Div buildPayslipComponent(PayslipModel p) {
        Div receiptCard = new Div();
        receiptCard.addClassName("receipt-card");

        // Header
        Div header = new Div();
        header.addClassName("receipt-header");
        header.add(new H2("فیش حقوقی هوشمند"));
        header.add(new Span("پیش‌نمایش زنده محاسبات"));
        receiptCard.add(header);

        // Body
        VerticalLayout body = new VerticalLayout();
        body.setPadding(false);
        body.setSpacing(false);
        body.addClassName("receipt-body");

        // Income
        body.add(createRow("حقوق پایه", p.getBaseSalary(), false));
        body.add(createRow("اضافه‌کاری", p.getOvertimePay(), true));
        long otherBenefits = p.getHousingAllowance() + p.getWorkersBenefit() + p.getExpertiseBonus() + p.getAttractionBonus() + p.getMarriageAllowance();
        body.add(createRow("مزایا و پاداش‌ها", otherBenefits, true));

        // Divider
        Div divider = new Div();
        divider.getStyle().set("height", "1px").set("background-color", "#e2e8f0").set("margin", "1rem 0");
        body.add(divider);

        // Deductions
        body.add(createRow("حق بیمه (۷٪)", p.getSsoDeduction(), false, true));
        body.add(createRow("مالیات بر درآمد", p.getTaxDeduction(), false, true));
        body.add(createRow("بیمه تکمیلی", p.getExtraInsurance(), false, true));

        // Total (Net Pay)
        Div netPaySection = new Div();
        netPaySection.addClassName("net-pay-section");
        HorizontalLayout netPayLayout = new HorizontalLayout(new Span("خالص پرداختی"), createNetPayValue(p.getNetSalary()));
        netPayLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        netPayLayout.setAlignItems(FlexComponent.Alignment.END);
        netPayLayout.setWidthFull();
        netPaySection.add(netPayLayout);
        body.add(netPaySection);

        receiptCard.add(body);

        return receiptCard;
    }

    private static Div createRow(String label, long value, boolean isIncome) {
        return createRow(label, value, isIncome, false);
    }

    private static Div createRow(String label, long value, boolean isIncome, boolean isDeduction) {
        Div row = new Div();
        row.addClassName("receipt-row");

        Span labelSpan = new Span(label);
        labelSpan.addClassName("receipt-label");

        String sign = isIncome ? "+ " : (isDeduction ? "- " : "");
        Span valueSpan = new Span(sign + formatCurrency(value) + " ریال");
        valueSpan.addClassName("receipt-value");
        if (isIncome) valueSpan.addClassName("income");
        if (isDeduction) valueSpan.addClassName("deduction");

        row.add(labelSpan, valueSpan);
        return row;
    }

    private static VerticalLayout createNetPayValue(long value) {
        Span valueSpan = new Span(formatCurrency(value));
        valueSpan.getStyle()
                .set("font-size", "1.875rem") // text-3xl
                .set("font-weight", "900") // font-black
                .set("color", "#4f46e5"); // text-indigo-600

        Span currencySpan = new Span("ریال ایران");
        currencySpan.getStyle()
                .set("font-size", "0.75rem") // text-xs
                .set("color", "#94a3b8"); // text-slate-400

        VerticalLayout layout = new VerticalLayout(valueSpan, currencySpan);
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.END);
        return layout;
    }

    private static String formatCurrency(long value) {
        return CURRENCY_FMT.format(value);
    }
}