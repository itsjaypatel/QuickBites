package com.itsjaypatel.quickbites.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NotificationPayLoadException extends RuntimeException {

    private final List<String> subErrors;

    public NotificationPayLoadException(String message, List<String> subErrors) {
        super(message);
        this.subErrors = subErrors;
    }

    public NotificationPayLoadException(String message) {
        super(message);
        this.subErrors = new ArrayList<>();
    }
}
