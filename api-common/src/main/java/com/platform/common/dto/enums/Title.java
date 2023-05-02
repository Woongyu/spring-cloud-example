package com.platform.common.dto.enums;

import lombok.Getter;

@Getter
public enum Title {
    A("Lorem"),
    B("Ipsum"),
    C("Dolor"),
    D("Sit"),
    E("Amet"),
    F("Consectetur"),
    G("Adipiscing"),
    H("Elit"),
    I("Sed"),
    J("Eiusmod"),
    K("Tempor"),
    L("Incididunt"),
    M("Labore"),
    N("Et"),
    O("Dolore"),
    P("Magna"),
    Q("Aliqua"),
    R("Ut"),
    S("Enim"),
    T("Minim"),
    U("Veniam"),
    V("Quis"),
    W("Nostrud"),
    X("Exercitation"),
    Y("Ullamco"),
    Z("Laboris");

    private final String value;

    Title(String value) {
        this.value = value;
    }
}
