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
- Make GeodeEntry etc. generic

o Use InputFormat to get geode data
	https://github.com/apache/flink/blob/master//flink-core/src/main/java/org/apache/flink/api/common/io/InputFormat.java
    GeodeInputFormat : See FileInputFormat and JDBCInputFormat
	GeodeInputSplit : See FileInputSplit (A FileInputSplit is based on a flie block. Whatabout Geode?)
	then pass it to ExecutionEnvironment.createInput(inputFormat) 

|             | TextInputFormat | GeodeInputFormat |
|-------------|-----------------|------------------|
| InputFormat | a file          | a region         |
| Split       | a file block    | some records     |
| Record      | a line          | a key-value      |


### Note
InputFormat
-> FileInputFormat
   -> DelimitedInputFormat
      -> TextInputFormat


