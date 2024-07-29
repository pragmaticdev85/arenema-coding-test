package com.arenema.codingtest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"baseCurrencyCode", "targetCurrencyCode", "revisionDate"})})
@Entity(name = "rate")
@Data
public class RateEntity {
    private static final DateFormat STANDARD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double baseCurrencyValue;
    private double targetCurrencyValue;
    private Date revisionDate;

    public RateVO toVO() {
        return RateVO.builder()
                .baseCurrencyCode(baseCurrencyCode)
                .date(STANDARD_DATE_FORMAT.format(revisionDate))
                .conversionRatio(String.valueOf(targetCurrencyValue / baseCurrencyValue))
                .build();
    }
}
