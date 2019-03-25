package com.stelary.metadata.explorer.probe.security;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;
import lombok.var;

import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;

import static com.stelary.metadata.explorer.util.IteratorUtil.toList;

public class CertPathProbe extends Probe<CertPath> {
    @Override
    public void getAttributes(Yield yield, CertPath value) {
        if (cliOptions.security) {
            yield.apply(value.getCertificates().toArray(), "certificates");
            try {
                if (value.getEncoded() != null) {
                    yield.apply("‹certPath›", "encoded");
                }
            } catch (CertificateEncodingException unused) {
            }

            var encodings = toList(value.getEncodings());
            if (encodings != null) {
                yield.apply(String.format("‹%d›", encodings.size()), "encodings");
            }
            yield.apply(value.getType(), "type");
        }
    }
}