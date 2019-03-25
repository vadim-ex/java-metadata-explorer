package com.stelary.metadata.explorer;

import com.stelary.metadata.explorer.probe.*;
import com.stelary.metadata.explorer.probe.reflect.*;
import com.stelary.metadata.explorer.probe.security.*;
import lombok.var;

public class Cli {

    private static final Probe[] probes = {
            new AnnotatedArrayTypeProbeV9(),
            new AnnotatedElementProbe(),
            new AnnotatedParametrizedTypeProbe(),
            new AnnotatedTypeProbeV9(),
            new AnnotatedTypeVariableProbe(),
            new AnnotatedWildcardTypeProbe(),
            new CertificateProbe(),
            new CertPathProbe(),
            new ClassProbeV9(),
            new CodeSignerProbe(),
            new CodeSourceProbe(),
            new ConfigurationProbeV9(),
            new GenericArrayTypeProbe(),
            new GenericDeclarationProbe(),
            new ModuleDescriptorProbeV9(),
            new ModuleLayerProbeV9(),
            new ModuleProbeV9(),
            new PackageProbe(),
            new ParameterizedTypeProbe(),
            new PermissionProbe(),
            new PrincipalProbe(),
            new ProtectionDomainProbeV9(),
            new TypeProbe(),
            new TypeVariableProbe(),
            new WildcardTypeProbe(),
    };

    public static void main(String[] args) {
        var application = new Application(9, probes);
        application.run(args);
    }
}
