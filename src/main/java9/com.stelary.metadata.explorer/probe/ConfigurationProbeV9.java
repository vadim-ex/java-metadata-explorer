package com.stelary.metadata.explorer.probe;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.module.Configuration;

public class ConfigurationProbeV9 extends Probe<Configuration> {
    @Override
    public void getAttributes(Yield yield, Configuration value) {
        if (cliOptions.packageAndModule) {
            if (value == Configuration.empty()) {
                yield.apply(true, "-empty");
            }
            yield.apply(String.format("‹size: %d›", value.modules().size()), "modules");
            yield.apply(value.parents().toArray(), "parents");
        }
    }
}