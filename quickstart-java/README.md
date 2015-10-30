mvn clean package -Pbuild-jar
mvn exec:java  -Dexec.mainClass=org.myorg.quickstart.WordCount

