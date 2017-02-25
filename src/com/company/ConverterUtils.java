package com.company;


import java.util.ArrayList;
import java.util.List;


public class ConverterUtils {

    public static List<StatisticDto> convertStatistic(List<String[]> data){
        System.out.println("Converting data ...");
        List<StatisticDto> result = new ArrayList<>();
        for (String[] objects : data) {
            if(objects.length < 10) continue;
            StatisticDto e = new StatisticDto();
            e.setId(objects[0]);
            e.setProductId(objects[1]);
            e.setUserId(objects[2]);
            e.setProfileName(objects[3]);
            e.setText(objects[9]);
            result.add(e);
        }
        return result;
    }

}
