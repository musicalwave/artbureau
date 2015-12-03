package com.itsoft.ab.sys;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: itertychnyi
 * Date: 29.04.14
 * Time: 11:40
 */
public class MapMaster {
    public static String ASC = "ASC";
    public static String DESC = "DESC";

    public static Map<Integer,Integer> sortByValue(Map<Integer,Integer> daysMap, String order) {
        if(order.equals(ASC)){
            return sortByComparator(daysMap);
        }
        throw new UnsupportedOperationException();
    }

    private static Map sortByComparator(Map unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
