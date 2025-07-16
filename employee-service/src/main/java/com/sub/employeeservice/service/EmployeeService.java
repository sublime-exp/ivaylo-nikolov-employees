package com.sub.employeeservice.service;

import com.sub.employeeservice.api.ProjectResponse;
import com.sub.employeeservice.domain.EmployeeProject;
import com.sub.employeeservice.utils.CsvProjectParser;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    public Mono<List<ProjectResponse>> findBestPair(FilePart filePart) {
        return DataBufferUtils
                .join(filePart.content())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    InputStream inputStream = new ByteArrayInputStream(bytes);
                    List<EmployeeProject> records = CsvProjectParser.parseCsv(inputStream);

                    return computeBestPair(records);
                });
    }

    public List<ProjectResponse> computeBestPair(List<EmployeeProject> records) {
        Map<Integer, Map<Integer, Map.Entry<Date, Date>>> schedule = new HashMap<>();
        records.forEach(r -> {
            schedule.putIfAbsent(r.getProjectId(), new HashMap<>());
            schedule.get(r.getProjectId()).putIfAbsent(r.getEmpId(), Map.entry(r.getDateFrom(), r.getDateTo()));
        });

        int[] bestPair = {-1, -1, -1, -1};

        for (Map.Entry<Integer, Map<Integer, Map.Entry<Date, Date>>> kvp : schedule.entrySet()) {
            if (kvp.getValue().size() <= 1) {
                continue;
            }
            int[] bestPairCurrent = {-1, -1, -1, -1};

            for (Integer emp1Id : kvp.getValue().keySet()) {
                for (Integer emp2Id : kvp.getValue().keySet()) {
                    if (emp1Id.equals(emp2Id)) {
                        continue;
                    }

                    long overlappingDays = EmployeeProject.calculateOverlapDays(
                            kvp.getValue().get(emp1Id).getKey(),
                            kvp.getValue().get(emp1Id).getValue(),
                            kvp.getValue().get(emp2Id).getKey(),
                            kvp.getValue().get(emp2Id).getValue()
                    );

                    if (overlappingDays > bestPairCurrent[3]) {
                        bestPairCurrent[0] = emp1Id;
                        bestPairCurrent[1] = emp2Id;
                        bestPairCurrent[2] = kvp.getKey();
                        bestPairCurrent[3] = (int) overlappingDays;
                    }
                }
            }

            if (bestPair[0] == -1 && bestPairCurrent[0] != -1
                    || (bestPairCurrent[0] != -1 && bestPairCurrent[3] > bestPair[3])) {
                bestPair[0] = bestPairCurrent[0];
                bestPair[1] = bestPairCurrent[1];
                bestPair[2] = bestPairCurrent[2];
                bestPair[3] = bestPairCurrent[3];
            }
        }

        return schedule
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().containsKey(bestPair[0]) && entry.getValue().containsKey(bestPair[1]))
                .map(entry -> {
                    Map.Entry<Date, Date> firstPeriod = entry.getValue().get(bestPair[0]);
                    Map.Entry<Date, Date> secondPeriod = entry.getValue().get(bestPair[1]);

                    long overlappingDays = EmployeeProject.calculateOverlapDays(
                            firstPeriod.getKey(),
                            firstPeriod.getValue(),
                            secondPeriod.getKey(),
                            secondPeriod.getValue());

                    return new int[]{bestPair[0], bestPair[1], entry.getKey(), (int) overlappingDays};
                })
                .sorted((a, b) -> Integer.compare(b[3], a[3]))
                .map(arr -> new ProjectResponse(arr[0], arr[1], arr[2], arr[3]))
                .toList();
    }
}
