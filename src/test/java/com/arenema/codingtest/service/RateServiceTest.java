package com.arenema.codingtest.service;

import com.arenema.codingtest.dao.RateRepository;
import com.arenema.codingtest.model.RateEntity;
import com.arenema.codingtest.model.RateVO;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
public class RateServiceTest {
    @InjectMocks
    private RateServiceImpl rateService;

    @Mock
    private RateRepository rateRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(true);
    }

    @Test
    public void testGetLast3DayRatesForBaseCurrency() throws URISyntaxException, IOException, InterruptedException {
        Set<RateEntity> rateEntitySet = new HashSet<>();
        Date today = new Date(System.currentTimeMillis());
        Date yesterday = DateUtils.addDays(today, -1);
        Date dayBeforeYesterday = DateUtils.addDays(today, -2);
        Date _2daysBeforeYesterday = DateUtils.addDays(today, -3);

        rateEntitySet.add(RateEntity.builder().
                baseCurrencyCode("USD")
                .baseCurrencyValue(1.0)
                .targetCurrencyCode("EUR")
                .targetCurrencyValue(1.09)
                .revisionDate(today).build());
        rateEntitySet.add(RateEntity.builder().
                baseCurrencyCode("USD")
                .baseCurrencyValue(1.0)
                .targetCurrencyCode("EUR")
                .targetCurrencyValue(1.07)
                .revisionDate(yesterday).build());
        rateEntitySet.add(RateEntity.builder().
                baseCurrencyCode("USD")
                .baseCurrencyValue(1.0)
                .targetCurrencyCode("EUR")
                .targetCurrencyValue(1.085)
                .revisionDate(dayBeforeYesterday).build());
        rateEntitySet.add(RateEntity.builder().
                baseCurrencyCode("USD")
                .baseCurrencyValue(1.0)
                .targetCurrencyCode("EUR")
                .targetCurrencyValue(1.065)
                .revisionDate(_2daysBeforeYesterday).build());
        when(rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(anyString(), anyString())).thenReturn(rateEntitySet);
        ReflectionTestUtils.setField(rateService, "defaultBaseCurrencyCode", "USD");
        ReflectionTestUtils.setField(rateService, "maxResultsWhenBothCodesAreGiven", 3);
        Set<RateVO> rateVOSet =  rateService.getLast3DayRatesForBaseCurrency("USD", "EUR");
        Assertions.assertEquals(3, rateVOSet.size());
    }

    @Test
    public void testGetLatestRatesForBaseCurrency() throws URISyntaxException, IOException, InterruptedException {
        Set<RateEntity> rateEntitySet = new HashSet<>();
        Date today = new Date(System.currentTimeMillis());
        rateEntitySet.add(RateEntity.builder().
                baseCurrencyCode("USD")
                .baseCurrencyValue(1.0)
                .targetCurrencyCode("EUR")
                .targetCurrencyValue(1.09)
                .revisionDate(today).build());
        when(rateRepository.findByBaseCurrencyCodeAndRevisionDate(anyString(), any())).thenReturn(rateEntitySet);
        when(rateRepository.findMaxRevisionDate()).thenReturn(today);
        ReflectionTestUtils.setField(rateService, "defaultBaseCurrencyCode", "USD");
        ReflectionTestUtils.setField(rateService, "maxResultsWhenBothCodesAreGiven", 3);
        Set<RateVO> rateVOSet =  rateService.getLatestRatesForBaseCurrency("USD");
        Assertions.assertEquals(1, rateVOSet.size());
    }

}
