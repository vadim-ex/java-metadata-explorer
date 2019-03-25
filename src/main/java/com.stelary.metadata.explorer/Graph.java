package com.stelary.metadata.explorer;

import lombok.var;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Graph {
    private final List<Target> targets = new ArrayList<>();
    private final Map<Object, Node> valueToTarget = new HashMap<>();

    Node getNode(Object value) {
        if (value == null) {
            return null;
        } else if (valueToTarget.containsKey(value)) {
            return valueToTarget.get(value);
        } else {
            var node = new Node(targets.size(), value);
            targets.add(node);
            valueToTarget.put(value, node);
            return node;
        }
    }

    Group getGroup() {
        var group = new Group(targets.size());
        targets.add(group);
        return group;
    }

    void print(PrintStream out) {
        out.printf("@startuml%n");
        for (var target : targets) {
            target.print(out);
        }
        out.printf("@enduml%n");
    }
}