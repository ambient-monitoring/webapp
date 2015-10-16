package org.ambientmonitoring.webapp.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import org.ambientmonitoring.webapp.server.AmbientServiceImpl;
import org.ambientmonitoring.webapp.server.filter.NoCacheFilter;

import java.util.logging.Logger;

public class GuiceServletConfig extends GuiceServletContextListener {

    private Logger log = Logger.getLogger(getClass().getSimpleName());

    @Override
    protected Injector getInjector() {
        ServletModule servletModule = new ServletModule() {

            @Override
            protected void configureServlets() {
                // filters
                filter("/*").through(NoCacheFilter.class);

                // GWT RPC
                serve(AmbientServiceImpl.ENDPOINT).with(AmbientServiceImpl.class);
            }
        };

        return Guice.createInjector(Stage.PRODUCTION, servletModule);

    }
}
