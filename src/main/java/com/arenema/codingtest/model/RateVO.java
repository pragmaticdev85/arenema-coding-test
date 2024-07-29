package com.arenema.codingtest.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
public class RateVO {
    private String date;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private String conversionRatio;
}