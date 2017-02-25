package com.company;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class IOUtils {

    public static List<String[]> readCSVFile(Path path) throws IOException, URISyntaxException {
        List<String[]> result = new ArrayList<>();
        System.out.println("Start reading "+path.getFileName());
        try {
            Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
            result = lines.skip(1).map(IOUtils::parseCSVLine).distinct().collect(Collectors.toList());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Main.getFreeMemory();
        System.out.println("Successfully reading "+path.getFileName());
        return result;
    }

    private static String[] parseCSVLine(String line) {
        Pattern p = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
        String[] fields = p.split(line);
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].replace("\"", "");
        }
        return fields;
    }

}
