### Run geode locator
run geode locator (no server required)
java -server -classpath gemfire-assembly/build/install/apache-geode/lib/gemfire-core-dependencies.jar com.gemstone.gemfire.distributed.LocatorLauncher start locator1 --redirect-output  &



### Excute this
```
mvn compile
mvn exec:exec
mvn exec:exec -Pdebug // remote debug
```

### TODO
- Can I use file scheme aproach described here https://ci.apache.org/projects/flink/flink-docs-release-0.8/example_connectors.html
"geode://...."

- what is sink


