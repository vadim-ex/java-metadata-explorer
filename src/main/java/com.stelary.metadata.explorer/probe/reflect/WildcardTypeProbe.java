package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.WildcardType;

public class WildcardTypeProbe extends Probe<WildcardType> {
    @Override
    public void getAttributes(Yield yield, WildcardType value) {
        if (cliOptions.generic) {
            yield.apply(value.getUpperBounds(), "upperBounds");
            yield.apply(value.getLowerBounds(), "lowerBounds");
        }
    }
}