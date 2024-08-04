package com.arenema.codingtest.service;

import com.arenema.codingtest.dao.RateRepository;
import com.arenema.codingtest.model.ExternalResponseVO;
import com.arenema.codingtest.model.RateEntity;
import com.arenema.codingtest.model.RateVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RateServiceImpl implements RateService {


    public static final double BASE_CURRENCY_VALUE = 1.0;
    @Value("${default.base.currency.code}")
    private String defaultBaseCurrencyCode;

    @Value("${maximum.results.when.both.base.n.target.are.given:3}")
    private int maxResultsWhenBothCodesAreGiven;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private Gson gson;

    private static final DateFormat df = new SimpleDateFormat("YYYY-MM-dd");

    private String loadFromRemote(final String dateStr, final String baseCurrencyCode) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest todayData = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://api.frankfurter.app/%s?from=%s", dateStr, baseCurrencyCode)))
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build()
                .send(todayData, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void conditionalDataRefreshForLast3Days() throws URISyntaxException, IOException, InterruptedException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        final Set<RateEntity> consolidatedList = new HashSet<>();
        consolidatedList.addAll(refreshOnSpecificDate(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        consolidatedList.addAll(refreshOnSpecificDate(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        consolidatedList.addAll(refreshOnSpecificDate(calendar.getTime()));
        rateRepository.saveAll(consolidatedList);
    }

    private Set<RateEntity> refreshOnSpecificDate(Date specificDate) throws IOException, InterruptedException, URISyntaxException {
        final Set<RateEntity> results = rateRepository.findByBaseCurrencyCodeAndRevisionDate(defaultBaseCurrencyCode, specificDate);
        if (Objects.isNull(results) || results.isEmpty()) {
            final String responseStr = loadFromRemote(df.format(specificDate), defaultBaseCurrencyCode);
            if (StringUtils.isNotBlank(responseStr)) {
                ExternalResponseVO responseObj = gson.fromJson(responseStr, ExternalResponseVO.class);
                Set<RateEntity> set = new HashSet<>();
                if (responseObj.getRates().size() > 0) {
                    for (Map.Entry<String, Double> stringDoubleEntry : responseObj.getRates().entrySet()) {
                        set.add(new RateEntity((int) (Math.random() * 1_000_000),
                                responseObj.getBase(), stringDoubleEntry.getKey(), BASE_CURRENCY_VALUE, stringDoubleEntry.getValue(), specificDate));

                    }
                    return set;
                }
            }
        }
        return Collections.emptySet();
    }

    public Set<RateVO> getLatestRatesForBaseCurrency(final String baseCurrency) throws URISyntaxException, IOException, InterruptedException {
        Date maxDate = rateRepository.findMaxRevisionDate();
        Set<RateEntity> rateEntities = null;
        conditionalDataRefreshForLast3Days();
        if (Objects.isNull(maxDate) || ((rateEntities = rateRepository.findByBaseCurrencyCodeAndRevisionDate(
                    StringUtils.defaultIfBlank(baseCurrency, defaultBaseCurrencyCode), maxDate) ) != null && !rateEntities.isEmpty())){
            return Objects.nonNull(rateEntities) ? rateEntities.stream().map(RateEntity::toVO).collect(Collectors.toSet()) : Collections.emptySet();
        }
        return Collections.emptySet();
    }
    public Set<RateVO> getLast3DayRatesForBaseCurrency(final String baseCurrency, final String targetCurrency) throws URISyntaxException, IOException, InterruptedException {
        conditionalDataRefreshForLast3Days();
        Set<RateEntity> rateEntities = rateRepository.findByBaseCurrencyCodeAndTargetCurrencyCode(
                StringUtils.defaultIfBlank(baseCurrency, defaultBaseCurrencyCode), targetCurrency);
        return rateEntities.stream().map(RateEntity::toVO).limit(maxResultsWhenBothCodesAreGiven).collect(Collectors.toSet());
    }
}