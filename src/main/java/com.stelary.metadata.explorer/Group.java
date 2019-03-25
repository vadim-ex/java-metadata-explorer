package com.stelary.metadata.explorer;

import lombok.Getter;
import lombok.var;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class Group implements Target {
    @Getter
    private final int id;
    @Getter
    private final List<Node> targets = new ArrayList<>();

    Group(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return String.format("\"[%04d]\"", id);
    }

    @Override
    public void print(PrintStream out) {
        var nodeName = getName();
        out.printf("class %s << (G,#CCCCFF) >> #CCCCFF {%n", nodeName);
        out.printf("}%n");
        for (var target : targets) {
            out.printf("%s --> %s%n", nodeName, target.getName());
        }
        out.printf("%n");
    }
}
