package com.stelary.metadata.explorer;

import com.stelary.metadata.explorer.probe.ClassProbe;
import com.stelary.metadata.explorer.probe.PackageProbe;
import com.stelary.metadata.explorer.probe.reflect.*;
import com.stelary.metadata.explorer.probe.security.*;
import lombok.var;

public class Cli {

    private static final Probe[] probes = {
            new AnnotatedElementProbe(),
            new AnnotatedParametrizedTypeProbe(),
            new AnnotatedTypeProbe(),
            new AnnotatedTypeVariableProbe(),
            new AnnotatedWildcardTypeProbe(),
            new CertificateProbe(),
            new CertPathProbe(),
            new ClassProbe(),
            new CodeSignerProbe(),
            new CodeSourceProbe(),
            new GenericArrayTypeProbe(),
            new GenericDeclarationProbe(),
            new PackageProbe(),
            new ParameterizedTypeProbe(),
            new PermissionProbe(),
            new PrincipalProbe(),
            new ProtectionDomainProbe(),
            new TypeProbe(),
            new TypeVariableProbe(),
            new WildcardTypeProbe(),
    };

    public static void main(String[] args) {
        var application = new Application(8, probes);
        application.run(args);
    }
}
