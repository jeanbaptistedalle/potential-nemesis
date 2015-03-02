package recherche.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

class ValueComparator implements Comparator<String> {
 
    Map<String, List<Integer>> map;
 
    public ValueComparator(Map<String, List<Integer>> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a).size() >= map.get(b).size()) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys 
    }
}