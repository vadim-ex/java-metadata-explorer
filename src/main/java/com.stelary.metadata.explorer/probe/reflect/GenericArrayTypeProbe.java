package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.GenericArrayType;

public class GenericArrayTypeProbe extends Probe<GenericArrayType> {
    @Override
    public void getAttributes(Yield yield, GenericArrayType value) {
        yield.apply(value.getGenericComponentType(), "genericComponentType");
    }
}
