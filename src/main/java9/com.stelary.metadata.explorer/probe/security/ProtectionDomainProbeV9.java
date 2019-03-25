package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.util.Yield;

import java.security.ProtectionDomain;

public class ProtectionDomainProbeV9 extends ProtectionDomainProbe {
    @Override
    public void getAttributes(Yield yield, ProtectionDomain value) {
        super.getAttributes(yield, value);
        if (cliOptions.security) {
            yield.apply(value.staticPermissionsOnly(), "staticPermissionsOnly");
        }
    }
}