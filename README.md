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

2. Copy the above `dept-service` from temp to this project `micro-service-docker-compose`

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
	<connection>scm:git:git://github.com/ecominds/micro-service-docker-compose.git</connection>
	<developerConnection>scm:git:git@github.com:ecominds/micro-service-docker-compose.git</developerConnection>
	<url>https://github.com/ecominds/micro-service-docker-compose</url>
</scm>
```

## Create another micro-service, `user-service`
1. Created a copy of `dept-service` with the name `user-service` and made nacessary change to make it run as micro-service. 

2. Also, integrated `user-service` with `dept-service` using `rest-template`

3. Test both `dept-service` and `user-service` on local system (or IDE)

4. Create docker images
```
# Create dept-service docker image
micro-service-docker-compose $ docker build -t dept-service .\dept-service\

#Create user-service docker image
micro-service-docker-compose $ docker build -t user-service .\user-service\
```

5. Execute the below command to create a custom network `dev-env-net` if it is not available
```
$ docker network create dev-env-net
```

6. Start `dept-service`
```
$ docker run -it -d --network=dev-env-net -p 8081:8081 dept-service
```

7. Inspect `dept-service` container to find out the IP which need to be configured in `user-service`

8. Start `user-service` with the same network as `dept-service`
```
$ docker run -it --rm --network=dev-env-net -e "ecominds.endpoint.dept-service.base-path=http://172.20.0.2:8081/api/departments/" -p 8082:8082 user-service
```
