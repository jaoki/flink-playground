package flink_geode;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import flink_geode.integration.GeodeExecutionEnvironment;

public class WordCount {

	public static void main(String[] args) throws Exception {
		GeodeUtil.makeData();


		// set up the execution environment
		final GeodeExecutionEnvironment geodeBasedEnv = new GeodeExecutionEnvironment();
		
//		DataSet<Tuple2<String, String>> data = geodeBasedEnv.fromRegion("region1");
		

//		assert data != null;

		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		// get input data
		DataSet<String> text = env.fromElements(
				"To be, or not to be,--that is the question:--",
				"Whether 'tis nobler in the mind to suffer",
				"The slings and arrows of outrageous fortune",
				"Or to take arms against a sea of troubles,"
				);

		// split up the lines in pairs (2-tuples) containing: (word,1)
		FlatMapOperator<String, Tuple2<String, Integer>> flatten = text.flatMap(new LineSplitter());

		// group by the tuple field "0" and sum up tuple field "1"
		UnsortedGrouping<Tuple2<String, Integer>> grouped = flatten.groupBy(0);

		DataSet<Tuple2<String, Integer>> counts = grouped.sum(1);

		// execute and print result
		counts.print();

	}

	// http://www.programcreek.com/java-api-examples/index.php?api=org.apache.flink.api.java.operators.DataSource
	 public static class GeodeRegision extends Tuple2<String, String> {
         private static final long serialVersionUID = -1;
     }

	/**
	 * Implements the string tokenizer that splits sentences into words as a user-defined
	 * FlatMapFunction. The function takes a line (String) and splits it into
	 * multiple pairs in the form of "(word,1)" (Tuple2<String, Integer>).
	 */
	public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
			// normalize and split the line
			String[] tokens = value.toLowerCase().split("\\W+");

			// emit the pairs
			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Tuple2<String, Integer>(token, 1));
				}
			}
		}
	}
}
