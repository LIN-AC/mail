package listener;


import tool.PropertiesTool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class ApplicationListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent Event) {
        Event.getServletContext().setAttribute("file_server", PropertiesTool.getValue("file_server"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent Event) {

    }
}
