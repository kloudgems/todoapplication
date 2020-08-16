
To run the application change the db credential in the application.properties/application-prod.properties file
and execute the command

mvn package <br />
java -jar -Dspring.profiles.active=application-prod todoapplication-0.0.1-SNAPSHOT.jar
