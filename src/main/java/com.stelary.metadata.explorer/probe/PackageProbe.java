package com.stelary.metadata.explorer.probe;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

public class PackageProbe extends Probe<Package> {
    @Override
    public void getAttributes(Yield yield, Package value) {
        if (cliOptions.packageAndModule) {
            yield.apply(value.getName(), "name");
            yield.apply(value.isSealed(), "isSealed");
            yield.apply(value.getSpecificationTitle(), "specificationTitle");
            yield.apply(value.getSpecificationVendor(), "specificationVendor");
            yield.apply(value.getSpecificationVersion(), "specificationVersion");
            yield.apply(value.getImplementationTitle(), "implementationTitle");
            yield.apply(value.getImplementationVendor(), "implementationVendor");
            yield.apply(value.getImplementationVersion(), "implementationVersion");
            // yield.apply(String.format("‹size: %d›", value.getPackages().length), "packages"); -- nothing interesting, static list of all pkg
        }
    }
}