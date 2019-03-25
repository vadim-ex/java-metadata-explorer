package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.TypeVariable;

public class TypeVariableProbe extends Probe<TypeVariable> {
    @Override
    public void getAttributes(Yield yield, TypeVariable value) {
        if (cliOptions.generic) {
            yield.apply(value.getName(), "name");
            yield.apply(value.getGenericDeclaration(), "genericDeclaration");
            yield.apply(value.getBounds(), "bounds");
            yield.apply(value.getAnnotatedBounds(), "annotatedBounds");
        }
    }
}