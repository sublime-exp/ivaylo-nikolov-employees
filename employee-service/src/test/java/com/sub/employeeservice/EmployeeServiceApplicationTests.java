package com.sub.employeeservice;

import com.sub.employeeservice.api.ProjectResponse;
import com.sub.employeeservice.domain.EmployeeProject;
import com.sub.employeeservice.service.EmployeeService;
import com.sub.employeeservice.utils.CsvProjectParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmployeeServiceApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void test1() {
        Set<EmployeeProject> records = CsvProjectParser
                .parseCsv(EmployeeServiceApplicationTests.class.getClassLoader()
                        .getResourceAsStream("test1.csv"));
        Set<ProjectResponse> commonProjects = employeeService.computeCommonProjects(records);
        Set<ProjectResponse> expected = Set.of(
                new ProjectResponse(102, 143, 11, 351),
                new ProjectResponse(102, 143, 12, 66)
        );
        assertEquals(expected, commonProjects);
    }

    @Test
    void test2() {
        Set<EmployeeProject> records = CsvProjectParser
                .parseCsv(EmployeeServiceApplicationTests.class.getClassLoader()
                        .getResourceAsStream("test2.csv"));
        Set<ProjectResponse> commonProjects = employeeService.computeCommonProjects(records);
        Set<ProjectResponse> expected = Set.of(
                new ProjectResponse(201, 202, 25, 276),
                new ProjectResponse(201, 202, 20, 214)
        );
        assertEquals(expected, commonProjects);
    }

    @Test
    void test3() {
        Set<EmployeeProject> records = CsvProjectParser
                .parseCsv(EmployeeServiceApplicationTests.class.getClassLoader()
                        .getResourceAsStream("test3.csv"));
        Set<ProjectResponse> commonProjects = employeeService.computeCommonProjects(records);
        Set<ProjectResponse> expected = Set.of(
                new ProjectResponse(218, 143, 11, 364),
                new ProjectResponse(218, 143, 14, 214),
                new ProjectResponse(218, 143, 12, 36),
                new ProjectResponse(218, 143, 10, 0)
        );
        assertEquals(expected, commonProjects);
    }

}
