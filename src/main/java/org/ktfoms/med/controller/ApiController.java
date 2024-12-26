package org.ktfoms.med.controller;


import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.Spfinfap;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.FysService;
import org.ktfoms.med.service.LicenseService;
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
//import org.springframework.web.servlet.function.ServerRequest;

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

    @Autowired
    private LicenseService licenseService;
    @Autowired
    private FysService fysService;

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

    @RequestMapping(value = { "/pd_tarif/{year}" }, method = RequestMethod.GET, produces = "application/xml;charset=UTF-8")
    public ResponseEntity<String> getSpPdTarifForYear(@PathVariable("year") int year) throws IOException {
        HttpHeaders h = new HttpHeaders();
        h.set("Content-Disposition", "attachment; filename=\"PD_TARIF.xml\"");
        return new ResponseEntity<>(lpuService.getFileSpFundingNormaSmp(year),h, HttpStatus.OK);
    }

    @RequestMapping(value = { "/pd_tarif" }, method = RequestMethod.GET, produces = "application/xml;charset=UTF-8")
    public ResponseEntity<String> getSpPdTarif() throws IOException {
        HttpHeaders h = new HttpHeaders();
        h.set("Content-Disposition", "attachment; filename=\"PD_TARIF_" + LocalDate.now().format(DateTimeFormatter.ofPattern("uuuuMMdd")) + ".xml\"");
        return new ResponseEntity<>(lpuService.getFileSpFundingNormaSmp(),h, HttpStatus.OK);
    }

    //Шлет на фронт справочник подушевого финансирования ФАП в xml (cp1251)
    @RequestMapping(value = { "/sp_fin_fap/{year}" }, method = RequestMethod.GET, produces = "application/xml;charset=windows-1251")
    public ResponseEntity<String> sp(@PathVariable("year") int year) throws IOException {
        HttpHeaders h = new HttpHeaders();
        h.set("Content-Disposition", "attachment; filename=\"sp_fin_fap.xml\"");
        return new ResponseEntity<>(fapService.getFileSpfinfap(year),h, HttpStatus.OK);
    }

//метод заполнит указанный месяц в справочнике СФОФ по данным предыдущего, дублируется в главном контроллере /fill_next_month/{year}/{month}
    @PostMapping(value = "/fill_next_month/{year}/{month}")
    public ResponseEntity fillNextMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        try {
            fapService.fillNextMonth(month, year);
            return ResponseEntity.ok("Отработало");
        } catch (RuntimeException | NoSuchFieldException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @RequestMapping(value = { "/parse" }, method = RequestMethod.GET)
    public ResponseEntity<String> parseXml() throws IOException {
//        fapService.parseSpfinfap();
        lpuService.parseSpFundingNormaSmp();
        return new ResponseEntity<>("import completed", HttpStatus.OK);
    }

    @RequestMapping(value = { "/licences" }, method = RequestMethod.GET, produces = "application/xml;charset=windows-1251")
    public ResponseEntity<String> getLicences() {
        HttpHeaders h = new HttpHeaders();
        h.set("Content-Disposition", "attachment; filename=\"Licences.xml\"");
        return new ResponseEntity<>(licenseService.getFileLicences(),h, HttpStatus.OK);
    }

    @RequestMapping(value = { "/fys_xml" }, method = RequestMethod.GET, produces = "application/xml;charset=utf-8")
    public ResponseEntity<String> getFysXml() {
        HttpHeaders h = new HttpHeaders();
        h.set("Content-Disposition", "attachment; filename=\"fys.xml\"");
        return new ResponseEntity<>(fysService.getFysXML(),h, HttpStatus.OK);
    }

    @RequestMapping(value = { "/publish_licences" }, method = RequestMethod.POST)
    public ResponseEntity<String> publishLicences() throws IOException {
        return new ResponseEntity<>(licenseService.publishLicences(), HttpStatus.OK);
    }
}
