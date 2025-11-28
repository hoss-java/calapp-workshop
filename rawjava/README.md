# CalAppV1
[**Deck Board**](https://github.com/hoss-java/calapp-workshop/blob/main/DECK.md#48-002)

* **OBS!** No test has been planned to code on this level.

## How to use the code
A openjdk container is used to run and test the code. It means it needs to compose the openjdk container that was pereard through this repo [containers](https://github.com/hoss-java/containers)

### Compose a openjdk container
The container compose file by default share `~/github-java` folder which is the home of all github repo(s) (included the folder that java code folders) with the openjdk container.
In the case that java codes are stored somewhere else, it needs to update `.env.openjdk` with a right path before composing.
* **OBS!** `.env.openjdk` can be found in the root of `containers`'s' repo.

```
# chenge folder to the 'containers' repo folder
docker compose --env-file ".env.openjdk" -f docker-compose-openjdk.yaml up -d

# to check the openjdk container
#docker ps

# The conteiner can be removed by using the command below
docker compose --env-file ".env.openjdk" -f docker-compose-openjdk.yaml down --remove-orphans --rmi local -v
# to Clean up all caches, images, networks
docker system prune -a
```
### Run a java 
The openjdk container sees `~/github-java` as root folder.
In fact `~/github-java` is mapped to `/data` inside of the container but by default `/data` is set as working directory for the container. It means to address a file that is stored inside of `~/github-java` the path start from `~/github-java`, not from the root of the host.
For example if a file is styored on `~/github-java/calapp-workshop/rawjava/CalAppV1.java` (on the host), `calapp-workshop/rawjava/CalAppV1.java` is the address of the same file from the inside of the container.
So to run `~/github-java/calapp-workshop/rawjava/CalAppV1.java`
```
docker exec -it openjdk java calapp-workshop/rawjava/CalAppV1.java
```

### How it works

1. **CalAppV1** asks first to select an oprator.
> * An operator can be (`+`, `-`, `*`, `/`) or `exit`
2. **CalAppV1** asks to inpute two numbers that can be `Double`
3. If the oprator is a valid oprator the result will be printed otherwis an invalid operator error is shown
4. To exit , an 'exit' instead operators is used.

A summary
* **CalAppV1** uses two standard libraries
> * `import java.util.Set`
> * `java.util.Scanner`

### Improve **CalAppV1**
In the CalAppV1 logic, numbers are asked first and then an operator validation is run. It means numbers are asks even the passed operator is invalid!!
* It could be better to check operator before asking numbers, if the operator is an invalid operator, the main asks again to input a valid operator

# **CalAppV2**
[**Deck Board**](https://github.com/hoss-java/calapp-workshop/blob/main/DECK.md#48-003)

* **OBS!** Doxygen was added by getting help from AI tools
* **OBS!** No test has been planned to code on this level.

## How to use the code
See [here](https://github.com/hoss-java/calapp-workshop/blob/main/rawjava/README.md#how-to-use-the-code)

### Compose a openjdk container
See [here](https://github.com/hoss-java/calapp-workshop/blob/main/rawjava/README.md#compose-a-openjdk-container)

### Run a java 
See [here](https://github.com/hoss-java/calapp-workshop/blob/main/rawjava/README.md#run-a-java)

### How it works

1. **CalAppV2** is an expression parser base calculator.
> * It means it try to parse an calculator and calculate the result
> * It is a very simple parser and can't parse nested expression.
> * An expression can containe numbers, `+`, `-`, `*`, `/` and `(`, `)`
> * Typing `exit` finished the program and quits.
2. **CalAppV2** asks to inpute an expression.
3. The expression is parsed
> * Whith spaces are removed.
> * The parser creates two stacks (via an standard library `import java.util.Stack`), one for values, and one for oprators.
> * The parser loops on the expression chars
>> * If it starts with a digit, it will continue to find a digit token. The token is pushed to the values stack
>> * If it is an oprator. It will be pushed to the operators stack
>> * `(` is seen as an oprator and it will pushed the operators stack.
>> * if `)` is found. The parser expects to find two valus and two opratores pushed to the stacks (values and oprators).
>>> * In the case of finding `)` two tokens from the valuse stack and two tokens from the operators stack are poped and a new token which is the result of the contains of the values and operator inside of the parentheses is pushed to the values stack.
>> * When the expression is parsed and all tokens are pushed to the stacks, a loop starts to pop values and operators to calculate the final results.
>>> * The parses expects to find two values for each operator.
> * An expression has a pattern like `<[(]a<[+,-,*,/]>b[)]>[<[+,-,*,/]><[(]a<[+,-,*,/]>b[)]>]`. In the case of an expression does not follow the pattern , the parser will be crached
5. The main code uses a `try{} catch{}` to handle errors in cases that expression does not follow a right pattern.
4. To exit , an 'exit' instead operators is used.

A summary
* **CalAppV2** uses two standard libraries
> * `java.util.Scanner`
> * `import java.util.Stack`

### Improve **CalAppV2**

* **OBS!** Doxygen was added by getting help from AI tools