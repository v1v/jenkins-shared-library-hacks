## Jenkins Shared library

A shared library with some vitamins to help you to speed up your local development and test the hacks of built-in step.

## Context

This is an example of a shared library for the Jenkins pipelines based on:

- [JCasC](https://jenkins.io/projects/jcasc/) to configure a local jenkins instance.
- [JobDSL](https://github.com/jenkinsci/job-dsl-plugin/wiki) to configure the pipelines to test the steps.
- [JenkinsUnitPipeline](https://github.com/jenkinsci/JenkinsPipelineUnit) to test the shared library steps.
- [Gradle](https://docs.gradle.org/current/userguide/userguide.html) to orchestrate the build/tests of this library.
- [Vagrant](https://www.vagrantup.com/docs/index.html) and [VirtualBox](https://www.virtualbox.org/wiki/Documentation) to spin up jenkins agents using the Swarm connection.

## System Requirements

- Docker >= 19.x.x
- Docker Compose >= 1.25.0
- Vagrant >= 2.2.4
- VirtualBox >= 6
- Java >= 8

## Layout

```
(root)
+- src                             # Groovy source files
|   +- main
|       +- groovy
|           +- Bar.groovy          # for Bar class
|   +- test
|       +- groovy
|           +- FooStepTest.groovy  # Tests for the foo step
|       +- resources               # resource files for the tests
|
+- vars
|   +- foo.groovy                  # for global 'foo' variable
|   +- foo.txt                     # help for 'foo' variable
|
+- resources                       # resource files (external libraries only)
|   +- org
|       +- v1v
|           +- bar.json            # static helper data for org.foo.Bar
|
+- local                           # to enable a jenkins instance with this library
|   +- configs
|   |   +- jenkins.yaml
|   |   +- plugins.txt
|   +- workers
|       +- linux
|       |   +- Vagrantfile
|       +- windows
|           +- Vagrantfile
|   +- docker-compose.yml
|   +- Dockerfile
|   +- Makefile
|
```

## How to test it

```bash
./gradlew clean test
open build/reports/tests/test/index.html
```

### How to test it within the local Jenkins instance

1. Build docker image by running:

```bash
make -C local build
```

2. Start the local Jenkins master service by running:

```bash
make -C local start
```

3. Browse to <http://localhost:8080> in your web browser.

#### Enable the local agent

You can enable your own machine to become an agent, as simple as:

```bash
make -C local start-local-worker
```
NOTE: Java is required.

#### Enable the linux vagrant worker

```bash
make -C local start-linux-worker
```
#### Enable the widnows vagrant worker

```bash
make -C local start-windows-worker
```

#### Customise what plugins are installed

You can configure this jenkins instance as you wish, if so please change:

* local/configs/jenkins.yaml using the [JCasC](https://jenkins.io/projects/jcasc/)
* local/configs/plugins.txt

## Further details

This is a subset of what it has been implemented in the https://github.com/elastic/apm-pipeline-library and https://github.com/v1v/jenkins-pipeline-library-skeleton

https://brokenco.de/2017/08/03/overriding-builtin-steps-pipeline.html
