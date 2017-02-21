package org.ambientmonitoring.webapp.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import org.ambientmonitoring.webapp.server.filter.NoCacheFilter;
import org.ambientmonitoring.webapp.server.rpc.AmbientServiceImpl;
import org.ambientmonitoring.webapp.server.servlet.ApiSensorServlet;

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

                // api
                serve(ApiSensorServlet.ENDPOINT).with(ApiSensorServlet.class);
            }
        };

        return Guice.createInjector(Stage.PRODUCTION, servletModule);

    }
}
