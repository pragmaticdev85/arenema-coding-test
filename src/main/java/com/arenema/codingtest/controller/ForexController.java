package com.arenema.codingtest.controller;

import com.arenema.codingtest.model.RatesWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fx")
public class ForexController {
    @GetMapping("/{targetCurrency}")
    public List<RatesWrapper> getTimeseriesRatesFromBaseCurrency(@PathVariable("targetCurrency") final String targetCurrency) {
        return List.of(RatesWrapper.builder().build());
    }

    @GetMapping
    public RatesWrapper getLatestRatesFromBaseCurrency(@PathVariable("targetCurrency") final String targetCurrency) {
        return RatesWrapper.builder().build();
    }
}
