package com.expensemanagementapp.Controller.Debtor;

import com.expensemanagementapp.Entity.Debtor;
import com.expensemanagementapp.Entity.User;
import com.expensemanagementapp.Model.UserModel;
import com.expensemanagementapp.Repository.UserRepository;
import com.expensemanagementapp.Service.DebtorService;
import com.expensemanagementapp.Validation.Group.DebtorAddValidationGroup;
import com.expensemanagementapp.Validation.Group.DebtorUpdateValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/debtor")
public class DebtorController {

    @Autowired
    private DebtorService debtorService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String addDebtor(UserModel userModel, Model model) {

        // Declare and assign values to the form
        String formTitle = "Add";
        String formAction = "/debtor/add";

        // Return form
        return debtorFormPopulation(model, userModel, formTitle, formAction);
    }

    @PostMapping("/add")
    public String addDebtor(@Validated(DebtorAddValidationGroup.class)
                                @ModelAttribute UserModel userModel,
                            BindingResult bindingResult, Model model, Authentication authentication) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {

            // Declare and assign values to the form
            String formTitle = "Add";
            String formAction = "/debtor/add";

            // Return form
            return debtorFormPopulation(model, userModel, formTitle, formAction);
        }

        // Get user email from authentication context

       // Check whether the user is authenticated

        String userEmail = authentication.getName();

        // Get user from email
        User user = userRepository.findByUserEmail(userEmail).orElse(null);

        // Add debtor
        debtorService.addDebtor(userModel, user);

        // Redirect to the debtor dashboard after successful addition
        return "redirect:/dashboard/debtor";
    }

    @GetMapping("/{debtorId}/update")
    public String updateDebtor(@PathVariable String debtorId, UserModel userModel, Model model) {

        // Declare and assign values to the form
        String formTitle = "Update";
        String formAction = "/debtor/" + debtorId + "/update";

        // Return form
        return debtorFormPopulation(model, userModel, formTitle, formAction);
    }

    @PostMapping("/{debtorId}/update")
    public String updateDebtor(@PathVariable int debtorId,
                               @Validated(DebtorUpdateValidationGroup.class)
                               @ModelAttribute UserModel userModel,
                               BindingResult bindingResult, Model model) {

        // Check for binding errors
        if (bindingResult.hasErrors()) {

            // Declare and assign values to the form
            String formTitle = "Update";
            String formAction = "/debtor/" + debtorId + "/update";

            // Return form
            return debtorFormPopulation(model, userModel, formTitle, formAction);
        }

        // Find the existing debtor by Id

        Debtor debtor = debtorService.findDebtorById(debtorId);

        // Update Debtor

        if (debtor != null) {
            debtorService.updateDebtor(debtor, userModel);
        }

        return "redirect:/dashboard/debtor";
    }

    @GetMapping("/{debtorId}/delete")
    public String deleteDebtor(@PathVariable int debtorId) {

        // Find debtor by Id
        Debtor debtor = debtorService.findDebtorById(debtorId);

        // Delete debtor if found
        if (debtor != null) {
            debtorService.deleteDebtor(debtor);
        }

        // Redirect to the debtor dashboard after deletion
        return "redirect:/dashboard/debtor";
    }

    // Populates debtor form, for both 'add' and 'update' operations
    private String debtorFormPopulation(Model model, UserModel userModel, String formTitle, String formAction) {
        model.addAttribute("userModel", userModel);
        model.addAttribute("formTitle", formTitle);
        model.addAttribute("formAction", formAction);

        return "Forms/debtor-form";
    }
}
