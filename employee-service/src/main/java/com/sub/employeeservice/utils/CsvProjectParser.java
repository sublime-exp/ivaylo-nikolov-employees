package com.sub.employeeservice.utils;

import com.sub.employeeservice.domain.EmployeeProject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CsvProjectParser {

    private static final List<String> DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd",
            "MM/dd/yyyy",
            "dd-MM-yyyy"
    );

    public static List<EmployeeProject> parseCsv(InputStream inputStream) {
        List<EmployeeProject> records = new ArrayList<>();

        try (
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                int empId = Integer.parseInt(tokens[0].trim());
                int projectId = Integer.parseInt(tokens[1].trim());
                Date dateFrom = parseDate(tokens[2].trim());
                Date dateTo = tokens[3].trim().equalsIgnoreCase("NULL") ? new Date() : parseDate(tokens[3].trim());
                records.add(new EmployeeProject(empId, projectId, dateFrom, dateTo));
            }
        }catch (Exception e) {
            records = new ArrayList<>();
            System.out.println("Error reading CSV: " + e.getMessage());
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