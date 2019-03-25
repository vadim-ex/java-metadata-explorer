package com.stelary.metadata.explorer;

import com.stelary.metadata.explorer.util.ProbeLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ScanClassesTest {

    private static final List<Probe> definedProbes = ProbeLocator.getProbes();

    @DataProvider(name = "probes")
    public Object[][] locateProbe() {
        Object[][] parameters = definedProbes.stream()
                .map(aProbe -> new ProbeHandler(aProbe))
                .sorted()
                .map(probeHandler -> new Object[]{probeHandler})
                .toArray(Object[][]::new);
        return parameters;
    }

    HashSet<String> methods(Probe probe) {
        var methodNames = new HashSet<String>();
        for (var method : probe.getClassToProbeFor().getMethods()) {
            if (method.getParameterCount() != 0) {
                continue;
            }
            if (method.getReturnType() == void.class){
                continue;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            methodNames.add(method.getName());
        }
        return methodNames;
    }

    @Test(dataProvider = "probes")
    public void listMethods(ProbeHandler handler) throws NoSuchMethodException {
        Probe probe = handler.probe();
        var properMethods = methods(probe);
        for (var otherHandler : locateProbe()) {
            var otherProbe = ((ProbeHandler)otherHandler[0]).probe();
            if (isProperSubclassOf(probe.getClass(), otherProbe.getClass())) {
                // cannot detect method availability by JDK version, avoid duplication
                return;
            }
            if (isProperSubclassOf(probe.getClassToProbeFor(), otherProbe.getClassToProbeFor())) {
                // System.out.printf("*** %s %s%n", probe.getClass().getSimpleName(), otherProbe.getClass().getSimpleName());
                properMethods.removeAll(methods(otherProbe));
            }
        }

        var desirableMethods = properMethods.toArray(new String[properMethods.size()]);
        Arrays.sort(desirableMethods);
        var acc = new StringBuilder();
        acc.append(String.format("%s%n", probe.getClass().getSimpleName()));
        for (var methodName : desirableMethods) {
            if (methodName.equals("toString")
                    || methodName.equals("hashCode")
                    || methodName.equals("getClass")) {
                continue;
            }
            var method = probe.getClassToProbeFor().getMethod(methodName);
            var attributeName = methodName;
            if (attributeName.startsWith("get")) {
                attributeName = attributeName.substring(3);
                attributeName = attributeName.substring(0, 1).toLowerCase() + attributeName.substring(1);
            }
            acc.append(String.format("        yield.apply(value.%s(), \"%s\"); // -> %s%n",
                    methodName, attributeName,
                    method.getReturnType().getSimpleName()));
        }
        System.out.println(acc);
    }

    private boolean isProperSubclassOf(Class probeClass, Class otherClass) {
        return probeClass != otherClass && otherClass.isAssignableFrom(probeClass);
    }

    class ProbeHandler implements Comparable<ProbeHandler>{
        private final Probe probe;

        ProbeHandler(Probe probe) {
            this.probe = probe;
        }

        @Override
        public String toString() {
            return probe.getClass().getSimpleName();
        }

        Probe probe() {
            return this.probe;
        }

        @Override
        public int compareTo(ProbeHandler other) {
            return toString().compareTo(other.toString());
        }
    }
}
