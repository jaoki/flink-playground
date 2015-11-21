package flink_playground;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.testng.annotations.Test;


public class WordCountReadTextFileTest {

	@Test
	public void test1() throws Exception {

		final ExecutionEnvironment env = ExecutionEnvironment.createLocalEnvironment();
		String currentDir = System.getProperty("user.dir");
		DataSet<String> data = env.readTextFile("file:///" + currentDir + "/target/test-classes/text1.txt");
		DataSet<String> filteredData = data.filter(new FilterFunction<String>(){
			@Override
			public boolean filter(String value) throws Exception {
				return value.startsWith("[ERROR]");
			}
		});
		filteredData.writeAsText("file:///" + currentDir + "/target/output1.txt");
		env.execute();

	}

}
