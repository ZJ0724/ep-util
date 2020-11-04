package com.easipass.util.test;

import com.easipass.util.core.component.SWGDPARADatabase;
import com.easipass.util.core.database.MdbDatabase;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {


//        MdbDatabase mdbDatabase = new MdbDatabase("E:\\ZJ\\epProject\\参数库\\release\\Update\\Data\\parameterDb.mdb");

//        mdbDatabase.test();

//        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();
//
//        System.out.println(swgdparaDatabase.getFields("SWGDPARA.CIQ_ORG"));

        int[] a = new int[]{5, 2, 3, 43345, 3423, 234, 2, 123123, 123123, 1123123123};//4

        System.out.println(Arrays.toString(a));

        for (int i = 0 ;i < a.length; i++) {//3,0123
            for (int j = i + 1;j < a.length; j++) {//1234
                if (a[i] > a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(a));

    }

}