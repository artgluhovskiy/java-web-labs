package org.art.java_web.labs.web.responses;

public enum ResponseStatus {

    OK("OK"), FAIL("FAIL");

    String status;

    ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
