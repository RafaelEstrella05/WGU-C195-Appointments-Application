package edu.wgu.restrel.appointmentsapplication.Models;

public class ValidationException extends Exception {
    private String fieldName;

    public ValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getMessage() {
        return "Validation error for field '" + fieldName + "': " + super.getMessage();
    }
}
