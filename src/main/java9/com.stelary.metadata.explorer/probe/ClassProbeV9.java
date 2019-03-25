package com.stelary.metadata.explorer.probe;

import com.stelary.metadata.explorer.util.Yield;

public class ClassProbeV9 extends ClassProbe {
    @Override
    public void getAttributes(Yield yield, Class value) {
        super.getAttributes(yield, value);
        yield.apply(value.getPackageName(), "packageName");
        if (cliOptions.packageAndModule) {
            yield.apply(value.getModule(), "module");
        }
    }
}
