package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.AnnotatedArrayType;

public class AnnotatedArrayTypeProbeV9 extends Probe<AnnotatedArrayType> {
    @Override
    public void getAttributes(Yield yield, AnnotatedArrayType value) {
        if (cliOptions.annotation) {
            yield.apply(value.getAnnotatedGenericComponentType(), "getAnnotatedGenericComponentType");
        }
    }
}
