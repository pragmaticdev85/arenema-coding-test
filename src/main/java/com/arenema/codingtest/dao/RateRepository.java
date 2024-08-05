package com.arenema.codingtest.dao;

import com.arenema.codingtest.model.RateEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface RateRepository extends CrudRepository<RateEntity, Integer> {
    Set<RateEntity> findByBaseCurrencyCodeAndRevisionDate(final String baseCurrencyCode, final Date revisionDate);
    Set<RateEntity> findByBaseCurrencyCodeAndTargetCurrencyCode(
            final String baseCurrencyCode, final String targetCurrencyCode);

    RateEntity findTopByOrderByRevisionDate();

    default Date findMaxRevisionDate() {
        RateEntity top = findTopByOrderByRevisionDate();
        return top != null ? top.getRevisionDate() : null;
    }
}
