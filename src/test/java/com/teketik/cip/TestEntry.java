package com.teketik.cip;

abstract class TestEntry {

    @CorrelationKey
    private String key;

    public TestEntry(String key) {
        super();
        this.key = key;
    }

}
