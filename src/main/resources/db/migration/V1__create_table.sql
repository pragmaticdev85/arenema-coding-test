CREATE TABLE rate (
    id INT not null primary key auto_increment,
    base_currency_code CHAR(3) not null,
    target_currency_code CHAR(3) not null,
    base_currency_value DOUBLE(16,2) not null,
    target_currency_value DOUBLE(16, 2) not null,
    revision_date DATE not null
);