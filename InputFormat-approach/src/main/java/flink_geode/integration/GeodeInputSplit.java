package flink_geode.integration;

import java.util.List;

import org.apache.flink.core.io.GenericInputSplit;

public class GeodeInputSplit extends GenericInputSplit {

	private static final long serialVersionUID = 1L;
	
	private List<GeodeRegionEntry> data;
	
	public GeodeInputSplit(int partitionNumber, int totalNumberOfPartitions, List<GeodeRegionEntry> data) {
		super(partitionNumber, totalNumberOfPartitions);
		this.data = data;
	}

	public List<GeodeRegionEntry> getData() { return data; }


}
