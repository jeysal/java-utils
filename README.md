# java-utils

[![Build status](https://img.shields.io/travis/jeysal/java-utils.svg?style=flat-square)](https://travis-ci.org/jeysal/java-utils)
[![Code coverage](https://img.shields.io/codecov/c/github/jeysal/java-utils.svg?style=flat-square)](https://codecov.io/gh/jeysal/java-utils)

A small collection of Java utility classes to complement the ones provided by the java.util package.

Meant to grow over time as new general-purpose utility methods required by other projects are added.

## Installation

You can retrieve the artifacts from the jitpack.io repository.
Add the following repository and dependency to your Gradle or Maven build config.

**Note that specifying the version `master-SNAPSHOT` will get you the artifact based on the current commit on branch master.  
You can specify any release number, the latest one available is**
[![Latest release](https://jitpack.io/v/com.github.jeysal/java-utils.svg?style=flat-square)](https://jitpack.io/#com.github.jeysal/java-utils)

### Gradle

    repositories {
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        compile 'com.github.jeysal:java-utils:master-SNAPSHOT'
    }

### Maven

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>com.github.jeysal</groupId>
            <artifactId>java-utils</artifactId>
            <version>master-SNAPSHOT</version>
        </dependency>
    </dependencies>
