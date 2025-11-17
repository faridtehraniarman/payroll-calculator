package com.iran.payroll.ui;

import com.iran.payroll.NewCalculator;
import com.iran.payroll.PayslipModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/payroll-form-view.css")
public class PayrollFormView extends VerticalLayout {

    private final Div resultContainer = new Div();

    public PayrollFormView() {
        // Center the content on the page
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Form container with a max-width and custom style
        VerticalLayout formContainer = new VerticalLayout();
        formContainer.addClassName("form-container"); // Apply the CSS class
        formContainer.setMaxWidth("600px");
        formContainer.setWidthFull();

        formContainer.add(new H1("محاسبه‌گر فیش حقوقی"));

        // Input fields
        TextField baseSalaryField = new TextField("حقوق پایه ماهانه (ریال)");
        baseSalaryField.setRequired(true);
        TextField overtimeHoursField = new TextField("ساعات اضافه‌کاری در ماه");
        overtimeHoursField.setRequired(true);
        TextField expertiseBonusField = new TextField("فوق العاده تخصص (ریال)");
        TextField attractionBonusField = new TextField("حق جذب (ریال)");
        TextField marriageAllowanceField = new TextField("حق تاهل (ریال)");

        // Use FormLayout for better alignment
        FormLayout formLayout = new FormLayout();
        formLayout.add(baseSalaryField, overtimeHoursField, expertiseBonusField, attractionBonusField, marriageAllowanceField);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.setColspan(marriageAllowanceField, 2);

        // Calculation button
        Button calcButton = new Button("محاسبه فیش حقوقی", event -> {
            try {
                long baseSalary = Long.parseLong(baseSalaryField.getValue().trim());
                double overtimeHours = Double.parseDouble(overtimeHoursField.getValue().trim());
                long expertiseBonus = parseLongOrZero(expertiseBonusField.getValue());
                long attractionBonus = parseLongOrZero(attractionBonusField.getValue());
                long marriageAllowance = parseLongOrZero(marriageAllowanceField.getValue());

                PayslipModel model = NewCalculator.calculate(baseSalary, overtimeHours, expertiseBonus,
                        attractionBonus, marriageAllowance);

                resultContainer.removeAll();
                resultContainer.add(PayslipView.buildPayslipComponent(model));
            } catch (NumberFormatException ex) {
                Notification.show("لطفا مقادیر عددی معتبر برای فیلدهای الزامی وارد کنید.", 3000, Notification.Position.MIDDLE);
            } catch (Exception ex) {
                Notification.show("خطا در محاسبه: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        calcButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        calcButton.setWidthFull();

        // Add components to the form container
        formContainer.add(formLayout, calcButton, resultContainer);

        // Add the form container to the main view
        add(formContainer);
    }

    private long parseLongOrZero(String v) {
        if (v == null || v.trim().isEmpty()) return 0L;
        return Long.parseLong(v.trim());
    }
}