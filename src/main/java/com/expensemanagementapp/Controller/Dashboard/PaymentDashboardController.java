package com.expensemanagementapp.Controller.Dashboard;

import com.expensemanagementapp.Entity.DebtorDebt;
import com.expensemanagementapp.Entity.DebtorDebtPayment;
import com.expensemanagementapp.Model.DebtorDebtPaymentModel;
import com.expensemanagementapp.Service.DebtorDebtPaymentService;
import com.expensemanagementapp.Service.DebtorDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PaymentDashboardController {

    @Autowired
    private DebtorDebtService debtorDebtService;

    @Autowired
    private DebtorDebtPaymentService debtorDebtPaymentService;


    @GetMapping("/dashboard/debtor/{debtorId}/debt/{debtorDebtId}/payment")
    public String debtorDebtPaymentDashboard(@PathVariable int debtorId,
                                             @PathVariable int debtorDebtId,
                                             Model model) {

        DebtorDebt debtorDebt = debtorDebtService.findDebtorDebtById(debtorDebtId);

        List<DebtorDebtPayment> debtorDebtPaymentList = debtorDebtPaymentService.findAllDebtorDebtPaymentByDebtorDebt(debtorDebt);

        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtorDebtId", debtorDebtId);
        model.addAttribute("debtorDebtPaymentList", debtorDebtPaymentList);


        return "Dashboard/debtor-debt-payment-dashboard";
    }
}
