package com.sub.employeeservice.api;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CsvProjectParser {

    private static final List<String> DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd",
            "MM/dd/yyyy",
            "dd-MM-yyyy"
    );

    public static List<EmployeeProject> parseCsv(String resourcePath) throws IOException {
        List<EmployeeProject> records = new ArrayList<>();

        try (InputStream inputStream = CsvProjectParser.class.getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            // Skip header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                int empId = Integer.parseInt(tokens[0].trim());
                int projectId = Integer.parseInt(tokens[1].trim());
                Date dateFrom = parseDate(tokens[2].trim());
                Date dateTo = tokens[3].trim().equalsIgnoreCase("NULL") ? new Date() : parseDate(tokens[3].trim());
                records.add(new EmployeeProject(empId, projectId, dateFrom, dateTo));
            }
        }
        return records;
    }


    private static Date parseDate(String dateStr) {
        for (String format : DATE_FORMATS) {
            try {
                return new SimpleDateFormat(format).parse(dateStr);
            } catch (ParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + dateStr);
    }
}