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
> * Running or building an app add some files to folder `CalApp/target`. They should be ignored by git. The target folder and `.jar` files can be ignored by adding them to `.gitignore`
> * Aftre creating the simple project above, a file named `App.java` is added to `calapp-workshop/CalApp/src/main/java/com/CalApp/`. To impement the calculator class to the project instead the hello world, it just needs to replace the class `App` with `CalAppV2`  developed throgh [48-003](https://github.com/hoss-java/calapp-workshop/blob/main/DECK.md#calappv2-) ( [code can be found here](https://github.com/hoss-java/calapp-workshop/blob/main/rawjava/CalAppV2.java))
> **OBS!** In the case of changing class name ( for example `App` to `CalApp`), the file name `App.java` should also be renamed. It also needs to update `pom.xml` to points to new class name and entry points.
> * **OBS!** Java commands usually uses caches. In the case of changing file names or major changes it need to be clean target folder and caches before running again. To clean caches :
>>```
>># To clean up
>>docker exec -it maven mvn -f calapp-workshop/CalApp clean package
>>
>># And then run
>>docker exec -it maven mvn -f calapp-workshop/CalApp exec:java
>>```
> * To make the code more generic, the `CalApp` class was pulled out of the main file and moved to a new file named `ExpressionParser.java` to use as a library.

* Develop some tests for the maven version of CalApp
> * Acording to the java frameworks spike, JUnit is a widely used open-source testing framework for writing and running automated unit tests in Java.
>> * There are two key versions of JUnit, JUnit 4 and JUnit 5.
>> * JUnit 5 has changed the name to JUnit Jupiter.
>> * Unit 5 is modular and composed of JUnit Platform, JUnit Jupiter (API & engine), and JUnit Vintage for legacy tests.
> * A Maven project created through the commands explained before has a simple test part but as below which means using the default version of JUinit
>>```
>>.
>>.
>>  <dependencies>
>>    <dependency>
>>      <groupId>junit</groupId>
>>      <artifactId>junit</artifactId>
>>      <version>3.8.1</version>
>>      <scope>test</scope>
>>    </dependency>
>>  </dependencies>
>>.
>>.
>>```
> **OBS!** During getting hands on coding tests and getting them working, I found the pom.xml file developed during the spike has an issue. In the build part, `configuration` was placed in side executions, that forcet Maven to build jar file for each exacution. Now it has been fixed.
>>```
>>      <configuration>
>>        <mainClass>com.CalApp.CalApp</mainClass>
>>      </configuration>
>>```
> * To run tests :
>>```
>># To clean temporary files and caches
>>docker exec -it maven mvn -f calapp-workshop/CalApp clean
>> # To compile and creating jar file
>>docker exec -it maven mvn -f calapp-workshop/CalApp coompile
>># or
>>docker exec -it maven mvn -f calapp-workshop/CalApp clean coompile
>>
>># To run tests
>>docker exec -it maven mvn -f calapp-workshop/CalApp test
>>
>># To Create package
>>docker exec -it maven mvn -f calapp-workshop/CalApp package
>># To run main
>>docker exec -it maven mvn -f calapp-workshop/CalApp exec:java
>>```
> * Now CalApp has two empty test classes, one for `CalApp.java` and one for the lib `ExpressionParser.java`. The test classes are JUnit 3 base. The should be updated to JUnit 4 or 5 when a some tests were coded.
> * To make the code for CalApp more generic to support multi libraries, `ExpressionParser.cal` method was moved to `CalApp` class and the name was changed to `expressionCalculator`, now `ExpressionParser` is more like a lib and make it easyer to write tests.
> * It posible to  write test for `CalApp` methods but as the aim of this job (learning), it's skipped and `ExpressionParser` is focused to develop tests and learn about test frameworks. So `CalAppTest` is an empty test class tests nothing.
> * `ExpressionParser` class has 4 methods one is public (`evaluate`) and three is private (`isOperator`, `hasPrecedence`, `applyOperation`). Here is a challenge to how to test a private method :)
>> * There is no access to a private method from outside of the class so it needs to be invoked. Here is a simple invoke for `hasPrecedence`
>>>```
>>>private static boolean invokeHasPrecedence(char op1, char op2) throws Exception {
>>>    Method m = ExpressionParser.class.getDeclaredMethod("hasPrecedence", char.class, char.class);
>>>    m.setAccessible(true);
>>>    return (Boolean) m.invoke(null, op1, op2);
>>>}
>>>```
>> * It can be simple as above or it can be more generic to get method names and parameters to invoke. For now, it is enough to have one invoke per private method.
>> * Ok now the first has been done. There are 2 more private method that need invoke to test. To avoid code invoke for each private method it is better to code a generik invoke.
>>> * First try : The class name is known, only methods and parameteres vary - The invoke below gets a method name and args, and pass them to the original method (without any arg validity check)
>>>>```
>>>>@SuppressWarnings("unchecked")
>>>>private static <R> R invokeStaticByName(String methodName, Object... args) throws Exception {
>>>>    Class<?> cls = ExpressionParser.class;
>>>>    Method found = null;
>>>>    int argCount = args == null ? 0 : args.length;
>>>>
>>>>    for (Method m : cls.getDeclaredMethods()) {
>>>>        if (!m.getName().equals(methodName)) continue;
>>>>        if (!Modifier.isStatic(m.getModifiers())) continue;
>>>>        if (m.getParameterTypes().length != argCount) continue;
>>>>        found = m;
>>>>        break;
>>>>    }
>>>>
>>>>    if (found == null) {
>>>>        throw new NoSuchMethodException("No matching static method '" + methodName + "' with " + argCount + " parameters found on " + cls.getName());
>>>>    }
>>>>
>>>>    found.setAccessible(true);
>>>>    Object ret = found.invoke(null, args); // may throw if args incompatible
>>>>    return (R) ret;
>>>>}
>>>>```
>>> * Improve the invoke: It can also been improved by sending the class name as a parameter
>>>>```
>>>>@SuppressWarnings("unchecked")
>>>>private static <R> R invokeStaticByName(String className, String methodName, Object... args) throws Exception {
>>>>    Class<?> cls = Class.forName(className);
>>>>    Method found = null;
>>>>    int argCount = args == null ? 0 : args.length;
>>>>
>>>>    for (Method m : cls.getDeclaredMethods()) {
>>>>        if (!m.getName().equals(methodName)) continue;
>>>>        if (!Modifier.isStatic(m.getModifiers())) continue;
>>>>        if (m.getParameterTypes().length != argCount) continue;
>>>>        found = m;
>>>>        break;
>>>>    }
>>>>
>>>>    if (found == null) {
>>>>        throw new NoSuchMethodException("No matching static method '" + methodName + "' with " + argCount + " parameters found on " + cls.getName());
>>>>    }
>>>>
>>>>    found.setAccessible(true);
>>>>    Object ret = found.invoke(null, args); // may throw IllegalArgumentException if args incompatible
>>>>    return (R) ret;
>>>>}
>>>>```
>>> * It can also improved by adding validety check for args, but it can be skipped for now