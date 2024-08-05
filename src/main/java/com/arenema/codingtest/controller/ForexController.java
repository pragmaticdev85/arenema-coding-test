package com.arenema.codingtest.controller;

import com.arenema.codingtest.model.RateVO;
import com.arenema.codingtest.service.RateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping("/fx")
public class ForexController {


    private RateService rateService;

    @Autowired
    public ForexController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/{targetCurrency}")
    public Set<RateVO> getTimeseriesRatesFromBaseCurrency(@RequestParam(value = "baseCurrency", required = false) final String baseCurrency,
                                                          @PathVariable(value = "targetCurrency", required = true) final String targetCurrency) throws URISyntaxException, IOException, InterruptedException {
        return rateService.getLast3DayRatesForBaseCurrency(StringUtils.trimToEmpty(baseCurrency), StringUtils.trimToEmpty(targetCurrency));
    }

    @GetMapping
    public Set<RateVO> getLatestRatesFromBaseCurrency(@RequestParam(value = "baseCurrency", required = false) final String baseCurrency) throws URISyntaxException, IOException, InterruptedException {
        return rateService.getLatestRatesForBaseCurrency(StringUtils.trimToEmpty(baseCurrency));
    }
}
