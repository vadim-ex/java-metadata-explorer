package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.security.Permission;

import static com.stelary.metadata.explorer.util.IteratorUtil.toList;

public class PermissionProbe extends Probe<Permission> {
    @Override
    public void getAttributes(Yield yield, Permission value) {
        if (cliOptions.security) {
            yield.apply(value.getName(), "name");
            yield.apply(value.getActions(), "actions");
            yield.apply(toList(value.newPermissionCollection().elements()), "newPermissionCollection"); // -> PermissionCollection
        }
    }
}