package flink_geode.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CollectionInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.typeutils.TypeExtractor;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.Region.Entry;

import flink_geode.GeodeUtil;

public class GeodeExecutionEnvironment extends ExecutionEnvironment{

	public <T> DataSet<T> fromRegion(String regionName){
		Cache cache = GeodeUtil.getCache();
		Region<String, String> region2 = cache.<String, String>getRegion(regionName);
		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>(region2.entrySet());

//	    for (Map.Entry<String, String>  entry : region2.entrySet()) {
//	    	System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
//	    }
		TypeInformation<Region.Entry<String, String>> type = TypeExtractor.getForObject(region2.getEntry(""));
		final ExecutionConfig config = new ExecutionConfig();
		//		return new DataSource<T>(this, b, c, regionName);
//		Arrays.asList(a)
		TypeSerializer<Entry<String, String>> serializer = type.createSerializer(config);
		CollectionInputFormat<T> inputFormat = new CollectionInputFormat<T>(arrayList, serializer);
		return new DataSource<T>(this, inputFormat, type, regionName);
		

		// ExecutionEnvironment.fromCollection()
//		data = new X[]
//		return fromCollection(Arrays.asList(data), TypeExtractor.getForObject(data[0]), Utils.getCallLocationName());
//		return new DataSource<X>(this, new CollectionInputFormat<X>(data, type.createSerializer(config)), type, callLocationName);
//		return null;
		
	}

	@Override
	public JobExecutionResult execute(String jobName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getExecutionPlan() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
