package com.practice.bank.web;



import com.practice.bank.Bank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Bank bank;

    public HomeController(Bank bank) {
        this.bank = bank;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("bankName", bank.getName());
        model.addAttribute("accounts", bank.getAllAccounts());
        model.addAttribute("total", bank.getTotalDeposits());
        model.addAttribute("customers", bank.getHolderNames());
        return "home";
    }
}
