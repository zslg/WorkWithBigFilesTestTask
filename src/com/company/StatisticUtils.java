package com.company;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class StatisticUtils {

    private final static int responseLimit = 1000;

//    but better to use database functional for this operations
    public static List<String> get1000MostActiveUsers(List<StatisticDto> statistic){
        System.out.println("Analyzing data ...");
        Map<String, String> userIdToUserNameMap = statistic.stream().collect(Collectors.toMap(StatisticDto::getUserId, StatisticDto::getProfileName,(s, s2) -> s));
        Map<String, Long> userOperationsCount = statistic.stream().collect(Collectors.groupingBy(StatisticDto::getUserId, Collectors.counting()));
        List<String> result = userOperationsCount.entrySet().stream().sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue())).limit(responseLimit)
                .map(stringLongEntry -> userIdToUserNameMap.get(stringLongEntry.getKey())).collect(Collectors.toList());
        return result;
    }

    //    but better to use database functional for this operations
    public static List<String> get1000MostCommentedProducts(List<StatisticDto> statistic){
        System.out.println("Analyzing data ...");
        Map<String, Long> productOperationsCount = statistic.stream().collect(Collectors.groupingBy(StatisticDto::getProductId, Collectors.counting()));
        List<String> result = productOperationsCount.entrySet().stream().sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue())).limit(responseLimit)
                .map(Map.Entry::getKey).collect(Collectors.toList());
        return result;
    }

    //    but better to use database functional or Apache Lucene library for this operations
    public static List<String> get1000MostUsedWords(List<StatisticDto> statistic){
        Main.getFreeMemory();
        System.out.println("Analyzing data ...");
        Map<String, Long> wordsUseCount = statistic.stream().map(statisticDto -> {
            String text = statisticDto.getText();
            if (text != null && !text.isEmpty()) {
                String[] split = text.split(" ");
                for (int i = 0; i < split.length; i++) {
//                    use string pool while working with bug amount of strings
                    split[i] = split[i].intern();
                }
                statisticDto.setTextWords(Arrays.asList(split));
            }
            return statisticDto;
        }).flatMap(statisticDto -> statisticDto.getTextWords().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Main.getFreeMemory();

        List<String> collect = wordsUseCount.entrySet().stream().sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue())).limit(responseLimit).map(Map.Entry::getKey).collect(Collectors.toList());

        Main.getFreeMemory();

        return collect;
    }

}
