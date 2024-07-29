package com.arenema.codingtest.service;

import com.arenema.codingtest.model.RatesWrapper;

import java.util.List;

public interface IRateService {
    public List<RatesWrapper> getTimeseriesRatesFromBaseCurrency(final String targetCurrency);
    public RatesWrapper getAllRates();
}
