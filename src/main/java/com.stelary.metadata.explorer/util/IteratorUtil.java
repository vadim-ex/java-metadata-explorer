package com.stelary.metadata.explorer.util;

import lombok.var;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class IteratorUtil {
    public static <T> List<T> toList(Enumeration<T> enumeration) {
        if (enumeration == null) {
            return null;
        }
        var acc = new ArrayList<T>();
        while (enumeration.hasMoreElements()) {
            acc.add(enumeration.nextElement());
        }
        return acc;
    }

    public static <T> List<T> toList(Iterator<T> iterator) {
        if (iterator == null) {
            return null;
        }
        var acc = new ArrayList<T>();
        while (iterator.hasNext()) {
            acc.add(iterator.next());
        }
        return acc;
    }
}
