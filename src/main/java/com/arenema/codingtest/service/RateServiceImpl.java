package com.arenema.codingtest.service;

import com.arenema.codingtest.dao.RateRepository;
import com.arenema.codingtest.model.RateEntity;
import com.arenema.codingtest.model.RateVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RateServiceImpl implements RateService {


    @Value("${default.base.currency.code}")
    private String defaultBaseCurrencyCode;

    @Value("${maximum.results.when.both.base.n.target.are.given:3}")
    private int maxResultsWhenBothCodesAreGiven;

    @Autowired
    private RateRepository rateRepository;

    public List<RateVO> getLatestRatesForBaseCurrency(final String baseCurrency) {
        Date maxDate = rateRepository.findMaxRevisionDate();
        List<RateEntity> rateEntities = rateRepository.findByBaseCurrencyCodeAndRevisionDate(
                    StringUtils.defaultIfBlank(baseCurrency, defaultBaseCurrencyCode), maxDate);
        return rateEntities.stream().map(RateEntity::toVO).toList();
    }
    public List<RateVO> getLast3DayRatesForBaseCurrency(final String baseCurrency, final String targetCurrency) {
        List<RateEntity> rateEntities = rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                StringUtils.defaultIfBlank(baseCurrency, defaultBaseCurrencyCode), targetCurrency);
        return rateEntities.stream().map(RateEntity::toVO).limit(maxResultsWhenBothCodesAreGiven).collect(Collectors.toList());
    }
}