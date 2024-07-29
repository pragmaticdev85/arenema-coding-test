package com.arenema.codingtest.service;

import com.arenema.codingtest.model.RateVO;

import java.util.List;

public interface RateService {
    List<RateVO> getLatestRatesForBaseCurrency(final String baseCurrency);
    List<RateVO> getLast3DayRatesForBaseCurrency(final String baseCurrency, final String targetCurrency);
}
