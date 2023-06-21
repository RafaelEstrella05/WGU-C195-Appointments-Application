package edu.wgu.restrel.appointmentsapplication.Models;

/**
 * FormValidationException class for handling form validation errors
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class FormValidationException extends Exception {
    private String fieldName;

    /**
     * Constructor
     * 
     * @param message
     */
    public FormValidationException(String message) {
        super(message);
        this.fieldName = fieldName;
    }

    /**
     * Getter for fieldName
     * 
     * @return fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getMessage() {
        return "Validation error: " + super.getMessage();
    }
}
