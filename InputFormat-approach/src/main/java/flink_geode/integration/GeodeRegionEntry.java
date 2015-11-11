package flink_geode.integration;

import java.io.Serializable;
import java.util.Map;

public class GeodeRegionEntry<K, V> implements Serializable{
	private static final long serialVersionUID = 1L;

	private K key;
	private V value;

    public GeodeRegionEntry(){}
     
    public GeodeRegionEntry(Map.Entry<K, V> entry){
		this.key = entry.getKey();
    	this.value = entry.getValue();
    }
    
    @Override
    public String toString() {
    	return key.toString() + ":" + value.toString();
    }
    
    public K getKey() { return key; }
	public void setKey(K key) { this.key = key; }

	public V getValue() { return value; }
	public void setValue(V value) { this.value = value; }
	
 }
