package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.security.CodeSource;

public class CodeSourceProbe extends Probe<CodeSource> {
    @Override
    public void getAttributes(Yield yield, CodeSource value) {
        if (cliOptions.security) {
            yield.apply(value.getLocation().toString(), "location");
            yield.apply(value.getCertificates(), "certificates");
            yield.apply(value.getCodeSigners(), "codeSigners");
        }
    }
}