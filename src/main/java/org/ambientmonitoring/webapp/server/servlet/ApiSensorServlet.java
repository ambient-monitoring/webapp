package org.ambientmonitoring.webapp.server.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.ambientmonitoring.webapp.server.manager.ReadingManager;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Singleton
public class ApiSensorServlet extends AbstractServlet {

    public static final String ENDPOINT = "/api/sensor";

    private Logger log = Logger.getLogger(getClass().getSimpleName());

    private final ReadingManager rm;

    // todo proper REST

    @Inject
    public ApiSensorServlet(ReadingManager rm) {
        this.rm = rm;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer sensorId = Integer.valueOf(req.getParameter("id"));

        ReadingRPC reading = rm.getLastReading(sensorId);

        writeResponse(getGson().toJson(reading), resp, false);
    }
}
