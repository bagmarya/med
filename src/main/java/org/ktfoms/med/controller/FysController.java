package org.ktfoms.med.controller;


import org.ktfoms.med.service.FysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Controller
@RequestMapping("/fys")
public class FysController {
    @Autowired
    private FysService fysService;

    //Страница (основная) для справочника медицинских услуг
    @RequestMapping( method = RequestMethod.GET)
    public String fundingFap(Model model) {

        return "fys";
    }

    //  в данный момент не работает, т.к. ориентировано на старый формат справочника
    @RequestMapping(value = { "/parse/fys" }, method = RequestMethod.GET)
    public String parseFys(Model model) {
        String filename = "fys.xls";
        String message = fysService.parseFysXls(filename, true);
        model.addAttribute("message", message);
        return "message";
    }

    //  в данный момент не работает, т.к. ориентировано на старый формат справочника
    @RequestMapping(value = { "/parse/fys_empty_price" }, method = RequestMethod.GET)
    public String parseFysEmptyPrice(Model model) {
        String filename = "fys.xls";
        String message = fysService.parseFysXls(filename, false);
        model.addAttribute("message", message);
        return "message";
    }

    @RequestMapping(value = { "/parse/price" }, method = RequestMethod.GET)
    public String parsePrice(Model model) {
        String filename = "sto_m.xls";
        String message = fysService.parsePriceXls(filename);
        model.addAttribute("message", message);
        return "message";
    }

    @RequestMapping(value = { "/fys_calc" }, method = RequestMethod.GET)
    public String fysCalculate(Model model) {
        String message = fysService.fysCalculate();
        model.addAttribute("message", message);
        return "message";
    }
    @RequestMapping(value = { "/get_fys_dbf" }, method = RequestMethod.GET)
    public ResponseEntity<Resource> getFysDbf() {
        String filename = "fys.dbf";
        InputStreamResource file = new InputStreamResource(fysService.createFysDbf());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.dbf")).body(file);
    }

    @PostMapping("/upload_fys_xls")
    public String uploadFys(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = fysService.parseFysXls(in, true);
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "fys";
    }

    @PostMapping("upload_fys_xls_empty_price")
    public String uploadFysEmptyPrice(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = fysService.parseFysXls(in, false);
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "fys";
    }

    @PostMapping("upload_price_xls")
    public String uploadPriceFys(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = fysService.parsePriceXls(in);
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "fys";
    }

}
