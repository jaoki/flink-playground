package flink_geode;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.util.Collector;

import flink_geode.integration.GeodeInputFormat;
import flink_geode.integration.GeodeRegionEntry;

public class WordCount {

	public static void main(String[] args) throws Exception {
		GeodeUtil.makeData();

		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		TypeInformation<GeodeRegionEntry<String, String>> geodeRegionEntryTypeInfo = TypeExtractor.getForObject(new GeodeRegionEntry<String, String>());

		GeodeInputFormat<String, String> geodeInputFormat = new GeodeInputFormat<String, String>("region1");

		DataSet<Tuple2<String, Integer>> counts = env.createInput(geodeInputFormat, geodeRegionEntryTypeInfo)
					.flatMap(new EntryValueSplitter<String, String>())
					.groupBy(0)
					.sum(1);
		counts.print();
	}

	private static final class EntryValueSplitter<K, V> implements FlatMapFunction<GeodeRegionEntry<K, V>, Tuple2<String, Integer>> {
		private static final long serialVersionUID = 1L;

		@Override
		public void flatMap(GeodeRegionEntry<K, V> entry, Collector<Tuple2<String, Integer>> out) {
			String[] tokens = entry.getValue().toString().toLowerCase().split("\\W+");

			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Tuple2<String, Integer>(token, 1));
				}
			}
		}
	}

}
