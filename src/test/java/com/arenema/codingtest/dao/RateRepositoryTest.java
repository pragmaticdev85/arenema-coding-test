package com.arenema.codingtest.dao;

import com.arenema.codingtest.model.RateEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class RateRepositoryTest {

    @Autowired
    private RateRepository rateRepository;

    @Test
    public void saveRateTest() {
        RateEntity rateEntity = RateEntity.builder()
                .baseCurrencyCode("USD")
                .baseCurrencyValue(1.0)
                .targetCurrencyCode("EUR")
                .targetCurrencyValue(1.09)
                .revisionDate(new Date(System.currentTimeMillis())).build();
        rateRepository.save(rateEntity);
        Assertions.assertThat(rateEntity.getId()).isGreaterThan(0);
    }

    @Test
    public void findByBaseCurrencyCodeAndTargetCurrencyCodeTest(){
        final Set<RateEntity> rateEeRateEntityList =
                rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode("USD", "EUR");
        Assertions.assertThat(rateEeRateEntityList.size()).isGreaterThan(0);
    }

    @Test
    public void findByBaseCurrencyCodeAndRevisionDateTest(){
        Date maxDate = rateRepository.findMaxRevisionDate();
        final Set<RateEntity> rateEeRateEntityList =
                rateRepository.findByBaseCurrencyCodeAndRevisionDate("USD", ObjectUtils.defaultIfNull(maxDate, Calendar.getInstance().getTime()));
        Assertions.assertThat(rateEeRateEntityList.size()).isGreaterThan(0);
    }
}
