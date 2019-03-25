package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.AnnotatedType;

public class AnnotatedTypeProbe extends Probe<AnnotatedType> {
    @Override
    public void getAttributes(Yield yield, AnnotatedType value) {
        if (cliOptions.annotation) {
            // yield.apply(value.getAnnotatedOwnerType(), "annotatedOwnerType"); -- v9+
            yield.apply(value.getType(), "type");
        }
    }
}