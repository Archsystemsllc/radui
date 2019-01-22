package com.archsystemsinc.rad.configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session creation.");
        event.getSession().setMaxInactiveInterval(60*10);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
         System.out.println("Session termination.");
    }
}
