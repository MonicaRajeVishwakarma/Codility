package com.HelloFresh.interview.statistics.controller;


import com.HelloFresh.interview.statistics.model.StatisticsSummary;
import com.HelloFresh.interview.statistics.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stats")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {

        this.statisticsService = statisticsService;

    }

    @GetMapping()
    public String get() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(10);
        df.setGroupingUsed(false);
        String stats = statisticsService.get();
        return stats;
    }
}
