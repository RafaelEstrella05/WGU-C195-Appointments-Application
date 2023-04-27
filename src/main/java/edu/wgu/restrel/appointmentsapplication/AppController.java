package edu.wgu.restrel.appointmentsapplication;

public abstract class AppController {
    protected AppointmentsApplication app;

    /**
     * Sets a reference to the app from any Form Controller so that other Form Controllers can be referenced if necessary
     * @param app
     */
    public void setApp(AppointmentsApplication app){
        this.app = app;
    }



}
