package com.teketik.cip;

import com.teketik.cip.CorrelationKey;

abstract class TestEntry {

    @CorrelationKey
    private String key;

    public TestEntry(String key) {
        super();
        this.key = key;
    }

}
