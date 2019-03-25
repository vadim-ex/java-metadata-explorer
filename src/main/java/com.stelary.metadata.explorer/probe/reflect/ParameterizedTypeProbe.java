package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.ParameterizedType;

public class ParameterizedTypeProbe extends Probe<ParameterizedType> {
    @Override
    public void getAttributes(Yield yield, ParameterizedType value) {
        if (cliOptions.generic) {
            yield.apply(value.getActualTypeArguments(), "actualTypeArguments");
            yield.apply(value.getRawType(), "rawType");
            yield.apply(value.getOwnerType(), "ownerType");
        }
    }
}