package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.AnnotatedWildcardType;

public class AnnotatedWildcardTypeProbe extends Probe<AnnotatedWildcardType> {
    @Override
    public void getAttributes(Yield yield, AnnotatedWildcardType value) {
        if (cliOptions.annotation) {
            yield.apply(value.getAnnotatedLowerBounds(), "annotatedLowerBounds");
            yield.apply(value.getAnnotatedUpperBounds(), "annotatedUpperBounds");
        }
    }
}