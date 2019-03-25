package com.stelary.metadata.explorer.probe.reflect;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.GenericDeclaration;

public class GenericDeclarationProbe extends Probe<GenericDeclaration> {
    @Override
    public void getAttributes(Yield yield, GenericDeclaration value) {
        if (cliOptions.generic) {
            yield.apply(value.getTypeParameters(), "typeParameters");
        }
    }
}
