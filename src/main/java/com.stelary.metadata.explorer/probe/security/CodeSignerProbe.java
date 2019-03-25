package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.security.CodeSigner;

public class CodeSignerProbe extends Probe<CodeSigner> {
    @Override
    public void getAttributes(Yield yield, CodeSigner value) {
        if (cliOptions.security) {
            yield.apply(value.getSignerCertPath(), "signerCertPath"); // CertPath
            yield.apply(value.getTimestamp().toString(), "timestamp");
        }
    }
}