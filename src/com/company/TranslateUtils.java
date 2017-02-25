package com.company;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TranslateUtils {

    private final static int maxParallelRequests = 100;
    private final static String translateFromLanguage = "en";
    private final static String translateToLanguage = "fr";

    public static String translate(String text, String fromLanguage, String toLanguage) throws InterruptedException {
//        Assert.isTrue(StringUtils.hasText(text));
//        Assert.isTrue(StringUtils.hasText(fromLanguage));
//        Assert.isTrue(StringUtils.hasText(toLanguage));
//        String requestUrl = UriComponentsBuilder.fromHttpUrl(translateUrl)
//                .queryParam("client", "gtx")
//                .queryParam("sl", fromLanguage)
//                .queryParam("tl", toLanguage)
//                .queryParam("dt", "t")
//                .queryParam("q", toLanguage).toUriString();
//        String forObject = restTemplate.getForObject(requestUrl, String.class, new Object[]{});
//        return forObject;

//        emulation of http request
        Thread.sleep(2);
//        http code returned from translate api
        int httpCode = 200;
        if (httpCode != 200){
            return null;
        }

//        translated data (if status 200)
        return text;
    }

    public static Map<Boolean,List<StatisticDto>> translateData(List<StatisticDto> data) {
        List<StatisticDto> notTranslated = Collections.synchronizedList(new ArrayList<StatisticDto>());
        int size = data.size();
        int translateParts = (int) (size / maxParallelRequests)+1;
        List<List<StatisticDto>> splitData = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(maxParallelRequests);
        for (int i = 0; i < translateParts; i++) {
            int startIndex = i * maxParallelRequests;
            int endIndex = startIndex + maxParallelRequests;
            if (endIndex > size) endIndex = size;
            List<StatisticDto> statisticDtos = data.subList(startIndex, endIndex);
            splitData.add(statisticDtos);
            for (StatisticDto statisticDto : statisticDtos) {
                executorService.execute(new TranslateTask(statisticDto,notTranslated));
            }
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<Boolean,List<StatisticDto>> result = new HashMap<>();
        result.put(true,splitData.stream().flatMap(Collection::stream).collect(Collectors.toList()));
        result.put(false,notTranslated);

        return result;
    }

    private static class TranslateTask implements Runnable {

        private StatisticDto translateData;

        private List<StatisticDto> errors;

        public TranslateTask() {

        }

        public TranslateTask(StatisticDto translateData, List<StatisticDto> errors) {
            this.translateData = translateData;
            this.errors = errors;
        }

        @Override
        public void run() {
            try {
                String translate = translate(translateData.getText(), translateFromLanguage, translateToLanguage);
                if (translate == null) {
                    errors.add(translateData);
                }else {
                    translateData.setTextTranslated(translate);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
