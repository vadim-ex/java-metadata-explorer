package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.AnnotatedElement;

public class AnnotatedElementProbe extends Probe<AnnotatedElement> {
    @Override
    public void getAttributes(Yield yield, AnnotatedElement value) {
        if (cliOptions.annotation) {
            yield.apply(value.getAnnotations(), "annotations");
            yield.apply(value.getDeclaredAnnotations(), "declaredAnnotations");
        }
    }
}
