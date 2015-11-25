package flink_playground;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.testng.annotations.Test;


public class WordCountReadTextFileTest {

	@Test
	public void test1() throws Exception {

		final ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();     // [1]
//		env.setParallelism(1);
		String currentDir = System.getProperty("user.dir");
		DataSet<String> data = env.readTextFile("file:///" + currentDir + "/target/test-classes/text1.txt");   // [2]
		data.filter(new FilterFunction<String>(){                     // [3]
				@Override
				public boolean filter(String value) throws Exception {
					return value.startsWith("[ERROR]");
				}
			})
			.writeAsText("file:///" + currentDir + "/target/output1.txt");   // [4]
		env.execute();   // [5]

	}

}
