package com.stelary.metadata.explorer;

import com.stelary.metadata.explorer.util.Yield;
import lombok.Getter;
import lombok.var;

import java.lang.reflect.ParameterizedType;

public abstract class Probe<TClassToProbeFor> {
    @Getter
    private final Class<?> classToProbeFor;
    protected CliOptions cliOptions;

    public static void topSort(Probe[] probes) {
        for (var ix = 0; ix < probes.length; ++ix) {
            var candidate = probes[ix];
            for (var iy = ix + 1; iy < probes.length; ++iy) {
                var other = probes[iy];
                if (candidate.ancestorOf(other)) {
                    probes[iy] = candidate;
                    candidate = other;
                }
            }
            probes[ix] = candidate;
        }
    }

    public Probe() {
        // simplified superclass reflection
        Class childClass = getClass();
        Class parentClass = childClass.getSuperclass();
        while (parentClass != Probe.class) {
            childClass = parentClass;
            parentClass = childClass.getSuperclass();
        }
        classToProbeFor = (Class) ((ParameterizedType) childClass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

    boolean recognizes(Object value) {
        return classToProbeFor.isInstance(value);
    }

    boolean ancestorOf(Probe probe) {
        return classToProbeFor.isAssignableFrom(probe.classToProbeFor);
    }

    String getTag() {
        if (classToProbeFor.isInterface()) {
            return "interface";
        } else if (classToProbeFor.isEnum()) {
            return "enum";
        } else {
            return "class";
        }
    }

    public String getStem(TClassToProbeFor value) {
        return classToProbeFor.getSimpleName();
    }

    public void configure(CliOptions cliOptions) {
        this.cliOptions = cliOptions;
    }

    public abstract void getAttributes(Yield yield, TClassToProbeFor value);
}
