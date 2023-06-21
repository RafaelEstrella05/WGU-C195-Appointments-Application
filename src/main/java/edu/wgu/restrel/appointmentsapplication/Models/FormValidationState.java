package edu.wgu.restrel.appointmentsapplication.Models;

/**
 * Class for storing the state of form validation for any form
 * helps keep track of whether or not the form is valid and if not, why
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class FormValidationState {

    private boolean isValid;
    private String message;

    /**
     * Constructor
     * 
     * @param isValid
     * @param message
     */
    public FormValidationState(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    /**
     * Getter for isValid
     * 
     * @return isValid
     */

    public boolean isValid() {
        return isValid;
    }

    /**
     * Setter for isValid
     * 
     * @param isValid
     */
    public void setisValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * Getter for message
     * 
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for message
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;

    }

}
