package com.arenema.codingtest.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
public class RatesWrapper {
    private double amount;
    private CountryCode baseCountryCode;
    private Date date;
    private Map<CountryCode, Double> rates;
}