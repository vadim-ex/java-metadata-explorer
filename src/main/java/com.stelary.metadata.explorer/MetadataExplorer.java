package com.stelary.metadata.explorer;

import lombok.var;

import java.util.*;

class MetadataExplorer {

    private final Deque<Node> todo = new ArrayDeque<>();
    private final Graph graph;
    private final Set<Node> visited = new HashSet<>();
    private final CliOptions cliOptions;
    private final Probe[] probes;

    MetadataExplorer(Graph graph, CliOptions cliOptions, Probe[] probes) {
        this.graph = graph;
        this.cliOptions = cliOptions;
        this.probes = probes;
    }

    private void link(Node source, Object value, String name) {
        var target = graph.getNode(value);
        source.link(target, name);
        if (visited.contains(target)) {
            return;
        }
        todo.add(target);
    }

    private <T> void multilink(Node source, T[] targets, String name) {
        if (targets.length == 0) {
            return;
        }
        if (targets.length == 1) {
            link(source, targets[0], name);
        } else {
            var group = graph.getGroup();
            source.link(group, name);
            for (var target : targets) {
                var targetNode = graph.getNode(target);
                if (!visited.contains(targetNode)) {
                    todo.add(targetNode);
                }
                group.getTargets().add(targetNode);
            }
        }
    }

    private void attribute(Node node, Object attribute, String attributeName) {
        if (attribute == null) {
            return;
        }
        var attributeClass = attribute.getClass();
        if (attributeClass == Boolean.class || attributeClass == Integer.class ||
                attributeClass == Long.class || attributeClass == String.class) {
            node.attribute(attribute, attributeName);
        } else if (attribute instanceof Optional) {
            if (((Optional) attribute).isPresent()) {
                attribute(node, ((Optional) attribute).get(), attributeName);
            }
        } else if (attributeClass.isArray()) {
            multilink(node, (Object[]) attribute, attributeName);
        } else if (attribute instanceof Collection) {
            multilink(node, ((Collection) attribute).toArray(), attributeName);
        } else {
            link(node, attribute, attributeName);
        }
    }

    void explore(Class value) {
        var node = graph.getNode(value);
        node.setStartClass(true);
        todo.add(node);

        processQueue();
    }

    private void processQueue() {
        while (!todo.isEmpty()) {
            var node = todo.getFirst();
            todo.removeFirst();
            if (visited.contains(node)) {
                continue;
            }
            visited.add(node);
            var value = node.getTarget();
            for (var probe : probes) {
                if (probe.recognizes(value)) {
                    if (cliOptions.trace) {
                        attribute(node, probe.getClass().getSimpleName(), "-trace");
                    }
                    node.setStem(probe.getStem(value));
                    node.setTag(probe.getTag());
                    probe.getAttributes((attribute, attributeName) -> attribute(node, attribute, attributeName), value);
                }
            }
            node.setTag("unrecognized"); // sets default, since latched if recognized by any
            if (cliOptions.className) {
                attribute(node, value.getClass().getSimpleName(), "-class");
            }
        }
    }
}
