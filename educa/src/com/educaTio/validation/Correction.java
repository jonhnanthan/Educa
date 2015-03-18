
package com.educaTio.validation;

public enum Correction {
    NOT_RATED("Not Rated"), RIGHT("Right"), WRONG("Wrong");

    private String value;

    private Correction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
