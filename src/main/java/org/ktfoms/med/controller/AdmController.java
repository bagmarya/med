package org.ktfoms.med.controller;


import org.hibernate.NonUniqueObjectException;
import org.hibernate.exception.ConstraintViolationException;
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

    // Загрузка Справочника лицензий с сайта
    @PostMapping("/upload_sp_license_xml")
    public String uploadSpLicenseXml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = licenseService.parseSpLicense(file);
            model.addAttribute("message", message);
        } catch (ConstraintViolationException e) {
            message = "Ограничения целостности не позволяют загрузить справочник, см. подробности в логах";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }

    // Загрузка ЛПУ из справочника F003 (добавит записи отсутствующие в базе) - не используется - справочник устарел
    @PostMapping("/add_lpu_F003_xml")
    public String addLpuF003Xml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = lpuService.parseF003ForAddLpu(file);
            model.addAttribute("message", message);
        } catch (ConstraintViolationException e) {
            message = "Ограничения целостности не позволяют загрузить данные справочника, см. подробности в логах";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }
    // Загрузка ЛПУ из справочника F032 (добавит записи отсутствующие в базе)
    @PostMapping("/add_lpu_F032_xml")
    public String addLpuF032Xml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = lpuService.parseF032ForAddLpu(file);
            model.addAttribute("message", message);
        } catch (ConstraintViolationException e) {
            message = "Ограничения целостности не позволяют загрузить данные справочника, см. подробности в логах";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }

    //ОБНОВЛЕНИЕ информации о ЛПУ из справочника F032 (обновит записи существующие в базе)
    @PostMapping("/upd_lpu_F032_xml")
    public String updateLpuF032Xml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = lpuService.parseF032ForUpdLpu(file);
            model.addAttribute("message", message);
        } catch (ConstraintViolationException e) {
            message = "Ограничения целостности не позволяют обновить данные справочника, см. подробности в логах";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }

    //ОБНОВЛЕНИЕ информации о ЛПУ из справочника F003 - не используется - справочник устарел
    //    (обновит записи существующие в базе: контактные данные и дату открытия и закрытия записи по справочнику)
    @PostMapping("/upd_lpu_F003_xml")
    public String updateLpuF003Xml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = lpuService.parseF003ForUpdLpu(file);
            model.addAttribute("message", message);
        } catch (ConstraintViolationException e) {
            message = "Ограничения целостности не позволяют обновить данные справочника, см. подробности в логах";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }

//    Принимает на вход файл реестра МО с сайта http://192.168.12.200/downloads/doc/forall/2023/02/MO-2023.xlsx
//    и обновляет ИНН, КПП, ОГРН, Названия организации, используя код МО в качестве ключа
    @PostMapping("/upd_lpu_xlsx")
    public String updateLpuXlsx(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = lpuService.parseXlsxForUpdLpu(file);
            model.addAttribute("message", message);
        } catch (ConstraintViolationException e) {
            message = "Ограничения целостности не позволяют обновить данные справочника, см. подробности в логах";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }
}
