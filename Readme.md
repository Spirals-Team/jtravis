# jtravis [![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/fr/inria/jtravis/jtravis/maven-metadata.xml.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22fr.inria.jtravis%22%20AND%20a%3A%22jtravis%22) [![Build Status](https://travis-ci.org/Spirals-Team/jtravis.svg?branch=master)](https://travis-ci.org/Spirals-Team/jtravis) [![Coverage Status](https://coveralls.io/repos/github/Spirals-Team/jtravis/badge.svg?branch=master)](https://coveralls.io/github/Spirals-Team/jtravis?branch=master)

The purpose of this project is to propose a Java API in order to use Travis-CI API.

## Install

```bash
mvn install
```

## Usage

The API can be immediately used after specifying the following dependency in your pom.xml:

```xml
<dependency>
    <groupId>fr.inria.jtravis</groupId>
    <artifactId>jtravis</artifactId>
    <version>2.0</version>
</dependency>
```

The API rely on Travis CI API V3 for most of its part, but some endpoints of Travis CI API V2 are still available (like /jobs endpoint).
It *does not* allow to trigger new builds for now. But it does not require to use TravisCI authentication either.

### Limitations

This tool has primarily been developed in order to study Java builds using Maven tools: so, most features rely on that choice. 
Don't hesitate to propose pull requests in order to enhance the current state of this library.

### Github Integration

JTravis uses Github API in order to get useful information about pull requests when builds has been triggered by pull requests. 

See [javadoc from github-api library](http://github-api.kohsuke.org/apidocs/org/kohsuke/github/GitHubBuilder.html#fromEnvironment--) for more information.

### Usage example

```java 

JTravis jTravis = new JTravis.Builder().build(); // you can specify in the builder the Github API token and/or the Travis CI API token
Optional<Repository> repository = jTravis.repository().fromSlug("Spirals-Team/jtravis");

if (repository.isPresent()) {
    Optional<Builds> optionalBuilds = jTravis.build().fromRepository(repository.get());

    if (optionalBuilds.isPresent()) {
        for (Build build : optionalBuilds.get().getBuilds()) {
            System.out.println("build id: "+build.getId()+" status: "+build.getState().name());
        }
    }
}
```

## Aknowledgement

This project has been funded by the InriaHub / ADT LibRepair.
