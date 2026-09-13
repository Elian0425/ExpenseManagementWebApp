package com.expensemanagementapp.Controller.Dashboard;

import com.expensemanagementapp.Entity.*;
import com.expensemanagementapp.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/dashboard")
public class DebtorDashboardController {

    @Autowired
    private DebtorService debtorService;

    @Autowired
    DebtorDebtService debtorDebtService;
    @Autowired
    private DebtorDebtStatusService debtorDebtStatusService;
    @Autowired
    private DebtorDebtTypeService debtorDebtTypeService;
    @Autowired
    private DebtorDebtPaymentService debtorDebtPaymentService;

    @GetMapping("/debtor")
    public String debtorDashboard(Model model) {
        List<Debtor> debtorList = debtorService.getAllDebtorsDesc();
        model.addAttribute("debtorList", debtorList);
        return "Dashboard/debtor-dashboard";
    }

    @GetMapping("/debtor/{debtorId}/debt")
    public String debtorDebtDashboard(@PathVariable int debtorId, Model model) {

        List<DebtorDebt> debtorDebtList = debtorDebtService.findAllDebtorDebtsByDebtorId(debtorId);

        List<Double> debtorDebtPaymentTotalList = debtorDebtList.stream()
                .map(debtorDebt -> debtorDebtPaymentService.findAllDebtorDebtPaymentByDebtorDebt(debtorDebt).stream()
                        .mapToDouble(DebtorDebtPayment::getDebtorDebtPaymentAmount)
                        .sum())
                .toList();

        double debtorDebtTotal = debtorDebtList.stream()
                .mapToDouble(DebtorDebt::getDebtorDebtAmount)
                .sum();

        model.addAttribute("debtorDebtList", debtorDebtList);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtorDebtTotal", debtorDebtTotal);
        model.addAttribute("debtorDebtPaymentTotalList", debtorDebtPaymentTotalList);

        return "Dashboard/debtor-debt-dashboard";
    }

    @GetMapping("/debtor/{debtorId}/debt/status")
    public String debtorDebtStatusDashboard(@PathVariable String debtorId, Model model) {
        List<DebtorDebtStatus> debtorDebtStatusList = debtorDebtStatusService.findAllStatusesAsc();
        model.addAttribute("debtorDebtStatusList", debtorDebtStatusList);
        model.addAttribute("debtorId", debtorId);
        return "Dashboard/debtor-debt-status-dashboard";
    }

    @GetMapping("/debtor/{debtorId}/debt/type")
    public String debtorDebtTypeDashboard(@PathVariable String debtorId, Model model) {
        List<DebtorDebtType> debtorDebtTypeList = debtorDebtTypeService.findAllTypesAsc();
        model.addAttribute("debtorDebtTypeList", debtorDebtTypeList);
        model.addAttribute("debtorId", debtorId);
        return "Dashboard/debtor-debt-type-dashboard";
    }

}

