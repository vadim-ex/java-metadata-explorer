package com.stelary.metadata.explorer.util;

import lombok.Value;

@Value
public class Pair<TKey, TValue> {
    TKey key;
    TValue value;
}
