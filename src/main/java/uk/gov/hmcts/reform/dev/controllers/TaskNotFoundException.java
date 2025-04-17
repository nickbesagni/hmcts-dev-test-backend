package uk.gov.hmcts.reform.dev.controllers;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
