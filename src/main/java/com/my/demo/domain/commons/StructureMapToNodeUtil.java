package com.my.demo.domain.commons;

import com.my.demo.domain.entities.Node;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * A helper utility class to change json object into a readable tree
 */
@ApplicationScoped
public class StructureMapToNodeUtil {

    /**
     * This method maps a dictionary to a name to {@link Node} map.
     *
     * @param inputStructure the input dictionary
     * @return the string to {@link Node} map
     */
    public Map<String, Node> mapToNode(Map<String, String> inputStructure) {
        Map<String, Node> tracker = new HashMap<>();

        inputStructure.forEach((key, value) -> {
            tracker.putIfAbsent(key, new Node(key));
            tracker.putIfAbsent(value, new Node(value));

            tracker.get(value).addChild(tracker.get(key));
        });

        return tracker;
    }
}
