package com.arenema.codingtest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"baseCurrencyCode", "targetCurrencyCode", "revisionDate"})})
@Entity(name = "rate")
@Data
@AllArgsConstructor
public class RateEntity {
    private static final DateFormat STANDARD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Id
    private Integer id;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double baseCurrencyValue;
    private double targetCurrencyValue;
    @Temporal(TemporalType.DATE)
    private Date revisionDate;

    public RateEntity(){}

    public RateVO toVO() {
        return RateVO.builder()
                .baseCurrencyCode(baseCurrencyCode)
                .targetCurrencyCode(targetCurrencyCode)
                .date(STANDARD_DATE_FORMAT.format(revisionDate))
                .conversionRatio(String.valueOf(targetCurrencyValue / baseCurrencyValue))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateEntity that = (RateEntity) o;
        return Double.compare(baseCurrencyValue, that.baseCurrencyValue) == 0 && Double.compare(targetCurrencyValue, that.targetCurrencyValue) == 0 && Objects.equals(baseCurrencyCode, that.baseCurrencyCode) && Objects.equals(targetCurrencyCode, that.targetCurrencyCode) && Objects.equals(revisionDate, that.revisionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrencyCode, targetCurrencyCode, baseCurrencyValue, targetCurrencyValue, revisionDate);
    }
}
