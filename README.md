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
