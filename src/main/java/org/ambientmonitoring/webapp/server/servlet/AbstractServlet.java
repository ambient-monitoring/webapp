package org.ambientmonitoring.webapp.server.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.logging.Logger;

public abstract class AbstractServlet extends HttpServlet {

    private Logger log = Logger.getLogger(getClass().getSimpleName());

    public String getPostContent(HttpServletRequest req) throws IOException {
        StringBuilder jb = new StringBuilder();
        BufferedReader reader = req.getReader();

        String line;

        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        return jb.toString();
    }

    public Gson getGson() {
        return new Gson();
    }

    public void writeResponse(String response, HttpServletResponse resp, boolean print) throws IOException {
        OutputStream os = new BufferedOutputStream(resp.getOutputStream());

        PrintStream ps = new PrintStream(os);

        ps.print(response);
        ps.close();

        if (print) {
            log.info("response = " + response);
        }
    }
}
