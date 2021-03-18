// import java.util.stream.Collectors;  
// import java.util.LinkedHashMap;
// import java.util.HashMap;
// import java.util.Map;

import java.util.*;
    

class StrFSeconds {
    public static void main(String[] args) {
        // System.out.println( "Hello World");

        // System.out.println( StrFSeconds( (float)1.123, "", 1));
        System.out.println( StrFSeconds( (float)1.123));
    }

    public static String StrFSeconds( float seconds, String formatString, int nDecimal) {
        // Arrays in Java
        // https://www.w3schools.com/java/java_hashmap.asp
        // https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/
        // https://www.journaldev.com/43792/sort-hashmap-by-value-java

        // Map<String, Float> units = new HashMap<String, Float>();
        Map<String, Float> units = new HashMap<>();
        units.put("w", (float)604800);
        units.put("d", (float)86400);
        units.put("h", (float)3600);
        units.put("m", (float)60);
        units.put("s", (float)1);
        units.put("l", (float).001);
        units.put("f", (float).000001);

        // Printing all items of the time units Map 
        System.out.println( "# Unordered results:");
        for (Map.Entry<String, Float> set : units.entrySet()) { 
            System.out.println(set.getKey() + " = " + set.getValue()); 
        } 

        // Ordering items in a hashmap
        // https://www.baeldung.com/java-hashmap-sort
        // https://www.benchresources.net/sorting-hashmap-by-keys-and-values-using-stream-in-java-8/
        // https://howtodoinjava.com/java/sort/java-sort-map-by-values/
        
        Map<String, Float> result = new LinkedHashMap<>();
        units.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEachOrdered(r -> result.put(r.getKey(), r.getValue()));

        units = result;
        // result = null;
        // System.out.println(result);

        // Printing all items ordered of the time units Map 
        System.out.println( "# Ordered results:");
        for (Map.Entry<String, Float> unit : 
            units.entrySet()) { 
            System.out.println( unit.getKey() + " " + unit.getValue());
        }

        
        return "Hello";
    }

    // Default parameter values? 
    // Use method overloading. 
    // https://stackoverflow.com/a/1038401 
    public static String StrFSeconds( float seconds, String formatString) {
        return StrFSeconds( seconds, formatString, 3);
    }

    public static String StrFSeconds( float seconds) {
        return StrFSeconds( seconds, "%h2:%m2:%s", 3);
    }



}
