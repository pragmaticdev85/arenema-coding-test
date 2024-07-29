CREATE TABLE rate (
    id INT not null primary key auto_increment,
    baseCurrencyCode CHAR(3) not null,
    targetCurrencyCode CHAR(3) not null,
    baseCurrencyValue DOUBLE(16,2) not null,
    targetCurrencyValue DOUBLE(16, 2) not null,
    revisionDate DATE not null
);