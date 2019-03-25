package com.stelary.metadata.explorer.probe;

import com.stelary.metadata.explorer.Probe;
import com.stelary.metadata.explorer.util.Yield;

import java.lang.reflect.Modifier;

public class ClassProbe extends Probe<Class> {
    @Override
    public String getStem(Class value) {
        return value.getSimpleName();
    }

    @Override
    public void getAttributes(Yield yield, Class value) {
        // yield.apply(value.getName(), "name"); -- nothing interesting
        // yield.apply(value.getSimpleName(), "simpleName"); -- nothing interesting
        yield.apply(value.getCanonicalName(), "canonicalName");
        // yield.apply(value.getPackageName(), "packageName"); -- v9+
        yield.apply(value.isAnnotation(), "isAnnotation");
        yield.apply(value.isAnonymousClass(), "isAnonymousClass");
        yield.apply(value.isArray(), "isArray");
        yield.apply(value.isEnum(), "isEnum");
        yield.apply(value.isInterface(), "isInterface");
        yield.apply(value.isLocalClass(), "isLocalClass");
        yield.apply(value.isMemberClass(), "isMemberClass");
        yield.apply(value.isPrimitive(), "isPrimitive");
        yield.apply(value.isSynthetic(), "isSynthetic");
        yield.apply(Modifier.toString(value.getModifiers()), "modifiers");
        yield.apply(value.desiredAssertionStatus(), "desiredAssertionStatus");
        // yield.apply(value.toGenericString(), "toGenericString"); -- nothing interesting

        if (cliOptions.annotation) {
            yield.apply(value.getAnnotatedInterfaces(), "annotatedInterfaces");
            yield.apply(value.getAnnotatedSuperclass(), "annotatedSuperclass");
        }
        // yield.apply(value.getClass(), "class"); -- nothing interesting, always Class
        ClassLoader classLoader = value.getClassLoader();
        if (classLoader != null) {
            yield.apply(String.format("‹%s›", classLoader.getClass().getSimpleName()), "classLoader");
        }
        yield.apply(value.getComponentType(), "componentType");
        yield.apply(value.getDeclaringClass(), "declaringClass");
        yield.apply(value.getEnclosingClass(), "enclosingClass");
        yield.apply(value.getSuperclass(), "superclass");
        yield.apply(value.getInterfaces(), "interfaces");
        yield.apply(value.getSigners(), "signers");
        if (cliOptions.generic) {
            yield.apply(value.getGenericSuperclass(), "genericSuperclass");
            yield.apply(value.getGenericInterfaces(), "genericInterfaces");
        }
        if (cliOptions.packageAndModule) {
            // yield.apply(value.getModule(), "module"); -- v9+
            yield.apply(value.getPackage(), "package");
        }
        if (cliOptions.security) {
            yield.apply(value.getProtectionDomain(), "protectionDomain");
        }

        // if (cliOptions.members) {
        //     yield.apply(value.getClasses(), "classes"); // -> Class[]
        //     yield.apply(value.getConstructors(), "constructors"); // -> Constructor[]
        //     yield.apply(value.getDeclaredClasses(), "declaredClasses"); // -> Class[]
        //     yield.apply(value.getDeclaredConstructors(), "declaredConstructors"); // -> Constructor[]
        //     yield.apply(value.getDeclaredFields(), "declaredFields"); // -> Field[]
        //     yield.apply(value.getDeclaredMethods(), "declaredMethods"); // -> Method[]
        //     yield.apply(value.getEnclosingConstructor(), "enclosingConstructor"); // -> Constructor
        //     yield.apply(value.getEnclosingMethod(), "enclosingMethod"); // -> Method
        //     yield.apply(value.getEnumConstants(), "enumConstants"); // -> Object[]
        //     yield.apply(value.getFields(), "fields"); // -> Field[]
        //     yield.apply(value.getMethods(), "methods"); // -> Method[]
        // }
    }
}
