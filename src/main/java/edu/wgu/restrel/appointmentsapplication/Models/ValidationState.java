package edu.wgu.restrel.appointmentsapplication.Models;

public class ValidationState {

    private boolean isValid;
    private String message;

    /**
     * Constructor
     * 
     * @param isValid
     * @param message
     */
    public ValidationState(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    /**
     * Getter for isValid
     * 
     * @return
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
