package flink_geode.integration;

import java.util.Map;

import org.apache.flink.api.java.tuple.Tuple2;

// http://www.programcreek.com/java-api-examples/index.php?api=org.apache.flink.api.java.operators.DataSource
 public class GeodeRegionEntry extends Tuple2<String, String> {
     private static final long serialVersionUID = -1;
     
     public GeodeRegionEntry(Map.Entry<String, String> entry){
    	 this.f0 = entry.getKey();
    	 this.f1 = entry.getValue();
     }
 }