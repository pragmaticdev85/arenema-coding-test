package com.arenema.codingtest.controller;

import com.arenema.codingtest.model.RateVO;
import com.arenema.codingtest.service.RateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fx")
public class ForexController {

    @Autowired
    private RateService rateService;

    @GetMapping("/{targetCurrency}")
    public List<RateVO> getTimeseriesRatesFromBaseCurrency(@RequestParam(value = "baseCurrency", required = false) final String baseCurrency,
                                                           @PathVariable(value = "targetCurrency", required = true) final String targetCurrency) {
        return rateService.getLast3DayRatesForBaseCurrency(StringUtils.trimToEmpty(baseCurrency), StringUtils.trimToEmpty(targetCurrency));
    }

    @GetMapping
    public List<RateVO> getLatestRatesFromBaseCurrency(@RequestParam(value = "baseCurrency", required = false) final String baseCurrency) {
        return rateService.getLatestRatesForBaseCurrency(StringUtils.trimToEmpty(baseCurrency));
    }
}
