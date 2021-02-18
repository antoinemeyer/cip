package com.teketik.cip;

import com.opencsv.bean.CsvBindByPosition;
import com.teketik.cip.CorrelationKey;

import java.util.Objects;

public class BEntry {

    @CsvBindByPosition(position = 0)
    @CorrelationKey
    private String key;

    @CsvBindByPosition(position = 1)
    private String value;

    public BEntry() {}

    public BEntry(String key, String value) {
        super();
        this.key = key;
        this.value = value;
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
        return "BEntry [key=" + key + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
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
        BEntry other = (BEntry) obj;
        return Objects.equals(key, other.key) && Objects.equals(value, other.value);
    }


}
