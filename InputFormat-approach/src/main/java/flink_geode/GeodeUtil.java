package flink_geode;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionShortcut;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import com.gemstone.gemfire.distributed.LocatorLauncher;
import com.gemstone.gemfire.distributed.ServerLauncher;

public class GeodeUtil {
	private static final String REGION_NAME = "region1";

	public static Cache getCache(){
		return new CacheFactory()
//	      		.addPoolLocator("localhost", 10334)
				.set("locators", "localhost[10334]")
	      		.create();
	}
	
	public static void makeData(){
		Cache cache = getCache();

	    Region<String, String> region = cache
	    		.<String, String>createRegionFactory(RegionShortcut.REPLICATE)
	    		.create(REGION_NAME);

	    region.put("1", "Hello World");
	    region.put("2", "World Wide Web");
	    region.put("3", "World Wide Web");
	    region.put("4", "World Wide Web");
	    region.put("5", "Web2.0");
	    region.put("6", "Machine Learning");
	    region.put("7", "World Wide Web");
	    region.put("8", "World Wide Web");
	    region.put("9", "World Wide Web");
	    region.put("10", "World Wide Web");
	    region.put("11", "World Wide Web");
	    region.put("12", "Mobile");
	    
//	    extracted(cache);
//	    cache.close();
	}

	private static void extracted() {
		Cache cache = getCache();
		Region<String, String> region2 = cache.<String, String>getRegion(REGION_NAME);

	    for (Map.Entry<String, String>  entry : region2.entrySet()) {
	    	System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
	    }
	}

}
