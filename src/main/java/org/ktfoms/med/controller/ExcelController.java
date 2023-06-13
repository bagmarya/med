package org.ktfoms.med.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ktfoms.med.service.ExcelService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//@CrossOrigin("http://localhost:8081")
@Controller
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @GetMapping("/sp_fin_fap/{year}")
    public ResponseEntity<Resource> getFinFapExcel(@PathVariable("year") Integer year) {
        String filename = "fap.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.loadFinFapExcel(year));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }

    @GetMapping("/fys/get_fys_excel")
    public ResponseEntity<Resource> getFysExcel() {
        String filename = "FYS.xls";
        InputStreamResource file = new InputStreamResource(excelService.loadFysExcel());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }

    @GetMapping("/get_norm_pd_excel/{year}/{month}")
    public ResponseEntity<Resource> getNormPdExcel(@PathVariable("year") int year, @PathVariable("month") int month) {
        LocalDate date = LocalDate.of(year, month, 01);
        String filename = "norm_pd "+date.format(DateTimeFormatter.ofPattern("dd.MM.uu"))+".xls";
        InputStreamResource file = new InputStreamResource(excelService.loadNormPdExcel(date));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }

}