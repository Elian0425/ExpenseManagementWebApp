package com.expensemanagementapp.Controller.Debtor;

import com.expensemanagementapp.Entity.DebtorDebtStatus;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.DebtorDebtStatusTypeModel;
import com.expensemanagementapp.Service.DebtorDebtStatusService;
import com.expensemanagementapp.Service.UserService;
import com.expensemanagementapp.Validation.Group.DebtorDebtStatusAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtStatusUpdateValidationGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DebtorDebtStatusController {

    @Autowired
    private DebtorDebtStatusService debtorDebtStatusService;

    @Autowired
    private UserService userService;

    @GetMapping("/debtor/{debtorId}/debt/status/add")
    public String addDebtorDebtStatus(@PathVariable String debtorId,
                                      DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                      Model model) {

        String formTitle = "Add Debt Status";
        String formAction = "/debtor/" + debtorId + "/debt/status/add";

        return debtorDebtStatusFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId);


    }

    @PostMapping("/debtor/{debtorId}/debt/status/add")
    public String addDebtorDebtStatus(@PathVariable String debtorId,
                                      @Validated(DebtorDebtStatusAddValidationGroup.class)
                            @ModelAttribute DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                      BindingResult bindingResult,
                                      Model model, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            String formTitle = "Add Debt Status";
            String formAction = "/debtor/" + debtorId + "/debt/status/add";

            debtorDebtStatusFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId);
        }

        String userEmail = authentication.getName();

        if (userEmail == null) {
            return "redirect:/debtor/" + debtorId + "/debt/status";
        }

        User user = userService.getUserByEmail(userEmail);

        debtorDebtStatusService.addDebtorDebtStatus(debtorDebtStatusTypeModel, user);

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/status";
    }

    @GetMapping("/debtor/{debtorId}/debt/status/{debtorDebtStatusId}/update")
    public String updateDebtorDebtStatus(@PathVariable String debtorId,
                                         @PathVariable String debtorDebtStatusId,
                                         DebtorDebtStatusTypeModel debtorDebtStatusTypeModel, Model model) {
        String formTitle = "Update Debt Status";
        String formAction = "/debtor/" + debtorId + "/debt/status/" + debtorDebtStatusId  + "/update";

        return debtorDebtStatusFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId, debtorDebtStatusId);
    }

    @PostMapping("/debtor/{debtorId}/debt/status/{debtorDebtStatusId}/update")
    public String updateDebtorDebtStatus(@PathVariable String debtorId,
                                      @PathVariable String debtorDebtStatusId,
                                      @Validated(DebtorDebtStatusUpdateValidationGroup.class)
                                      @ModelAttribute DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                      BindingResult bindingResult,
                                      Model model) {

        if (bindingResult.hasErrors()) {
            String formTitle = "Update Debt Status";
            String formAction = "/debtor/" + debtorId + "/debt/status/" + debtorDebtStatusId  + "/update";

            return debtorDebtStatusFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId, debtorDebtStatusId);
        }

        DebtorDebtStatus debtorDebtStatus = debtorDebtStatusService.findDebtorDebtStatusById(debtorDebtStatusId);

        debtorDebtStatusService.updateDebtorDebtStatus(debtorDebtStatus, debtorDebtStatusTypeModel);

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/status";
    }

    @GetMapping("/debtor/{debtorId}/debt/status/{debtorDebtStatusId}/delete")
    public String deleteDebtorDebtStatus(@PathVariable String debtorId,
                                         @PathVariable String debtorDebtStatusId) {

        DebtorDebtStatus debtorDebtStatus = debtorDebtStatusService.findDebtorDebtStatusById(debtorDebtStatusId);
        debtorDebtStatusService.deleteDebtorDebtStatus(debtorDebtStatus);

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/status";
    }


    private String debtorDebtStatusFormPopulation(String formTitle, String formAction,
                                                  DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                                  Model model, String debtorId) {
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("debtorDebtStatusTypeModel", debtorDebtStatusTypeModel);
        model.addAttribute("debtorId", debtorId);

        return "Forms/debtor-debt-status-type-form";

    }

    private String debtorDebtStatusFormPopulation(String formTitle, String formAction,
                                                  DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                                  Model model, String debtorId, String debtorDebtStatusId) {
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("debtorDebtStatusTypeModel", debtorDebtStatusTypeModel);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtorDebtStatusId", debtorDebtStatusId);

        return "Forms/debtor-debt-status-type-form";

    }

}
