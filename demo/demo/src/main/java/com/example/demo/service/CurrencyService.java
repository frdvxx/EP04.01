package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

    private final Map<String, Double> exchangeRates = Map.of(
            "USD", 1.0,
            "EUR", 0.93,
            "GBP", 0.80,
            "JPY", 149.0,
            "RUB", 92.0,
            "CNY", 7.18
    );

    public List<String> getAvailableCurrencies() {
        return Arrays.asList("USD", "EUR", "GBP", "JPY", "RUB", "CNY");
    }

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {

        double amountInUSD = amount / exchangeRates.get(fromCurrency);
        return amountInUSD * exchangeRates.get(toCurrency);
    }
}