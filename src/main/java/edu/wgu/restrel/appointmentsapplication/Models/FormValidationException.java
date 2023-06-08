package edu.wgu.restrel.appointmentsapplication.Models;

public class FormValidationException extends Exception {
    private String fieldName;

    public FormValidationException(String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getMessage() {
        return "Validation error: " + super.getMessage();
    }
}
