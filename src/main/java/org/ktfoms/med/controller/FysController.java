package org.ktfoms.med.controller;

import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.FysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Calendar;

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

    @RequestMapping(value = { "/parse/fys" }, method = RequestMethod.GET)
    public String parseFys(Model model) {
        String filename = "fys.xls";
        String message = fysService.parseFysXls(filename, true);
        model.addAttribute("message", message);
        return "message";
    }

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

//    @PostMapping("/files/upload")
//    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
//    try {
//            InputStream in = new ByteArrayInputStream(file.getBytes());
//            System.out.println("!!!!!!!!!!!!!");
//            System.out.println(in);
//            System.out.println("!!!!!!!!!!!!!");
//            return "File uploaded.";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "upload_form";
//    }

    @GetMapping("/upload_fys_xls")
    public String newFile(Model model) {
        return "upload_form";
    }

    @PostMapping({"/upload_fys_xls", "upload_fys_xls_empty_price"})
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            System.out.println("!!!!!!!!!!!!!");
            System.out.println(in);
            System.out.println("!!!!!!!!!!!!!");

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "upload_form";
    }

}
