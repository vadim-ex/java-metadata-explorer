package com.stelary.metadata.explorer.probe;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

public class ModuleLayerProbeV9 extends Probe<ModuleLayer> {
    @Override
    public void getAttributes(Yield yield, ModuleLayer value) {
        if (cliOptions.packageAndModule) {
            if (value == ModuleLayer.empty()) {
                yield.apply(true, "-empty");
            }
            // yield.apply(value.boot(), "boot"); -- nothing interesting, static bootstrap
            yield.apply(value.configuration(), "configuration");
            if (!value.modules().isEmpty()) {
                yield.apply(String.format("‹size: %d›", value.modules().size()), "modules");
            }
            yield.apply(value.parents().toArray(), "parents");
        }
    }
}