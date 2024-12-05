#!/bin/bash
# Camariere
# Build script; run with no arguments to build all artifacts for Server; run
# with "clean" to remove all artifacts created from the build.

if [[ ${1} == "clean" ]]; then
	find $(dirname $0) -type f -name "*.class" -print0 | xargs -0 rm
else
	pushd $(dirname $0)/src
	javac ./camariere/*.java
	popd
fi

