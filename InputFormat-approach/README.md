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
- Use InputFormat to get geode data
	https://github.com/apache/flink/blob/master//flink-core/src/main/java/org/apache/flink/api/common/io/InputFormat.java
    GeodeInputFormat : See FileInputFormat and JDBCInputFormat
	GeodeInputSplit : See FileInputSplit (A FileInputSplit is based on a flie block. Whatabout Geode?)
	then pass it to ExecutionEnvironment.createInput(inputFormat) 

- Flink can handle HadoopInputFormat and any Hadoop's InputFormat.
	Geode happends to have GFInputFormat https://apache.googlesource.com/incubator-geode/+/sga2/gemfire-core/src/main/java/com/gemstone/gemfire/cache/hdfs/internal/hoplog/mapreduce/GFInputFormat.java
	HadoopInputFomat<GFKey, PersistedEventImpl>
	can I use it?

- Can I use file scheme aproach described here https://ci.apache.org/projects/flink/flink-docs-release-0.8/example_connectors.html
"geode://...." ---> maybe not?? 

- what is sink?




