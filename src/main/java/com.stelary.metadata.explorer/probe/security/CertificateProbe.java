package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

public class CertificateProbe extends Probe<Certificate> {
    @Override
    public void getAttributes(Yield yield, Certificate value) {
        if (cliOptions.security) {
            try {
                if (value.getEncoded() != null) {
                    yield.apply("‹certificate›", "encoded");
                }
            } catch (CertificateEncodingException unused) {
            }
            PublicKey publicKey = value.getPublicKey();
            if (publicKey != null) {
                yield.apply(String.format("‹%s›", publicKey.getClass().getSimpleName()), "publicKey");
            }
            yield.apply(value.getType(), "type");
        }
    }
}