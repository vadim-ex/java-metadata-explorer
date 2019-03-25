package com.stelary.metadata.explorer;

import com.stelary.metadata.explorer.util.Pair;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.var;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class Node implements Target {
    @Getter
    private final int id;
    @Getter
    private final Object target;

    @Getter
    @Setter
    private boolean startClass = false;
    @Getter
    private List<Pair<String, Object>> attributes = new ArrayList<>();
    @Getter
    private List<Edge> edges = new ArrayList<>();

    // latching attributes: set only once
    private String stem = null;
    private String tag = null;

    Node(int id, Object target) {
        this.id = id;
        this.target = target;
    }

    void link(@NonNull Target target, String name) {
        assert target != null;
        var edge = new Edge(name, target);
        edges.add(edge);
    }

    void attribute(@NonNull Object value, String name) {
        assert value != null;
        attributes.add(new Pair<>(name, value));
    }

    public void setStem(String stem) {
        if (this.stem == null && stem != null) {
            this.stem = stem;
        }
    }

    public void setTag(String tag) {
        if (this.tag == null && tag != null) {
            this.tag = tag;
        }
    }

    @Override
    public String getName() {
        String stem = this.stem != null ? this.stem : target.toString();
        return String.format("\"%s@%04d\"", stem, id);
    }

    @Override
    public void print(PrintStream out) {
        var nodeName = getName();
        if (tag.equals("unrecognized")) {
            out.printf("class %s << (U,red) >> {%n", nodeName);
        } else {
            out.printf("%s %s {%n", tag, nodeName);
        }
        for (var entry : attributes) {
            if (!(entry.getValue() instanceof Boolean) || !entry.getKey().startsWith("is")) {
                out.printf("%s = %s%n", entry.getKey(), entry.getValue());
            } else if ((Boolean) entry.getValue() &&
                    !entry.getKey().equals("isInterface") &&
                    !entry.getKey().equals("isEnum")) {
                out.printf("%s%n", entry.getKey());
            }
        }
        out.printf("}%n");
        for (var edge : edges) {
            out.printf("%s --> %s : %s%n", nodeName, edge.getTo().getName(), edge.getName());
        }
        out.printf("%n");
    }
}
