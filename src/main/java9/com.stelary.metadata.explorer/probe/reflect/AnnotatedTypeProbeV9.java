package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.AnnotatedType;

public class AnnotatedTypeProbeV9 extends AnnotatedTypeProbe {
    @Override
    public void getAttributes(Yield yield, AnnotatedType value) {
        super.getAttributes(yield, value);
        if (cliOptions.annotation) {
            yield.apply(value.getAnnotatedOwnerType(), "annotatedOwnerType");
        }
    }
}