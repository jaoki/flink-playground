package flink_geode.integration;

import java.io.Serializable;
import java.util.Map;

public class GeodeRegionEntry implements Serializable{
	private static final long serialVersionUID = 1L;

	private String key;
	private String value;

    public GeodeRegionEntry(){}
     
    public GeodeRegionEntry(Map.Entry<String, String> entry){
		this.key = entry.getKey();
    	this.value = entry.getValue();
    }
    
    @Override
    public String toString() {
    	return key + ":" + value;
    }
    
    public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }

	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }
	
 }
