package org.ambientmonitoring.webapp.shared.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

    public static final String PARAMETER_SEPARATOR = "/";

    private HistoryHelper() {
    }

    public static String encode(List<String> parameters) {
        StringBuilder sb = new StringBuilder();

        for (String parameter : parameters) {
            sb.append(parameter);
            sb.append(PARAMETER_SEPARATOR);
        }

        String result = sb.toString();

        return result.substring(0, result.lastIndexOf(PARAMETER_SEPARATOR));
    }

    public static List<String> decode(String token) {
        if (token == null || token.trim().length() == 0) {
            return new ArrayList<>();
        }

        List<String> parameters = new ArrayList<>();

        token = token.trim();

        String[] params = token.split(HistoryHelper.PARAMETER_SEPARATOR);

        for (int i = 0; i < params.length; i++) {
            String param = params[i];

            parameters.add(param);
        }

        return parameters;
    }
}
