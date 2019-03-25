package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.security.Principal;

public class PrincipalProbe extends Probe<Principal> {
    @Override
    public void getAttributes(Yield yield, Principal value) {
        if (cliOptions.security) {
            yield.apply(value.getName(), "name");
        }
    }
}