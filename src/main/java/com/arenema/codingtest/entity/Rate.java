package com.arenema.codingtest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity(name = "rate")
public class Rate {
    @Id
    private String id;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private double baseCurrencyValue;
    private double targetCurrencyValue;
    private Date date;
}
