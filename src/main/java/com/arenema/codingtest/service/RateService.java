package com.arenema.codingtest.service;

import com.arenema.codingtest.model.RateVO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public interface RateService {
    Set<RateVO> getLatestRatesForBaseCurrency(final String baseCurrency) throws URISyntaxException, IOException, InterruptedException;
    Set<RateVO> getLast3DayRatesForBaseCurrency(final String baseCurrency, final String targetCurrency) throws URISyntaxException, IOException, InterruptedException;
}
