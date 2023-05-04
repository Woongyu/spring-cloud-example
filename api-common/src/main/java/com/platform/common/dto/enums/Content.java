package com.platform.common.dto.enums;

import lombok.Getter;

@Getter
public enum Content {
    AGRICULTURE("agriculture"),
    BANK("bank"),
    CURRENCY("currency"),
    DEBT("debt"),
    ECONOMY("economy"),
    FINANCE("finance"),
    GLOBALIZATION("globalization"),
    HOUSING("housing"),
    INFLATION("inflation"),
    JOB("job"),
    LABOR("labor"),
    MARKET("market"),
    NATIONAL_DEBT("national debt"),
    OPPORTUNITY_COST("opportunity cost"),
    PRODUCTION("production"),
    QUANTITY("quantity"),
    REVENUE("revenue"),
    STOCK("stock"),
    TAX("tax"),
    UNEMPLOYMENT("unemployment"),
    VALUE("value"),
    WEALTH("wealth"),
    XENOPHOBIA("xenophobia"),
    YIELD("yield"),
    ZERO_SUM("zero-sum");

    private final String value;

    Content(String value) {
        this.value = value;
    }
}
