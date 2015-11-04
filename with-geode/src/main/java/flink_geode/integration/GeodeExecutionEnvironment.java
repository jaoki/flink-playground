package flink_geode.integration;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

import flink_geode.GeodeUtil;

public class GeodeExecutionEnvironment extends ExecutionEnvironment{

	public DataSet<GeodeRegionEntry> fromRegion(String regionName){
		Cache cache = GeodeUtil.getCache();
		Region<String, String> region2 = cache.<String, String>getRegion(regionName);
		Iterator<Entry<String, String>> iterator = region2.entrySet().iterator();

//		Region.Entry<String, String>[] entries = new Region.Entry<String, String>[1];
		GeodeRegionEntry[] regionEntries = new GeodeRegionEntry[region2.size()];
		for(int i = 0; iterator.hasNext() ; i++) {
			Entry<String, String> entry = iterator.next();
			GeodeRegionEntry regionEntry = new GeodeRegionEntry(entry);
			regionEntries[i] = regionEntry;
		}
		DataSet<GeodeRegionEntry> data = this.fromElements(regionEntries);

		return data;
		

//		Object[] array = region2.entrySet().toArray();
//		ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>(region2.entrySet());
//
//		arrayList.toArray(new Map.Entry<String, String>[arrayList.size()]));
//		DataSet<String> text = this.fromElements(arrayList);
//
////	    for (Map.Entry<String, String>  entry : region2.entrySet()) {
////	    	System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
////	    }
//		TypeInformation<Region.Entry<String, String>> type = TypeExtractor.getForObject(region2.getEntry(""));
//		final ExecutionConfig config = new ExecutionConfig();
//		//		return new DataSource<T>(this, b, c, regionName);
////		Arrays.asList(a)
//		TypeSerializer<Entry<String, String>> serializer = type.createSerializer(config);
//		CollectionInputFormat<T> inputFormat = new CollectionInputFormat<T>(arrayList, serializer);
//		return new DataSource<T>(this, inputFormat, type, regionName);
		

		// ExecutionEnvironment.fromCollection()
//		data = new X[]
//		return fromCollection(Arrays.asList(data), TypeExtractor.getForObject(data[0]), Utils.getCallLocationName());
//		return new DataSource<X>(this, new CollectionInputFormat<X>(data, type.createSerializer(config)), type, callLocationName);
//		return null;
		
	}

//	@Override
//	public JobExecutionResult execute(String jobName) throws Exception {
////		this.execute();
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String getExecutionPlan() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
