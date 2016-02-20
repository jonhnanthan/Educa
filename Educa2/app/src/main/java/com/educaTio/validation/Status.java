
package com.educaTio.validation;

public enum Status {
    NEW("New"), ANSWERED("Answered"), SUBMITTED("Submitted");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
