package com.stelary.metadata.explorer.util;

@FunctionalInterface
public interface Yield {
    void apply(Object value, String name);
}
