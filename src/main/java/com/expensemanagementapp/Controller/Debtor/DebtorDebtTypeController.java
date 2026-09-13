package com.expensemanagementapp.Controller.Debtor;

import com.expensemanagementapp.Entity.DebtorDebtType;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.DebtorDebtStatusTypeModel;
import com.expensemanagementapp.Service.DebtorDebtTypeService;
import com.expensemanagementapp.Service.UserService;
import com.expensemanagementapp.Validation.Group.DebtorDebtTypeAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtTypeUpdateValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class DebtorDebtTypeController {

    @Autowired
    private UserService  userService;

    @Autowired
    private DebtorDebtTypeService debtorDebtTypeService;

    @GetMapping("/debtor/{debtorId}/debt/type/add")
    public String addDebtorDebtType(@PathVariable String debtorId,
                                    DebtorDebtStatusTypeModel debtorDebtStatusTypeModel, Model model) {

        String formTitle = "Add Debt Type";
        String formAction = "/debtor/" + debtorId + "/debt/type/add";

        return debtorDebtTypeFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId);
    }

    @PostMapping("/debtor/{debtorId}/debt/type/add")
    public String addDebtorDebtType(@PathVariable String debtorId,
                                    @Validated(DebtorDebtTypeAddValidationGroup.class)
                                    @ModelAttribute DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                    BindingResult bindingResult, Model model, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            String formTitle = "Add Debt Type";
            String formAction = "/debtor/" + debtorId + "/debt/type/add";

            return debtorDebtTypeFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId);
        }


        String userEmail = authentication.getName();

        if (userEmail == null) {
            return "redirect:/debtor/" + debtorId + "/debt/type";
        }

        User user = userService.getUserByEmail(userEmail);

        debtorDebtTypeService.addDebtorDebtType(debtorDebtStatusTypeModel, user);

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/type";
    }

    @GetMapping("/debtor/{debtorId}/debt/type/{debtorDebtTypeId}/update")
    public String updateDebtorDebtType(@PathVariable String debtorId,
                                       @PathVariable String debtorDebtTypeId,
                                       DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                       Model model) {
        String formTitle = "Update Debt Type";
        String formAction = "/debtor/" + debtorId + "/debt/type/" + debtorDebtTypeId + "/update";

        return debtorDebtTypeFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId, debtorDebtTypeId);
    }

    @PostMapping("/debtor/{debtorId}/debt/type/{debtorDebtTypeId}/update")
    public String updateDebtorDebtType(@PathVariable String debtorId,
                                       @PathVariable String debtorDebtTypeId,
                                       @Validated(DebtorDebtTypeUpdateValidationGroup.class)
                                       DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                       BindingResult bindingResult,
                                       Model model, Authentication authentication) {

        if (bindingResult.hasErrors()) {
            String formTitle = "Update Debt Type";
            String formAction = "/debtor/" + debtorId + "/debt/type/" + debtorDebtTypeId + "/update";

            return debtorDebtTypeFormPopulation(formTitle, formAction, debtorDebtStatusTypeModel, model, debtorId, debtorDebtTypeId);
        }

        DebtorDebtType debtorDebtType = debtorDebtTypeService.getDebtorDebtTypeById(debtorDebtTypeId);

        debtorDebtTypeService.updateDebtorDebtType(debtorDebtType, debtorDebtStatusTypeModel);

        return "redirect:/dashboard/debtor/" + debtorId + "/debt/type";
    }

    @GetMapping("/debtor/{debtorId}/debt/type/{debtorDebtTypeId}/delete")
    public String deleteDebtorDebtType(@PathVariable String debtorId, @PathVariable String debtorDebtTypeId){
        DebtorDebtType debtorDebtType = debtorDebtTypeService.getDebtorDebtTypeById(debtorDebtTypeId);
        debtorDebtTypeService.deleteDebtorDebtType(debtorDebtType);
        return "redirect:/dashboard/debtor/" + debtorId + "/debt/type";
    }



    private String debtorDebtTypeFormPopulation(String formTitle, String formAction,
                                                DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                                Model model, String debtorId){
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("debtorDebtStatusTypeModel", debtorDebtStatusTypeModel);
        model.addAttribute("debtorId", debtorId);

        return "Forms/debtor-debt-status-type-form";

    }

    private String debtorDebtTypeFormPopulation(String formTitle, String formAction,
                                                  DebtorDebtStatusTypeModel debtorDebtStatusTypeModel,
                                                  Model model, String debtorId, String debtorDebtTypeId) {
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("debtorDebtStatusTypeModel", debtorDebtStatusTypeModel);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtorDebtTypeId", debtorDebtTypeId);

        return "Forms/debtor-debt-status-type-form";
    }

}
