#!/bin/sh

find ./  -name "pom.xml" -exec sed -i "s/<version>\${webapp-site-version}/<version>$1/g" {} \;

mvn -Dversion=$1 -Dmaven.test.skip=true clean deploy