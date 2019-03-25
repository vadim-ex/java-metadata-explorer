#!/bin/bash
rm test.*
args="foo --output test.plantuml $@"
./gradlew clean distZip
unzip -o build/distributions/java-metadata-explorer.zip
chmod +x java-metadata-explorer/bin/java-metadata-explorer
java-metadata-explorer/bin/java-metadata-explorer -o test.plantuml "$@"
if [ -f test.plantuml ]; then
    plantuml test.plantuml -tsvg
fi
if [ -f test.svg ]; then
    gwenview test.svg &
fi
