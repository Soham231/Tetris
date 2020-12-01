#!/bin/bash -ex

mvn -q clean
mvn -q compile
mvn -q -e -Dprism.order=sw exec:java -Dexec.mainClass="cs1302.arcade.ArcadeDriver"

