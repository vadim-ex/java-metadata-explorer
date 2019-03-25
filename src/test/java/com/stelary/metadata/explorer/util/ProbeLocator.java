package com.stelary.metadata.explorer.util;

import com.stelary.metadata.explorer.Probe;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.stelary.metadata.explorer.util.IteratorUtil.toList;

public class ProbeLocator {
    private static final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


    private static <T> T print(String tag, T value) {
        System.out.printf("<%s> %s%n", tag, value);
        return value;
    }

    public static List<Probe> getProbes() {
        return enumerateClasses(getClassPath())
                .filter(p -> p.startsWith("com.stelary.metadata.explorer.probe."))
                .map(ProbeLocator::loadClass)
                .map(probeClass -> (Class<? extends Probe>) probeClass)
                .map(ProbeLocator::instantiateProbe)
                .collect(Collectors.toList());
    }

    private static Probe instantiateProbe(Class<? extends Probe> probeClass) {
        try {
            return probeClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException |
                InvocationTargetException |
                IllegalAccessException |
                NoSuchMethodException |
                NullPointerException unused) {
            System.out.printf("<error> %s: %s%n", probeClass.getSimpleName(), unused.getMessage());
            return null;
        }
    }

    private static Class loadClass(String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private static String[] getClassPath() {
        return System.getProperty("java.class.path").split(System.getProperty("path.separator"));
    }

    private static Stream<String> enumerateClasses(String[] paths) {
        return Stream.of(paths)
                .flatMap(ProbeLocator::enumerateJarOrDir)
                .map(className -> className.substring(0, className.length() - 6))
                .map(className -> className.replace('/', '.'));
    }

    private static Stream<String> enumerateJarOrDir(String path) {
        if (Files.isDirectory(Paths.get(path))) {
            return enumerateDir(path);
        } else if (Files.isRegularFile(Paths.get(path))) {
            return enumerateJar(path);
        } else {
            return Stream.empty();
        }
    }


    private static Stream<String> enumerateDir(String startDir) {
        var startPath = Paths.get(startDir);
        Stream<Path> walk;
        try {
            walk = Files.walk(startPath);
        } catch (IOException unused) {
            return Stream.empty();
        }
        return walk
                .filter(p -> p.toString().endsWith(".class"))
                .filter(Files::isRegularFile)
                .map(startPath::relativize)
                .map(Path::toString);
    }

    private static String stripVersionLocation(String className) {
        if (!className.startsWith("META-INF/versions/")) {
            return className;
        }
        String[] tokens = className.split("/");
        String[] resultTokens = new String[tokens.length - 3];
        System.arraycopy(tokens, 3, resultTokens, 0, tokens.length - 3);
        return String.join("/", resultTokens);
    }

    private static Stream<String> enumerateJar(String jarFile) {
        JarFile jar;
        try {
            jar = new JarFile(jarFile);
        } catch (IOException unused) {
            return Stream.empty();
        }
        return  toList(jar.entries())
                .stream()
                .map(JarEntry::getName)
                .filter(name -> name.endsWith(".class"))
                .map(ProbeLocator::stripVersionLocation);
    }
}
