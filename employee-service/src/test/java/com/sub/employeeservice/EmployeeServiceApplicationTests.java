package com.sub.employeeservice;

import com.sub.employeeservice.api.ProjectResponse;
import com.sub.employeeservice.domain.EmployeeProject;
import com.sub.employeeservice.service.EmployeeService;
import com.sub.employeeservice.utils.CsvProjectParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;

@SpringBootTest
class EmployeeServiceApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void test() {
        InputStream inputStream = EmployeeServiceApplicationTests.class.getClassLoader()
                .getResourceAsStream("data.csv");
        List<EmployeeProject> records  = CsvProjectParser.parseCsv(inputStream);
        List<ProjectResponse> bestPair = employeeService.computeBestPair(records);
        bestPair.forEach(System.out::println);
    }

}
