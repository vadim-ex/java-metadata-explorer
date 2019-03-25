package com.stelary.metadata.explorer;

import com.stelary.metadata.explorer.util.ProbeLocator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MetadataExplorerTest {
    static List<Probe> probes = ProbeLocator.getProbes();

//    @Test()
//    public void test() {
//        var graph = new Graph();
//        var explorer = new MetadataExplorer(graph, new CliOptions(), ProbeListV8.getProbes());
//        explorer.explore(MetadataExplorer.class);
//        graph.print(System.out);
//    }
//
//    @Test
//    public static void ensure_all_probes_are_used() {
//        var graph = new Graph();
//        var explorer = new MetadataExplorer(graph, new CliOptions(), ProbeListV8.getProbes());
//        Reflections reflections = new Reflections("com.stelary", new SubTypesScanner(false));
//
//        Set<Class<? extends Probe>> allProbes = reflections.getSubTypesOf(Probe.class);
//        Set<Class> usedProbes = explorer.probes.stream().map(Probe::getClass).collect(Collectors.toSet());
//        var unusedProbes = Sets.difference(allProbes, usedProbes);
//        for (var probe : unusedProbes) {
//            System.out.println(probe.getSimpleName());
//        }
//
//        assert unusedProbes.isEmpty();
//    }

    @DataProvider(name = "defined-probes")
    public static Object[][] definedProbes() {
        List<Object[]> objects = Stream.of(probes)
                .map(x -> new Object[]{x.getClass().getSimpleName(), x})
                .collect(Collectors.toList());
        return objects.toArray(new Object[objects.size()][]);
    }

    @Test
    public void dependency() {
        for (var pair1 : probes) {
            var a = pair1.getClass();
            for (var pair2 : probes) {
                var b = pair2.getClass();
                if (a == b) {
                    continue;
                }
                if (b.isAssignableFrom(a)) {
                    System.out.printf("%s extends %s%n", pair1.getClass().getSimpleName(), pair2.getClass().getSimpleName());
                }
            }
        }
    }

//    @Test(dataProvider = "defined-probes")
//    public void listMethods(String name, Probe probe) {
//        System.out.println(name);
//        var methods = new TreeMap<String, Method>();
//
//        var classMethods = probeClass.getDeclaredMethods();
//        // var classMethods = probeClass.getMethods();
//        for (var method : classMethods) {
//            if (method.getParameterCount() != 0) {
//                continue;
//            }
//            methods.put(method.getName(), method);
//        }
//        for (var entry : methods.entrySet()) {
//            var string = entry.getValue().toString();
//            if (!string.startsWith("public ")) {
//                continue;
//            }
//            var methodName = entry.getKey();
//            if (methodName.equals("toString") || methodName.equals("hashCode")) {
//                continue;
//            }
//            var attributeName = methodName;
//            System.out.printf("yield.apply(value.%s(), \"%s\"); // -> %s%n", methodName, attributeName,
//                    entry.getValue().getReturnType().getSimpleName());
//        }
//    }
}
