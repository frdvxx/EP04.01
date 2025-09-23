package com.example.demo.controller;

import com.example.demo.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final CurrencyService currencyService;

    public HomeController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/calculator")
    public String calculator() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam double num1,
            @RequestParam double num2,
            @RequestParam String operation,
            Model model) {

        double result = 0;
        String error = null;

        switch (operation) {
            case "add":
                result = num1 + num2;
                break;
            case "subtract":
                result = num1 - num2;
                break;
            case "multiply":
                result = num1 * num2;
                break;
            case "divide":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    error = "Деление на ноль невозможно";
                }
                break;
        }

        if (error != null) {
            model.addAttribute("error", error);
            return "calculator";
        }

        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("operation", operation);
        model.addAttribute("result", result);

        return "calculator-result";
    }

    @GetMapping("/currency-converter")
    public String currencyConverter(Model model) {
        model.addAttribute("currencies", currencyService.getAvailableCurrencies());
        return "currency-converter";
    }

    @PostMapping("/convert-currency")
    public String convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double amount,
            Model model) {

        double result = currencyService.convertCurrency(fromCurrency, toCurrency, amount);

        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("amount", amount);
        model.addAttribute("result", result);
        model.addAttribute("currencies", currencyService.getAvailableCurrencies());

        return "currency-converter";
    }
}