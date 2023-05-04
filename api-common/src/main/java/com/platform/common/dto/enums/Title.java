package com.platform.common.dto.enums;

import lombok.Getter;

@Getter
public enum Title {
    A("apple"),
    B("banana"),
    C("cherry"),
    D("date"),
    E("elderberry"),
    F("fig"),
    G("grape"),
    H("honeydew"),
    I("ice"),
    J("jujube"),
    K("kiwi"),
    L("lemon"),
    M("mango"),
    N("nectarine"),
    O("orange"),
    P("pear"),
    Q("quince"),
    R("raspberry"),
    S("strawberry"),
    T("tangerine"),
    U("umbrella"),
    V("yuzu"),
    W("watermelon"),
    X("xigua"),
    Y("yellow"),
    Z("zucchini");

    private final String value;

    Title(String value) {
        this.value = value;
    }
}
