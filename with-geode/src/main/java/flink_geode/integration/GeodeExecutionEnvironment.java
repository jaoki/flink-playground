package flink_geode.integration;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class GeodeExecutionEnvironment extends ExecutionEnvironment{

	public <T> DataSet<T> fromRegion(String regionName){
		return null;
		
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
