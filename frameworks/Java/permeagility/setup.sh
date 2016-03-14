#!/bin/bash

fw_depends java8 maven
export JAVA_HOME=/opt/java8
mvn clean package
java -jar target/permeagility-0.7.5-jar-with-dependencies.jar
