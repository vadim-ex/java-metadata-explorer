package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.Type;

public class TypeProbe extends Probe<Type> {
    @Override
    public void getAttributes(Yield yield, Type value) {
        if (cliOptions.generic) {
            yield.apply(value.getTypeName(), "typeName");
        }
    }
}