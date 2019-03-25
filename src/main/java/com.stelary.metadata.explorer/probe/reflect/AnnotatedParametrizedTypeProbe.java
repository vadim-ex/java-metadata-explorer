package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.AnnotatedParameterizedType;

public class AnnotatedParametrizedTypeProbe extends Probe<AnnotatedParameterizedType> {
    @Override
    public void getAttributes(Yield yield, AnnotatedParameterizedType value) {
        if (cliOptions.annotation) {
            yield.apply(value.getAnnotatedActualTypeArguments(), "annotatedActualTypeArguments");
        }
    }
}
