package com.arenema.codingtest.dao;

import com.arenema.codingtest.model.RateEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

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

    public void findByBaseCurrencyCodeAndRevisionDateTest(){
        // rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode();
    }
}
