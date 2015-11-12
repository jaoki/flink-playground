package flink_geode.integration;

import java.util.List;

import org.apache.flink.core.io.GenericInputSplit;

public class GeodeInputSplit<K, V> extends GenericInputSplit {

	private static final long serialVersionUID = 1L;
	
	private List<GeodeRegionEntry<K, V>> data;
	
	public GeodeInputSplit(int partitionNumber, int totalNumberOfPartitions, List<GeodeRegionEntry<K, V>> data) {
		super(partitionNumber, totalNumberOfPartitions);
		this.data = data;
	}

	public List<GeodeRegionEntry<K, V>> getData() { return data; }


}
