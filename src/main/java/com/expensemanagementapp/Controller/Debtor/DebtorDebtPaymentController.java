package com.expensemanagementapp.Controller.Debtor;

import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Entity.DebtorDebtPayment;
import com.expensemanagementapp.Model.DebtorDebtPaymentModel;
import com.expensemanagementapp.Service.DebtorDebtPaymentService;
import com.expensemanagementapp.Service.DebtorDebtService;
import com.expensemanagementapp.Validation.Group.DebtorDebtPaymentAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtPaymentUpdateValidationGroup;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class DebtorDebtPaymentController {

    @Autowired
    private DebtorDebtService debtorDebtService;

    @Autowired
    private DebtorDebtPaymentService debtorDebtPaymentService;


    @GetMapping("/debtor/{debtorId}/debt/{debtorDebtId}/payment/add")
    public String addDebtorDebtPayment(@PathVariable int debtorId,
                                       @PathVariable int debtorDebtId,
                                       DebtorDebtPaymentModel debtorDebtPaymentModel,
                                       Model model) {

        String formTitle = "Add Debt Payment";
        String formAction = "/debtor/" + debtorId + "/debt/" + debtorDebtId + "/payment/add";

        return debtorDebtPaymentFormPopulation(formTitle, formAction, debtorDebtPaymentModel, model, debtorId, debtorDebtId);
    }

    @PostMapping("/debtor/{debtorId}/debt/{debtorDebtId}/payment/add")
    public String addDebtorDebtPayment(@PathVariable int debtorId,
                                       @PathVariable int debtorDebtId,
                                       @Validated(DebtorDebtPaymentAddValidationGroup.class)
                                       @ModelAttribute DebtorDebtPaymentModel debtorDebtPaymentModel,
                                       BindingResult bindingResult,
                                       Model model) {


        // Get debtor debt by id
        DebtorDebt debtorDebt = debtorDebtService.findDebtorDebtById(debtorDebtId);

        // Get debtor debt amount
        double debtorDebtAmount = debtorDebt.getDebtorDebtAmount();

        // Get payment sum for a particular debt
        double debtorDebtPaymentTotal = debtorDebtPaymentService.findAllDebtorDebtPaymentByDebtorDebt(debtorDebt).stream()
                .mapToDouble(DebtorDebtPayment::getDebtorDebtPaymentAmount)
                .sum();

        // Get current debtor debt payment amount (Includes: sum of payments + new payment coming from the model)
        double currentDebtorDebtPaymentAmount  = debtorDebtPaymentTotal + debtorDebtPaymentModel.getDebtorDebtPaymentAmount();

        // Get exceeded debtor debt payment amount
        double exceededDebtorDebtPaymentAmount = currentDebtorDebtPaymentAmount - debtorDebtAmount;

        // Check whether the payment amount exceeds the debt amount
        // Add a custom field binding result error
        if (currentDebtorDebtPaymentAmount > debtorDebtAmount) {
            bindingResult.addError(new FieldError("debtorDebtPaymentModel",
                    "debtorDebtPaymentAmount",
                    "Payment amount exceeds debt amount by $" + (exceededDebtorDebtPaymentAmount) + "."));
        }

        // Check for binding result errors and return to the form if any exist
        if (bindingResult.hasErrors()) {
            String formTitle = "Add Debt Payment";
            String formAction = "/debtor/" + debtorId + "/debt/" + debtorDebtId + "/payment/add";

            return debtorDebtPaymentFormPopulation(formTitle, formAction, debtorDebtPaymentModel, model, debtorId, debtorDebtId);
        }

        // Add debtor debt payment
        debtorDebtPaymentService.addDebtorDebtPayment(debtorDebt, debtorDebtPaymentModel);

        // Check whether the payment sum equals the debt amount and update the debt status accordingly
        if (debtorDebtPaymentTotal + debtorDebtPaymentModel.getDebtorDebtPaymentAmount() == debtorDebtAmount) {
            debtorDebtService.updateDebtorDebtStatus(debtorDebt, "Paid");
        }

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/" + debtorDebtId + "/payment";
    }

    @GetMapping("/debtor/{debtorId}/debt/{debtorDebtId}/payment/{debtorDebtPaymentId}/update")
    public String updateDebtorDebtPayment(@PathVariable int debtorId,
                                          @PathVariable int debtorDebtId,
                                          @PathVariable int debtorDebtPaymentId,
                                          DebtorDebtPaymentModel debtorDebtPaymentModel,
                                          Model model) {

        String formTitle = "Update Debt Payment";
        String formAction = "/debtor/" + debtorId + "/debt/" + debtorDebtId + "/payment/" + debtorDebtPaymentId + "/update";

        return debtorDebtPaymentFormPopulation(formTitle, formAction, debtorDebtPaymentModel, model, debtorId, debtorDebtId);
    }

    @PostMapping("/debtor/{debtorId}/debt/{debtorDebtId}/payment/{debtorDebtPaymentId}/update")
    public String updateDebtorDebtPayment(@PathVariable int debtorId,
                                          @PathVariable int debtorDebtId,
                                          @PathVariable int debtorDebtPaymentId,
                                          @Validated(DebtorDebtPaymentUpdateValidationGroup.class)
                                          @ModelAttribute DebtorDebtPaymentModel debtorDebtPaymentModel,
                                          BindingResult bindingResult,
                                          Model model) {

        // Get debtor debt by id
        DebtorDebt debtorDebt = debtorDebtService.findDebtorDebtById(debtorDebtId);

        // Get Debtor Debt Payment
        DebtorDebtPayment debtorDebtPayment = debtorDebtPaymentService.findDebtorDebtPaymentById(debtorDebtPaymentId);

        // Get debtor debt amount
        double debtorDebtAmount = debtorDebt.getDebtorDebtAmount();

        // Get payment sum for a particular debt
        double debtorDebtPaymentTotal = debtorDebtPaymentService.findAllDebtorDebtPaymentByDebtorDebt(debtorDebt).stream()
                .mapToDouble(DebtorDebtPayment::getDebtorDebtPaymentAmount)
                .sum();

        double currentDebtorDebtPaymentAmount = debtorDebtPaymentTotal -
                debtorDebtPayment.getDebtorDebtPaymentAmount() +
                debtorDebtPaymentModel.getDebtorDebtPaymentAmount();

        double exceededDebtorDebtPaymentAmount = currentDebtorDebtPaymentAmount - debtorDebtAmount;

        // Check whether the payment amount exceeds the debt amount
        // Add a custom field binding result error
        if (currentDebtorDebtPaymentAmount > debtorDebtAmount) {
            bindingResult.addError(new FieldError("debtorDebtPaymentModel",
                    "debtorDebtPaymentAmount",
                    "Payment amount exceeds debt amount by $" + exceededDebtorDebtPaymentAmount + "."));
        }

        if (bindingResult.hasErrors()) {
            String formTitle = "Update Debt Payment";
            String formAction = "/debtor/" + debtorId + "/debt/" + debtorDebtId + "/payment/" + debtorDebtPaymentId + "/update";

            return debtorDebtPaymentFormPopulation(formTitle, formAction, debtorDebtPaymentModel, model, debtorId, debtorDebtId);
        }

        // Perform update
        debtorDebtPaymentService.updateDebtorDebtPayment(debtorDebtPaymentModel, debtorDebtPayment);

        if (currentDebtorDebtPaymentAmount == debtorDebtAmount) {
            debtorDebtService.updateDebtorDebtStatus(debtorDebt, "Paid");
        }else{
            debtorDebtService.updateDebtorDebtStatus(debtorDebt, "Unpaid");
        }

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/" + debtorDebtId + "/payment";

    }
//
    @GetMapping("/debtor/{debtorId}/debt/{debtorDebtId}/payment/{debtorDebtPaymentId}/delete")
    public String updateDebtorDebtPayment(@PathVariable int debtorId,
                                          @PathVariable int debtorDebtId,
                                          @PathVariable int debtorDebtPaymentId) {

        // Get debtor debt payment by id
        DebtorDebtPayment debtorDebtPayment = debtorDebtPaymentService.findDebtorDebtPaymentById(debtorDebtPaymentId);

        // Delete debt debt payment
        debtorDebtPaymentService.deleteDebtorDebtPayment(debtorDebtPayment);

        // Get debtor debt by id
        DebtorDebt debtorDebt = debtorDebtService.findDebtorDebtById(debtorDebtId);

        // Get debtor debt amount
        double debtorDebtAmount = debtorDebt.getDebtorDebtAmount();

        // Get payment sum for a particular debt
        double debtorDebtPaymentTotal = debtorDebtPaymentService.findAllDebtorDebtPaymentByDebtorDebt(debtorDebt).stream()
                .mapToDouble(DebtorDebtPayment::getDebtorDebtPaymentAmount)
                .sum();

        // Check whether the payment sum is less than the debt amount and update the debt status accordingly
        if (debtorDebtPaymentTotal < debtorDebtAmount) {
            debtorDebtService.updateDebtorDebtStatus(debtorDebt, "Unpaid");
        }

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/" + debtorDebtId + "/payment";
    }

    private String debtorDebtPaymentFormPopulation(String formTitle, String formAction, DebtorDebtPaymentModel debtorDebtPaymentModel, Model model, int debtorId, int debtorDebtId) {
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtorDebtId", debtorDebtId);
        model.addAttribute("debtorDebtPaymentModel", debtorDebtPaymentModel);
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);

        return "Forms/debtor-debt-payment-form";
    }




}
