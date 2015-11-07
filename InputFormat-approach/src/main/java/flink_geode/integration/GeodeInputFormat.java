package flink_geode.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.flink.api.common.io.GenericInputFormat;
import org.apache.flink.core.io.GenericInputSplit;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;

import flink_geode.GeodeUtil;

public class GeodeInputFormat extends GenericInputFormat<GeodeRegionEntry> {

	private static final long serialVersionUID = 1L;
	
	private String regionName = null;
	private int currentRecord;
	private GeodeInputSplit currentSplit;
	private Region<String, String> region;
	
	public GeodeInputFormat(String regionName){
		this.regionName = regionName;
	}

	@Override
	public void open(GenericInputSplit split) throws IOException {
		this.currentRecord = 0;
		this.currentSplit = (GeodeInputSplit)split;
		Cache cache = GeodeUtil.getCache();
		this.region = cache.<String, String>getRegion(this.regionName);
	}


	@Override
	public GenericInputSplit[] createInputSplits(int minNumSplits) throws IOException {

		final List<GeodeInputSplit> inputSplits = new ArrayList<GeodeInputSplit>(minNumSplits);

		int recordPerSplit = region.size() / minNumSplits;
		Iterator<Entry<String, String>> regionIter = region.entrySet().iterator();

		for(int splitNumber = 0 ; splitNumber < minNumSplits; splitNumber++){
			List<GeodeRegionEntry> data = new ArrayList<>();
			for(int i = 0; i < recordPerSplit ; i++){
				if(!regionIter.hasNext()){
					break;
				}
				Entry<String, String> entry = regionIter.next();
				data.add(new GeodeRegionEntry(entry));
			}
			GeodeInputSplit gis = new GeodeInputSplit(splitNumber, minNumSplits, data);
			inputSplits.add(gis);
		}

		return inputSplits.toArray(new GeodeInputSplit[inputSplits.size()]);
	}

	@Override
	public boolean reachedEnd() throws IOException {
		return currentSplit.getData().size() < currentRecord+1;
	}

	@Override
	public GeodeRegionEntry nextRecord(GeodeRegionEntry reuse) throws IOException {
		GeodeRegionEntry entry = currentSplit.getData().get(currentRecord);
		currentRecord++;
		return entry;
	}

}
