#!/bin/bash

fw_depends java8 maven

mvn clean package

java -jar target/permeagility-0.6.2-jar-with-dependencies.jar
