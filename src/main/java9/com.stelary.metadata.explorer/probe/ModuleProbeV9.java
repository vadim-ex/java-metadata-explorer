package com.stelary.metadata.explorer.probe;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

public class ModuleProbeV9 extends Probe<Module> {
    @Override
    public void getAttributes(Yield yield, Module value) {
        if (cliOptions.packageAndModule) {
            yield.apply(value.getName(), "name");
            yield.apply(value.getDescriptor(), "descriptor");
            yield.apply(value.isNamed(), "isNamed");
            yield.apply(value.getLayer(), "layer");
            yield.apply(String.format("‹size: %d›", value.getPackages().size()), "packages");
        }
    }
}