package com.expensemanagementapp.Controller.Debtor;

import com.expensemanagementapp.Entity.Debtor;
import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Entity.DebtorDebtStatus;
import com.expensemanagementapp.Entity.DebtorDebtType;
import com.expensemanagementapp.Model.DebtorDebtModel;
import com.expensemanagementapp.Service.DebtorDebtService;
import com.expensemanagementapp.Service.DebtorDebtStatusService;
import com.expensemanagementapp.Service.DebtorDebtTypeService;
import com.expensemanagementapp.Service.DebtorService;
import com.expensemanagementapp.Validation.Group.DebtorDebtAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorDebtUpdateValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/debtor")
public class DebtorDebtController {

    @Autowired
    private DebtorDebtStatusService  debtorDebtStatusService;

    @Autowired
    private DebtorDebtTypeService debtorDebtTypeService;

    @Autowired
    private DebtorDebtService debtorDebtService;

    @Autowired
    private DebtorService debtorService;

    @GetMapping("/{debtorId}/debt/add")
    public String AddDebtorDebt(@PathVariable int debtorId, DebtorDebtModel debtorDebtModel, Model model) {

        String formTitle = "Add";
        String formAction = "/debtor/" + debtorId + "/debt/add";
        String formDecision = "add";

        List<DebtorDebtStatus> debtorDebtStatusList = debtorDebtStatusService.findAllStatusesAsc();
        List<DebtorDebtType> debtorDebtTypeList = debtorDebtTypeService. findAllTypesAsc();

        return debtorDebtPopulationForm(model, formTitle, formAction, formDecision, debtorDebtStatusList, debtorDebtTypeList, debtorDebtModel, debtorId);

    }

    @PostMapping("/{debtorId}/debt/add")
    public String AddDebtorDebt(@PathVariable int debtorId,
                                @Validated(DebtorDebtAddValidationGroup.class)
                                @ModelAttribute DebtorDebtModel debtorDebtModel,
                                BindingResult  bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {

            String formTitle = "Add";
            String formAction = "/debtor/" + debtorId + "/debt/add";
            String formDecision = "add";

            List<DebtorDebtStatus> debtorDebtStatusList = debtorDebtStatusService.findAllStatusesAsc();
            List<DebtorDebtType> debtorDebtTypeList = debtorDebtTypeService. findAllTypesAsc();

            return debtorDebtPopulationForm(model, formTitle, formAction, formDecision, debtorDebtStatusList, debtorDebtTypeList, debtorDebtModel, debtorId);
        }

        Debtor debtor = debtorService.findDebtorById(debtorId);

        debtorDebtService.addDebtorDebt(debtorDebtModel, debtor);

        return "redirect:/dashboard/debtor/" + debtorId + "/debt";
    }

    @GetMapping("/{debtorId}/debt/{debtorDebtId}/update")
    public String updateDebtorDebt(@PathVariable int debtorId,
                                   @PathVariable int debtorDebtId,
                                   DebtorDebtModel debtorDebtModel, Model model) {

        String formTitle = "Update";
        String formAction = "/debtor/" + debtorId + "/debt/" + debtorDebtId + "/update";
        String formDecision = "update";

        List<DebtorDebtStatus> debtorDebtStatusList = debtorDebtStatusService.findAllStatusesAsc();
        List<DebtorDebtType> debtorDebtTypeList = debtorDebtTypeService. findAllTypesAsc();

        return debtorDebtPopulationForm(model, formTitle, formAction, formDecision, debtorDebtStatusList, debtorDebtTypeList, debtorDebtModel, debtorDebtId, debtorId);
    }

    @PostMapping("/{debtorId}/debt/{debtorDebtId}/update")
    public String updateDebtorDebt(@PathVariable int debtorId,
                                   @PathVariable int debtorDebtId,
                                @Validated(DebtorDebtUpdateValidationGroup.class)
                                @ModelAttribute DebtorDebtModel debtorDebtModel,
                                BindingResult  bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            String formTitle = "update";
            String formAction = "/debtor/" + debtorId + "/debt/" + debtorDebtId + "/update";
            String formDecision = "update";

            List<DebtorDebtStatus> debtorDebtStatusList = debtorDebtStatusService.findAllStatusesAsc();
            List<DebtorDebtType> debtorDebtTypeList = debtorDebtTypeService. findAllTypesAsc();

            return debtorDebtPopulationForm(model, formTitle, formAction, formDecision, debtorDebtStatusList, debtorDebtTypeList, debtorDebtModel, debtorDebtId, debtorId);
        }

        DebtorDebt debtorDebt = debtorDebtService.findDebtorDebtById(debtorDebtId);

        debtorDebtService.updateDebtorDebt(debtorDebtModel, debtorDebt);

        return "redirect:/dashboard/debtor/" + debtorId + "/debt";
    }

    @GetMapping("{debtorId}/debt/{debtorDebtId}/delete")
    public String deleteDebtorDebt(@PathVariable int debtorId,
                                   @PathVariable int debtorDebtId) {

        DebtorDebt debtorDebt =  debtorDebtService.findDebtorDebtById(debtorDebtId);
        debtorDebtService.deleteDebtorDebt(debtorDebt);
        return "redirect:/dashboard/debtor/" + debtorId + "/debt";
    }

    private String debtorDebtPopulationForm(Model model, String formTitle, String formAction, String formDecision, List<DebtorDebtStatus> debtorDebtStatusList, List<DebtorDebtType> debtorDebtTypeList, DebtorDebtModel debtorDebtModel, int debtorId) {
        debtorFormPopulationHelper(model, formTitle, formAction, formDecision, debtorDebtStatusList, debtorDebtTypeList, debtorDebtModel, debtorId);
        return "Forms/debtor-debt-form";
    }

    private String debtorDebtPopulationForm(Model model, String formTitle, String formAction, String formDecision, List<DebtorDebtStatus> debtorDebtStatusList, List<DebtorDebtType> debtorDebtTypeList, DebtorDebtModel debtorDebtModel,  int debtorDebtId, int debtorId) {
        debtorFormPopulationHelper(model, formTitle, formAction, formDecision, debtorDebtStatusList, debtorDebtTypeList, debtorDebtModel, debtorId);
        model.addAttribute("debtorDebtId", debtorDebtId);
        return "Forms/debtor-debt-form";
    }

    private void debtorFormPopulationHelper(Model model, String formTitle, String formAction, String formDecision, List<DebtorDebtStatus> debtorDebtStatusList, List<DebtorDebtType> debtorDebtTypeList, DebtorDebtModel debtorDebtModel, int debtorId){
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("formDecision", formDecision);

        model.addAttribute("debtorDebtStatusList", debtorDebtStatusList);
        model.addAttribute("debtorDebtTypeList", debtorDebtTypeList);
        model.addAttribute("debtorDebtModel", debtorDebtModel);
        model.addAttribute("debtorId", debtorId);
    }

}
