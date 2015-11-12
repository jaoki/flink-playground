package flink_geode.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.flink.api.common.io.DefaultInputSplitAssigner;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.InputSplitAssigner;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

import flink_geode.GeodeUtil;

public class GeodeInputFormat<K, V> implements InputFormat<GeodeRegionEntry<K, V>, GeodeInputSplit<K, V>> {

	private static final long serialVersionUID = 1L;

	private String regionName = null;
	private int currentRecord;
	private GeodeInputSplit<K, V> currentSplit;
	
	public GeodeInputFormat(String regionName){
		this.regionName = regionName;
	}

	@Override
	public void configure(Configuration parameters) {}

	@Override
	public BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
		return cachedStatistics;
	}

	@Override
	public InputSplitAssigner getInputSplitAssigner(GeodeInputSplit<K, V>[] splits) {
		return new DefaultInputSplitAssigner(splits);
	}

	@Override
	public GeodeInputSplit<K, V>[] createInputSplits(int minNumSplits) throws IOException {

		Cache cache = GeodeUtil.getCache();
		Region<K, V> region = cache.<K, V>getRegion(this.regionName);

		int recordPerSplit = region.size() / minNumSplits;
		Iterator<Entry<K, V>> regionIter = region.entrySet().iterator();
		
		@SuppressWarnings("unchecked")
		GeodeInputSplit<K, V>[] result = (GeodeInputSplit<K, V>[])new GeodeInputSplit[minNumSplits];

		for(int splitNumber = 0 ; splitNumber < minNumSplits; splitNumber++){
			List<GeodeRegionEntry<K, V>> data = new ArrayList<>();
			for(int i = 0; i < recordPerSplit ; i++){
				if(!regionIter.hasNext()){
					break;
				}
				Entry<K, V> entry = regionIter.next();
				data.add(new GeodeRegionEntry<K, V>(entry));
			}
			GeodeInputSplit<K, V> gis = new GeodeInputSplit<K, V>(splitNumber, minNumSplits, data);
			result[splitNumber] = gis;
		}

		return result;
	}

	@Override
	public void open(GeodeInputSplit<K, V> split) throws IOException {
		this.currentRecord = 0;
		this.currentSplit = split;
	}


	@Override
	public boolean reachedEnd() throws IOException {
		return currentSplit.getData().size() < currentRecord+1;
	}

	@Override
	public GeodeRegionEntry<K, V> nextRecord(GeodeRegionEntry<K, V> reuse) throws IOException {
		GeodeRegionEntry<K, V> entry = currentSplit.getData().get(currentRecord);
		currentRecord++;
		return entry;
	}

	@Override
	public void close() throws IOException {}

}
