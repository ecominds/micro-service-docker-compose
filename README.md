# micro-service-docker-compose
A repository to test the `docker-compose`

## Get code from micro-service
1. Clone from `micro-service`
```
# Create a temp directory and clone the micro-service repository
$ mkdir temp && cd temp
$ git clone https://github.com/ecominds/micro-service.git
$ cd micro-service
$ git checkout boot-rest-api-with-h2-and-docker
$ cd ..

# Rename the project folder to dept-service
$ mv micro-service dept-service
```

2. Copy the above `dept-service` from temp to this project `docker-compose-test`

3. Change the name ref to `dept-service` in `pom.xml`
```
<groupId>com.github.ecominds</groupId>
<artifactId>dept-service</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>dept-service</name>
```
Also,
```
<scm>
	<connection>scm:git:git://github.com/ecominds/docker-compose-test.git</connection>
	<developerConnection>scm:git:git@github.com:ecominds/docker-compose-test.git</developerConnection>
	<url>https://github.com/ecominds/docker-compose-test</url>
</scm>
```

## Create another micro-service, `user-service`
1. Created a copy of `dept-service` with the name `user-service` and made nacessary change to make it run as micro-service. 

2. Also, integrated `user-service` with `dept-service` using `rest-template`

3. Test both `dept-service` and `user-service` on local system (or IDE)

4. Create docker images
```
# Create dept-service docker image
docker-compose-test $ docker build -t dept-service .\dept-service\

#Create user-service docker image
docker-compose-test $ docker build -t user-service .\user-service\
```

### With Docker appproach, start container one by one using `docker run` command
Since both the docker containers run in default network, they cannot communicate with each other, so they must use a custom network
<details>
<summary>Click click to view the steps</summary>
  
1. Execute the below command to create a custom network `dev-env-net` if it is not available
```
$ docker network create dev-env-net
```

2. Start `dept-service`
```
$ docker run -it -d --network=dev-env-net -p 8081:8081 dept-service
```

3. Inspect `dept-service` container to find out the IP which need to be configured in `user-service`

4. Start `user-service` with the same network as `dept-service`
```
$ docker run -it --rm --network=dev-env-net -e "ecominds.endpoint.dept-service.base-path=http://172.20.0.2:8081/api/departments/" -p 8082:8082 user-service
```
</details>

### Or Use `docker-compose` appproach
1. Create a file `docker-compose.yml` and define each services
```
version: "3.9"
services:
  department:
    .....
	.....
  user:
    ....
    ....
```
2. Each services have some attributes, snippet below:
```
version: "3.9"
services:
  service1:
    container_name:<some-name>
	ports:
	  - <exposed-port>:<container-port>
```

3. Let's configure the attributes for both `department` and `user` service. `environment` attribute is included to override the properties defined in `application.properties` file included as part of the image.

```
version: "3.9"
services:
  department: 
    container_name: dept_service
    build: 
      context: ./dept-service
      dockerfile: Dockerfile
    ports:
      - 8081:8081
  user:
    container_name: user_service
    build:
      context: ./user-service
      dockerfile: Dockerfile
    ports:
      - 8082:8082
	environment:
      - "ecominds.endpoint.dept-service.base-path=http://<DEPT_SERVICE_HOST_IP>:8081/api/departments/"
```
4. Now, the question is, what to configure for `DEPT_SERVICE_HOST_IP`!

**localhost**: when we run the application, `dept-service` will work perfectly fine when we access the service on host machine. But, the `user-service` will fail to connect to `dept-service` because the services are isolated and running in different containers.

First of all, `localhost:<PORT>` does not behave the same way as it works on our system/host-machine. In docker container, **localhost always refers to the current container.**

**Static-IP**: The second option is to get the IP of the `dept-service` container and configure it in the `docker-compose.yml`. But for that, we'll to build the images using docker-compose and then run them as well, so that we can get the IP of the containers.

**Custom network**: We can create custom network and attach the containers to it.

We are now stuck becauee of **Cross-container networking** issue.

And this can be easily remediated in `docker-compose` using service name instead of any hard-coded IP/adddress. So the dept-service url should be `
`http://department:8081/api/departments/`. We have replaced `DEPT_SERVICE_HOST_IP` with `department`.

5. If a service depends on another service or we want to provide a sequence in loading the services, we can use `depends_on` attribute

6. The final `docker-compose.yml` content:
```
version: "3.9"
services:
  department: 
    container_name: dept_service
    build: 
      context: ./dept-service
      dockerfile: Dockerfile
    ports:
      - 8081:8081
  user:
    container_name: user_service
    build:
      context: ./user-service
      dockerfile: Dockerfile
    ports:
      - 8082:8082
    environment:
      - "ecominds.endpoint.dept-service.base-path=http://department:8081/api/departments/"
    depends_on:
      - department
```

## Build and run the services
1. Execute the command `docker-compose build` to build the images
2. Execute `docker-compose up -d` to start the service. Tag `-d` is supplied to run the services in *detached mode*

### Ref Links:
https://forums.docker.com/t/localhost-and-docker-compose-networking-issue/23100/5
