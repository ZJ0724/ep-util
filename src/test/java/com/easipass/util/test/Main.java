package com.easipass.util.test;

import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.database.MdbDatabase;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {


        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");


        Set<Map.Entry<String, String>> entries = map.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            System.out.println("key:" + entry.getKey());
            System.out.println("value:" + entry.getValue());
        }

        Set<String> keys = map.keySet();

        for (String key : keys) {
            System.out.println("key:" + key);
        }

        Collection<String> values = map.values();
        for (String v : values) {
            System.out.println("value:" + v);
        }

        // 迭代器
        Iterator<Map.Entry<String, String>> iterator1 = entries.iterator();
        while (iterator1.hasNext()) {
            Map.Entry<String, String> next = iterator1.next();
            System.out.println("key:" + next.getKey());
            System.out.println("value:" + next.getValue());
        }

    }

}