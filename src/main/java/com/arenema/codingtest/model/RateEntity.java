package com.arenema.codingtest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"base_currency_code", "target_currency_code", "revision_date"})})
@Entity(name = "rate")
@Data
@Builder
@AllArgsConstructor
public class RateEntity implements Serializable {
    private static final DateFormat STANDARD_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Id
    @SequenceGenerator(name="Event_Seq", sequenceName="Event_Seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Event_Seq")
    private Integer id;
    @Column(name = "base_currency_code")
    private String baseCurrencyCode;
    @Column(name = "target_currency_code")
    private String targetCurrencyCode;
    @Column(name = "base_currency_value")
    private double baseCurrencyValue;
    @Column(name = "target_currency_value")
    private double targetCurrencyValue;
    @Temporal(TemporalType.DATE)
    @Column(name = "revision_date")
    private Date revisionDate;

    public RateEntity() {
    }

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
