## User registration & login using spring-boot


```sh
$ mvn spring-boot:run
or
$ mvn clean install
$ java -jar target/user-spring
```

#### API List : 
* POST - `localhost:8086/registration`   
* POST `localhost:8086/login`  
* GET `localhost:8086//login/{email}` 
* GET `localhost:8086/logout/{email}` 