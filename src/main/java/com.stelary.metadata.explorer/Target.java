package com.stelary.metadata.explorer;

import java.io.PrintStream;

public interface Target {
    String getName();

    void print(PrintStream out);
}
