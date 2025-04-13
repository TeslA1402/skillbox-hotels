package org.example.skillboxhotels.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.skillboxhotels.service.StatService;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
@Validated
public class StatController {
    private final StatService statService;

    @GetMapping(value = "/export", produces = "text/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"stats.csv\"");
        statService.exportStatsToCsv(response.getOutputStream());
    }
}
