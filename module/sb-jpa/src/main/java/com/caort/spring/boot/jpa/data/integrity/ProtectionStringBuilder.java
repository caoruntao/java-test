package com.caort.spring.boot.jpa.data.integrity;

/**
 * @author caort
 * @date 2022/9/2 14:04
 */
public class ProtectionStringBuilder {

    private static final String SEPARATOR_TAG = "<sep/>";

    private final StringBuilder sb;

    public ProtectionStringBuilder() {
        sb = new StringBuilder();
    }

    public ProtectionStringBuilder(final int initialCapacity) {
        sb = new StringBuilder(initialCapacity);
    }

    public ProtectionStringBuilder append(final Object o) {
        if (sb.length() > 0) {
            sb.append(SEPARATOR_TAG);
        }
        // String.valueOf(o) would return "null" when null, which is not the same as null.
        if (o != null) {
            sb.append(o.toString());
        }
        return this;
    }

    public int length() {
        return sb.length();
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
