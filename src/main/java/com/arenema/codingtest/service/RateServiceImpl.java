package com.arenema.codingtest.service;

import com.arenema.codingtest.dao.RateRepository;
import com.arenema.codingtest.model.RateEntity;
import com.arenema.codingtest.model.RateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl implements RateService {


    @Value("${default.base.currency.code}")
    private String defaultBaseCurrencyCode;

    @Autowired
    private RateRepository rateRepository;

    public List<RateVO> getLatestRatesForBaseCurrency(final String baseCurrency) {
        List<RateEntity> rateEntities = rateRepository.findByBaseCurrencyCode(
                StringUtils.defaultIfBlank(baseCurrency, defaultBaseCurrencyCode), Calendar.getInstance().getTime());
        return rateEntities.stream().map(RateEntity::toVO).collect(Collectors.toList());
    }
    public List<RateVO> getLast3DayRatesForBaseCurrency(final String baseCurrency, final String targetCurrency) {
        Calendar _3DaysBackCal = Calendar.getInstance();
        _3DaysBackCal.add(Calendar.DATE, -3);
        Date _3DaysBackDate = _3DaysBackCal.getTime();
        List<RateEntity> rateEntities = rateRepository.findBaseCurrencyCodeAndTargetCurrencyCode(
                StringUtils.defaultIfBlank(baseCurrency, defaultBaseCurrencyCode), targetCurrency, _3DaysBackDate, Calendar.getInstance().getTime());
        return rateEntities.stream().map(RateEntity::toVO).collect(Collectors.toList());
    }

}
