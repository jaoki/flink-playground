package flink_geode.integration;

import java.util.Collection;
import java.util.Map;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CollectionInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.typeutils.TypeExtractor;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

import flink_geode.GeodeUtil;

public class GeodeExecutionEnvironment extends ExecutionEnvironment{

	public <T> DataSet<T> fromRegion(String regionName){
		Cache cache = GeodeUtil.getCache();
		Region<String, String> region2 = cache.<String, String>getRegion(regionName);

	    for (Map.Entry<String, String>  entry : region2.entrySet()) {
	    	System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
	    }
		InputFormat<T, ?> b;
		TypeInformation<T> c;
		T d = (T) "";
		TypeInformation<T> type = TypeExtractor.getForObject(d);
		final ExecutionConfig config = new ExecutionConfig();
		//		return new DataSource<T>(this, b, c, regionName);
		return new DataSource<T>(this, new CollectionInputFormat<T>(region2.values(), type.createSerializer(config)), type, regionName);
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
