package flink_geode.integration;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.Plan;
import org.apache.flink.api.common.PlanExecutor;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

import flink_geode.GeodeUtil;

public class GeodeExecutionEnvironment extends ExecutionEnvironment{

	public DataSet<GeodeRegionEntry> fromRegion(String regionName){
		// TODO this should not be loaded here and should be lazy evaluation.
		Cache cache = GeodeUtil.getCache();
		Region<String, String> region2 = cache.<String, String>getRegion(regionName);
		Iterator<Entry<String, String>> iterator = region2.entrySet().iterator();

		GeodeRegionEntry[] regionEntries = new GeodeRegionEntry[region2.size()];
		for(int i = 0; iterator.hasNext() ; i++) {
			Entry<String, String> entry = iterator.next();
			GeodeRegionEntry regionEntry = new GeodeRegionEntry(entry);
			regionEntries[i] = regionEntry;
		}
		DataSet<GeodeRegionEntry> data = this.fromElements(regionEntries);

		return data;
		
	}

	@Override
	public JobExecutionResult execute(String jobName) throws Exception {
		Plan p = createProgramPlan(jobName);
		
		PlanExecutor executor = PlanExecutor.createLocalExecutor(configuration);
		executor.setPrintStatusDuringExecution(p.getExecutionConfig().isSysoutLoggingEnabled());
		this.lastJobExecutionResult = executor.executePlan(p);
		return this.lastJobExecutionResult;
	}

	@Override
	public String getExecutionPlan() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
