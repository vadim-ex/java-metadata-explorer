package com.stelary.metadata.explorer;

import lombok.Value;

@Value
class Edge {
    private final String name;
    private final Target to;
}
