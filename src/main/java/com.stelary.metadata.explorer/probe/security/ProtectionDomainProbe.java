package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.security.ProtectionDomain;

import static com.stelary.metadata.explorer.util.IteratorUtil.toList;

public class ProtectionDomainProbe extends Probe<ProtectionDomain> {
    @Override
    public void getAttributes(Yield yield, ProtectionDomain value) {
        if (cliOptions.security) {
            yield.apply(value.getCodeSource(), "codeSource");
            yield.apply(toList(value.getPermissions().elements()), "permissions");
            yield.apply(value.getPrincipals(), "principals");
            ClassLoader classLoader = value.getClassLoader();
            if (classLoader != null) {
                yield.apply(String.format("‹%s›", classLoader.getClass().getSimpleName()), "classLoader");
            }
            // yield.apply(value.staticPermissionsOnly(), "staticPermissionsOnly"); -- v9+
        }
    }
}