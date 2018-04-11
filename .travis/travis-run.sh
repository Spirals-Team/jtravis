#!/usr/bin/env bash

set -e

mvn clean install javadoc:javadoc
mvn jacoco:report coveralls:report --fail-never