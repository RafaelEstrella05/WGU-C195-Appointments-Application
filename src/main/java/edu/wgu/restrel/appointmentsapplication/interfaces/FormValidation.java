package edu.wgu.restrel.appointmentsapplication.interfaces;

import edu.wgu.restrel.appointmentsapplication.Models.FormValidationException;
import edu.wgu.restrel.appointmentsapplication.Models.FormValidationState;

/**
 * Interface for form validation
 * Controllers that implement this interface will have a method to validate
 * their own form input
 */
public interface FormValidation {

    FormValidationState getFormInputValidationState() throws FormValidationException;


}
