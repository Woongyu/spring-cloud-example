package com.platform.common.dto.enums;

import lombok.Getter;

@Getter
public enum Title {
    A("lorem"),
    B("ipsum"),
    C("dolor"),
    D("sit"),
    E("amet"),
    F("consectetur"),
    G("adipiscing"),
    H("elit"),
    I("Sed"),
    J("eiusmod"),
    K("tempor"),
    L("incididunt"),
    M("labore"),
    N("et"),
    O("dolore"),
    P("magna"),
    Q("aliqua"),
    R("ut"),
    S("enim"),
    T("minim"),
    U("veniam"),
    V("quis"),
    W("nostrud"),
    X("exercitation"),
    Y("ullamco"),
    Z("laboris");

    private final String value;

    Title(String value) {
        this.value = value;
    }
}
