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
            "dd-MM-yyyy",
            "yyyy/MM/dd",
            "MM.dd.yyyy",
            "dd.MM.yyyy",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd HH:mm:ss Z",
            "yyyy-MM-dd HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss",
            "yyyyMMdd",
            "dd MMM yyyy",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    );


    public static Set<EmployeeProject> parseCsv(InputStream stream) {
        Set<EmployeeProject> records = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream)))) {
            reader.readLine(); // skip header line
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length < 4) continue; // skip invalid lines

                int empId = Integer.parseInt(tokens[0].trim());
                int projectId = Integer.parseInt(tokens[1].trim());
                Date dateFrom = parseDate(tokens[2].trim());
                Date dateTo = tokens[3].trim().equalsIgnoreCase("NULL") ? new Date() : parseDate(tokens[3].trim());

                records.add(new EmployeeProject(empId, projectId, dateFrom, dateTo));
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }
        return records;
    }


    private static Date parseDate(String dateStr) {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                sdf.setLenient(false);
                return sdf.parse(dateStr);
            } catch (ParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + dateStr);
    }
}