package flink_geode;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import flink_geode.integration.GeodeExecutionEnvironment;
import flink_geode.integration.GeodeRegionEntry;

public class WordCount {

	public static void main(String[] args) throws Exception {
		GeodeUtil.makeData();

		// set up the execution environment
		final GeodeExecutionEnvironment geodeBasedEnv = new GeodeExecutionEnvironment();
		
		DataSet<GeodeRegionEntry> data = geodeBasedEnv.fromRegion("region1");
		FlatMapOperator<GeodeRegionEntry, Tuple2<String, Integer>> flatten = data.flatMap(new EntryValueSplitter());
		UnsortedGrouping<Tuple2<String, Integer>> grouped = flatten.groupBy(0);
		DataSet<Tuple2<String, Integer>> counts = grouped.sum(1);
		counts.print();


//		FlinkWordCount.wordCount();

	}

	private static final class EntryValueSplitter implements FlatMapFunction<GeodeRegionEntry, Tuple2<String, Integer>> {
		private static final long serialVersionUID = 1L;

		@Override
		public void flatMap(GeodeRegionEntry entry, Collector<Tuple2<String, Integer>> out) {
			String[] tokens = entry.f1.toLowerCase().split("\\W+");

			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Tuple2<String, Integer>(token, 1));
				}
			}
		}
	}

}
