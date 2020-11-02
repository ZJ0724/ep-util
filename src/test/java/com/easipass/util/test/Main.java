package com.easipass.util.test;

import com.easipass.util.core.component.SWGDPARADatabase;

public class Main {

    public static void main(String[] args) {
        SWGDPARADatabase swgdparaDatabase = new SWGDPARADatabase();


        System.out.println(swgdparaDatabase.isPrimaryKey("CIQ_CODE", "HS_CODE"));

        swgdparaDatabase.close();

    }

}