package com.stelary.metadata.explorer.probe;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.module.ModuleDescriptor;

public class ModuleDescriptorProbeV9 extends Probe<ModuleDescriptor> {
    @Override
    public void getAttributes(Yield yield, ModuleDescriptor value) {
        if (cliOptions.packageAndModule) {
            yield.apply(value.name(), "name");
            yield.apply(value.isAutomatic(), "isAutomatic");
            yield.apply(value.isOpen(), "isOpen");
            yield.apply(value.toNameAndVersion(), "nameAndVersion");
            yield.apply(value.mainClass(), "mainClass");
            yield.apply(String.format("‹size: %d›", value.exports().size()), "exports");
            yield.apply(String.format("‹size: %d›", value.opens().size()), "opens");
            yield.apply(String.format("‹size: %d›", value.packages().size()), "packages");
            yield.apply(String.format("‹size: %d›", value.provides().size()), "provides");
            yield.apply(String.format("‹size: %d›", value.requires().size()), "requires");
            yield.apply(String.format("‹size: %d›", value.uses().size()), "uses");
            yield.apply(value.version().toString(), "version");
            yield.apply(value.rawVersion(), "rawVersion");
            yield.apply(value.modifiers().toString(), "modifiers");
        }
    }
}