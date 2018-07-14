package org.art.web.chat.web.responses;

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
