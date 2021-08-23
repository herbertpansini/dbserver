# dbserver

https://dbserver-app.herokuapp.com/swagger-ui.html

# instruções

dbserver$ docker-compose up -d

dbserver$ mvn clean install -DskipTests

dbserver$ mvn spring-boot:run -Drun.jvmArguments="-Xmx256m"
