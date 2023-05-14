package edu.wgu.restrel.appointmentsapplication.Interfaces;

import edu.wgu.restrel.appointmentsapplication.AppointmentsApplication;

public abstract class AppController {
    protected AppointmentsApplication app;

    /**
     * Sets a reference to the app from any Form Controller so that other Form
     * Controllers can be referenced if necessary
     * 
     * @param app
     */
    public void setApp(AppointmentsApplication app) {
        this.app = app;
    }

    /**
     * Getter for app
     */
    public AppointmentsApplication getApp() {
        return this.app;
    }

}
