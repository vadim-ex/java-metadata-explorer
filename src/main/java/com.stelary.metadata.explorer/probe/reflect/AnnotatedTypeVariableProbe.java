package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.AnnotatedTypeVariable;

public class AnnotatedTypeVariableProbe extends Probe<AnnotatedTypeVariable> {
    @Override
    public void getAttributes(Yield yield, AnnotatedTypeVariable value) {
        if (cliOptions.annotation) {
            yield.apply(value.getAnnotatedBounds(), "annotatedBounds");
        }
    }
}