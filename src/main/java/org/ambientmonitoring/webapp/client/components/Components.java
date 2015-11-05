package org.ambientmonitoring.webapp.client.components;

import java.util.HashMap;
import java.util.Map;

public class Components {

    private static Components components;
    private Map<String, ComponentFactory> compMap;

    protected Components() {
        compMap = new HashMap<String, ComponentFactory>();
    }

    public void register(ComponentFactory component) {
        compMap.put(component.getComponentId(), component);
    }

    public ComponentFactory getComponent(String componentId) {
        return compMap.get(componentId);
    }

    public static Components getInstance() {
        if (components == null) {
            components = new Components();
        }

        return components;
    }


}
