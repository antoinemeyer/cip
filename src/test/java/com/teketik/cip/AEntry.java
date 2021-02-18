package com.teketik.cip;

import com.opencsv.bean.CsvBindByPosition;
import com.teketik.cip.CorrelationKey;

import java.util.Objects;

public class AEntry {

    @CsvBindByPosition(position = 0)
    private String common;

    @CsvBindByPosition(position = 1)
    @CorrelationKey
    private String key;

    @CsvBindByPosition(position = 2)
    private String value;

    public AEntry() {}

    public AEntry(String common, String key, String value) {
        super();
        this.common = common;
        this.key = key;
        this.value = value;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AEntry [common=" + common + ", key=" + key + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(common, key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AEntry other = (AEntry) obj;
        return Objects.equals(common, other.common) && Objects.equals(key, other.key) && Objects.equals(value, other.value);
    }



}
