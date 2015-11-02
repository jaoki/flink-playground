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

	public static void startLocator(){
		LocatorLauncher locatorLauncher = new LocatorLauncher.Builder()
			.setMemberName("locator1")
//			.setPort(13489)
			.build();
		locatorLauncher.start();
		locatorLauncher.waitOnStatusResponse(30, 5, TimeUnit.SECONDS);
	}
	
	public static void startServer(){
		ServerLauncher serverLauncher = new ServerLauncher.Builder()
				.setMemberName("locator1")
//				.setServerPort(40405)
				.build();
		serverLauncher.start();
		// serverLauncher.wait
	}
	
	public static Cache getCache(){
		return new CacheFactory()
//	      		.addPoolLocator("localhost", 10334)
				.set("locators", "localhost[10334]")
	      		.create();
	}
	
	public static void hello(){
		Cache cache = getCache();

//	    Region<String, String> region = cache
//	    		.<String, String>createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
//	    		.create("region");

	    Region<String, String> region = cache
	    		.<String, String>createRegionFactory(RegionShortcut.REPLICATE)
	    		.create(REGION_NAME);

	    region.put("1", "Hello");
	    region.put("2", "World");
	    
	    Region<String, String> region2 = cache.<String, String>getRegion(REGION_NAME);

	    for (Map.Entry<String, String>  entry : region2.entrySet()) {
	    	System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
	    }
	    cache.close();
	}

}
