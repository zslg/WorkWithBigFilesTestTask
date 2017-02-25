package com.company;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {

        List<String> argsList = Arrays.asList(args);
        boolean withTranslateOption = argsList.contains("translate=true");
        Optional<String> first = argsList.stream().filter(s -> s.contains("path=")).findFirst();
        if (!first.isPresent()) {
            throw new RuntimeException("Please specify csv file path!");
        }

        System.out.println("Menu:");
        System.out.println("1)Get 1000 most active users.");
        System.out.println("2)Get 1000 most commented products.");
        System.out.println("3)Get 1000 most used words.");
        if (withTranslateOption) {
            System.out.println("4)Translate reviews.");
        }
        System.out.println("Choose menu item:");

//        Path file = Paths.get(ClassLoader.getSystemClassLoader().getResource("Reviews.csv").toURI());
        Path file = Paths.get(first.get().substring(5).trim());

        Scanner scanner = new Scanner(System.in);
        int nextInt = scanner.nextInt();

        switch (nextInt) {
            case 1:
                menu1(file);
                break;
            case 2:
                menu2(file);
                break;
            case 3:
                menu3(file);
                break;
            case 4:
                if (withTranslateOption) {
                    menu4(file);
                }
                break;
            default:
                System.out.println("You chose wrong menu item number!");
                break;
        }

    }

    private static void menu4(Path file) throws IOException, URISyntaxException {
        getFreeMemory();
        List<StatisticDto> data = ConverterUtils.convertStatistic(IOUtils.readCSVFile(file));
        Map<Boolean, List<StatisticDto>> result = TranslateUtils.translateData(data);
        System.out.println("Data translated!");
        List<StatisticDto> notTranslated = result.get(false);
        if (!notTranslated.isEmpty()) {
            System.out.println("Not translated:"+notTranslated);
        }
    }

    private static void menu3(Path file) throws IOException, URISyntaxException {
        Main.getFreeMemory();
        List<StatisticDto> data = ConverterUtils.convertStatistic(IOUtils.readCSVFile(file));
        List<String> mostUsedWords = StatisticUtils.get1000MostUsedWords(data);
        System.out.println("Result:");
        System.out.println(mostUsedWords);
    }

    private static void menu2(Path file) throws IOException, URISyntaxException {
        Main.getFreeMemory();
        List<StatisticDto> data = ConverterUtils.convertStatistic(IOUtils.readCSVFile(file));
        List<String> mostCommentedProducts = StatisticUtils.get1000MostCommentedProducts(data);
        System.out.println("Result:");
        System.out.println(mostCommentedProducts);
    }

    private static void menu1(Path file) throws IOException, URISyntaxException {
        Main.getFreeMemory();
        List<StatisticDto> data = ConverterUtils.convertStatistic(IOUtils.readCSVFile(file));
        List<String> mostActiveUsers = StatisticUtils.get1000MostActiveUsers(data);
        System.out.println("Result:");
        System.out.println(mostActiveUsers);
    }

    public static void getFreeMemory(){
        long freeMemory = Runtime.getRuntime().freeMemory()/1024/1024;
        System.out.println("Free memory:"+freeMemory+" MB.");
    }

}
