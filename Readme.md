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
    <version>1.1</version>
</dependency>
```

For now the API rely on Travis CI API V2 and only allow to get information from the API, like build statuses, logs, repository, etc. 
It *does not* allow to trigger new builds for now. But it does not require to use TravisCI authentication either.

The migration towards API V3 is planned soon.

### Limitations

This tool has primarily been developed in order to study Java builds using Maven tools: so, most features rely on that choice. 
Don't hesitate to propose pull requests in order to enhance the current state of this library.

### Github Integration

JTravis uses Github API in order to get useful information about pull requests when builds has been triggered by pull requests. 
You currently can use `GITHUB_OAUTH` and `GITHUB_LOGIN` environment variable in order to avoid improve the requests limitations imposed by Github.

See [javadoc from github-api library](http://github-api.kohsuke.org/apidocs/org/kohsuke/github/GitHubBuilder.html#fromEnvironment--) for more information.

### Usage example

```java 

Repository repo = RepositoryHelper.getRepositoryFromSlug("Spirals-Team/jtravis");
Build build = repo.getLastBuild(false); // false here means that we looks for all builds, not only on master branch
if (build.getBuildStatus() == BuildStatus.FAILED) {
    if (build.isPullRequest()) {
        PRInformation prInfo = build.getPrInformations() // get all information on the pull request from Github API
        
        ... 
        for (Job job : build.getJobs()) {
            if (job.getLog() != null) {
                TestsInformation testInfo = job.getLog().getTestsInformation(); 
                if (testInfo != null) {
                    System.out.println(testInfo.getFailing()+" failings tests (on "+testInfo.getRunning()+" tests) on job "+job.getId());
                }
            }
        }
    }           
}
```

## Aknowledgement

This project has been funded by the InriaHub / ADT LibRepair.
