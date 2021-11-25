package com.caort.verify;

/**
 * @author Reed
 * @date 2021/8/12 上午9:42
 */
public class Bean {
    private int value;

    public Bean(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "value=" + value +
                '}';
    }
}
