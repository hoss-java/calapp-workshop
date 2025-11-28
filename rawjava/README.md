# CalAppV1
[**Deck Board**](https://github.com/hoss-java/calapp-workshop/blob/main/DECK.md)

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

1. `CalAppV1` asks first to select an oprator.
> * An operator can be (`+`, `-`, `*`, `/`) or `exit`
2. `CalAppV1` asks to inpute two numbers that can be `Double`
3. If the oprator is a valid oprator the result will be printed otherwis an invalid operator error is shown
4. To exit , an 'exit' instead operators is used.

### Improve CalAppV1
