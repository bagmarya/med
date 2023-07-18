package org.ktfoms.med.controller;


import org.hibernate.NonUniqueObjectException;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.LicenseService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/adm")
public class AdmController {
    @Autowired
    private LpuService lpuService;
    @Autowired
    private FapService fapService;

    @Autowired
    private LicenseService licenseService;

    //Страница админки
    @RequestMapping( method = RequestMethod.GET)
    public String fundingFap(Model model) {
        return "adm";
    }

    @PostMapping("/upload_v002_xml")
    public String uploadV002Xml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = licenseService.parseSpV002(file);
            model.addAttribute("message", message);
        } catch (NonUniqueObjectException e) {
            message = "В справочнике присутствуют две или более незакрытых записи с одинаковым кодм профиля медицинской помощи";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }
}
