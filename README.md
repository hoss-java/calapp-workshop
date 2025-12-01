# CalApp
[**Deck Board**](https://github.com/hoss-java/calapp-workshop/blob/main/DECK.md)

## The idea
* The main idea is to learn more about Java and its related tools and java-frameworks. To achive this goal :
> 1. CalAppV1 (a singel java file) - Coding a simple java code to get numbers and oprtors as input (all sepertly) and then print results.
> 2. CalAppV2 (a singel java file) - Coding a new CalApp to get a sting as input and parssing the string to calculate results.
> 3. Try to implement CalAppV2 in a Maven project.
> 4. Add some tests to the maven created project.
> 5. Try to implement CalAppV2 as a spring project.
> 6. Try to update CalAppV2 to work as service and implement it as a Tomcat object.
* It also can be good to develop or prepare tools that make it easre/faster to code.
* Lookling to see how it can be automated to test and running codes.

## **rawjava**
Two versions of CalApp were coded and stored inside the folder `rawjava`.
* Both `CalAppV1.java` and `CalAppV2.java` are simple java files that can be run using `java` command/program
* As the initial idea to work around evrythings that needs to work with Java, a container base openjdk is used to run `java`
* Details about `CalAppV1.java` and `CalAppV2.java` are docomented [here](https://github.com/hoss-java/calapp-workshop/blob/main/rawjava/README.md)
* `CalAppV2.java` is used as the core code for Maven and other projects

## A **Maven** version of the `CalAppV2.java`
### Recap
* A spike was down about java frameworks [here](https://github.com/hoss-java/lessons/blob/main/w48-java1/java_frameworks_spike.md)
> As a output of the spike, an Alpine base openjdk+maven container was created (Docker container files can be found [here](https://github.com/hoss-java/containers/tree/main/maven)). To compose the maven container, from the cloned folder of the container repo:
>>```
>>docker compose --env-file ".env.maven" -f docker-compose-maven.yaml up -d
>>
>># To check the openjdk container
>>#docker ps
>># To use maven
>>#docker exec -it maven mvn <parameters>
>>
>># The conteiner can be removed by using the command below
>>docker compose --env-file ".env.maven" -f docker-compose-maven.yaml down --remove-orphans --rmi local -v
>># To Clean up all caches, images, networks
>>docker system prune -a
>>```
>* As the spike findings to create a maven project (the command create a project in the current path/folder,In other words, to use the following command, it needs first to change the current path to the path where the project is to be created.):
>>```
>>#mvn archetype:generate \
>>#    -DarchetypeGroupId=< archetype-group> \
>>#    -DarchetypeArtifactId=< archetype>\
>>#    -DarchetypeVersion=< version> \
>>#    -DgroupId=< your.company> \
>>#    -DartifactId=< app-name> \
>>#    -DinteractiveMode=false
>>
>># For example
>>mvn archetype:generate \
>>    -DarchetypeArtifactId=maven-archetype-quickstart  \
>>    -DgroupId=com.myapp.helloworld  \
>>    -DartifactId=my-app  \
>>    -DinteractiveMode=false
>>```
> **OBS!** There is an other option named `-DoutputDirectory` that can be used to specify the path used to create a project (without changing the current path)
>># For example
>>mvn archetype:generate \
>>    -DarchetypeArtifactId=maven-archetype-quickstart  \
>>    -DgroupId=com.myapp.helloworld  \
>>    -DartifactId=my-app  \
>>    -DinteractiveMode=false
>>    -DoutputDirectory=calapp-workshop
>>```
> * As the main idea to use Maven container instead installing maven and jdk directly on the host, paths used to create projects become container base , not host base. The maven container sees `~/github-java` as root folder (the path was set via `.env.maven`). In fact `~/github-java` is mapped to `/data` inside of the container but by default `/data` is set as working directory for the container. It means to address a file that is stored inside of `~/github-java` the path start from `~/github-java`, not from the root of the host. For example if a project is stored on `~/github-java/calapp-workshop` (on the host), `calapp-workshop` is the path of the project from the inside of the container. So to create a project named `CalApp` inside of `~/github-java/calapp-workshop`:
>>```
>>docker exec -it maven mvn archetype:generate \
>>    -DarchetypeArtifactId=maven-archetype-quickstart \
>>    -DgroupId=com.CalApp \
>>    -DartifactId=CalApp \
>>    -DinteractiveMode=false \
>>    -DoutputDirectory=calapp-workshop
>>```
> * The project created with the command above is a simple hello world app that can be modified as needed. There are many kind of maven projects than can be created by changing parameters passed to `mvn`.
> * The simple project created by the command above has a simple `pom.xml` without building settings/configurations
>>```
>><project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>>  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
>>  <modelVersion>4.0.0</modelVersion>
>>  <groupId>com.CalApp</groupId>
>>  <artifactId>CalApp</artifactId>
>>  <packaging>jar</packaging>
>>  <version>1.0-SNAPSHOT</version>
>>  <name>CalApp</name>
>>  <url>http://maven.apache.org</url>
>>  <dependencies>
>>    <dependency>
>>      <groupId>junit</groupId>
>>      <artifactId>junit</artifactId>
>>      <version>3.8.1</version>
>>      <scope>test</scope>
>>    </dependency>
>>  </dependencies>
>></project>
>>```
>>> * During the java frameworks spike, some useful project temlates with working `pom.xml` were created and added to the containers repos [here](https://github.com/hoss-java/containers/tree/main/maven/testcode)
>>> * Before starting working on the simple hollo world project above it needs to updated `pom.xml` with a build section instructions. The instruction below is a simple build instruction that defines the main path and necessary plugins to run CalApp. The build block is added between `<project>` and `</project>`

>>>>```

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.0.2</version>
        <configuration>
          <archive>
            <manifest>
              <addClasspath>true</addClasspath>
              <mainClass>com.CalApp.App</mainClass>
            </manifest>
          </archive>
        </configuration>
      </plugin>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>1.2.1</version>
        <executions>
          <execution>
            <goals>
              <goal>java</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <mainClass>com.CalApp.App</mainClass>
        </configuration>
      </plugin>
    </plugins>
  </build>

>>>>```
>>> * **OBS!** A project can be developed and run without a build section but cannot be built.
>>> * To create a real project it needs to add some more sections, for example test moduls and references. They can be added later but The have already tested and available on test projects codes created through the java frameworks spike [see here](https://github.com/hoss-java/containers/tree/main/maven/testcode)
> * According to findings through the spike, to run and working with the created project `CalApp`:
>>```
>>docker exec -it maven mvn -f calapp-workshop/CalApp exec:java
>>#or
>>docker exec -it maven mvn -f calapp-workshop/CalApp exec:java -Dexec.mainClass="main" -Dexec.args="sdafhjsdkfh"
>>
>># To clean up
>>docker exec -it maven mvn -f calapp-workshop/CalApp clean package
>>```
