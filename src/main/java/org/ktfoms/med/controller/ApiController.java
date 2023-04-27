package org.ktfoms.med.controller;


import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.Spfinfap;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    private LpuService lpuService;
    @Autowired
    private FapService fapService;

    @GetMapping("/lpu/{id}")
    public Lpu  getLpuById(@PathVariable("id") int id) {
        return lpuService.getLpuById(id);
    }

    @GetMapping("/fap/{id}")
    public Fap getFapById(@PathVariable("id") int id) {
        return fapService.getFapById(id);
    }

    @GetMapping("/fapd/{id}")
    public FapDto getFapDById(@PathVariable("id") int id) {
        return fapService.getFapDtoById(id);
    }

    @GetMapping("/fapf/{id}")
    public FapFin getFapFById(@PathVariable("id") int id) {
        return fapService.getFapFinById(id);
    }


    //TODO Шлет на фронт xml в utf-8; если будет не нужен, совсем убрать
    @GetMapping(value = "/sp_fin_fap_utf-8", produces = "application/xml")
    public Spfinfap getFapFById() throws IOException {
        LocalDate d = LocalDate.now();
        Spfinfap spr = new Spfinfap("1.0", LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")), fapService.getFapFinDtoList());
        fapService.getFileSpfinfap();
        return spr;
    }

    @PostMapping(value = "/fillNextMonth/{month}")
    public ResponseEntity fillNextMonth(@PathVariable("month") int month) {
        try {
            fapService.fillNextMonth(month);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).build();
        }
    }

//TODO Шлет на фронт xml в cp1251 и сохраняет файл такой же xml в D:\data\javaprojects\med\target\classes\templates,
// но нужно поменять чтобы запускалось скачивание файла, а сам файл никуда в ФС не сохранялся
    @RequestMapping(value = { "/sp_fin_fap" }, method = RequestMethod.GET, produces = "application/xml;charset=windows-1251")
    public ResponseEntity<String> sp() throws IOException {
        return new ResponseEntity<>(fapService.getFileSpfinfap(), HttpStatus.OK);
    }
}
