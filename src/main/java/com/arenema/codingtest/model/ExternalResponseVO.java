package com.arenema.codingtest.model;

import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class ExternalResponseVO {
    private double amount;
    private String base;
    private String date;
    private Map<String, Double> rates = new HashMap<>();
}
