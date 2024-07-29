package com.arenema.codingtest.controller;

import com.arenema.codingtest.model.RatesWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fx")
public class ForexController {
    @RequestMapping("/{targetCurrency}")
    public List<RatesWrapper> getTimeseriesRatesFromBaseCurrency(@PathVariable("targetCurrency") final String targetCurrency) {


        return null;
    }
}
