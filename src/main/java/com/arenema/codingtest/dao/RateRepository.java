package com.arenema.codingtest.dao;

import com.arenema.codingtest.model.RateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RateRepository extends CrudRepository<RateEntity, Integer> {
    @Query("select o from rate o where o.baseCurrencyCode = ?1 and o.revisionDate = ?2")
    List<RateEntity> findByBaseCurrencyCode(final String baseCurrencyCode, final Date currentDate);
    @Query("select o from rate o where o.baseCurrencyCode = ?1 and o.targetCurrencyCode = ?2 and o.revisionDate between ?3 and ?4")
    List<RateEntity> findBaseCurrencyCodeAndTargetCurrencyCode(
            final String baseCurrencyCode, final String targetCurrencyCode, final Date _3DaysBackDate, final Date currentDate);
}
