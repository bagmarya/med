package org.ktfoms.med.controller;

import org.ktfoms.med.form.LicensePolForm;
import org.ktfoms.med.form.LicenseStacForm;
import org.ktfoms.med.service.LicenseService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private LpuService lpuService;

    //########### Блок для управления лицензиями СТАЦИОНАРА ###########

    //Просмотр списка лицензий стационара для ЛПУ
    @RequestMapping(value = { "/stac/{mcod}" }, method = RequestMethod.GET)
    public String ShowLicenseStac(Model model, @PathVariable("mcod") Integer mcod, @Param("message") String message) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        model.addAttribute("licenseStacList", licenseService.getLicenseStacInfosByMcod(mcod));
        model.addAttribute("message", message);
        return "license_stac";
    }

    //Вывод формы для заполнения новой лицензии стационара
    @RequestMapping(value = { "/stac/add_license_stac/{mcod}" }, method = RequestMethod.GET)
    public String addLicenseStac(Model model, @PathVariable("mcod") Integer mcod) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        LicenseStacForm form = licenseService.getNewLicenseStacForm();
        form.setMcod(mcod);
        model.addAttribute("newLicenseStacForm", form);
        return "add_license_stac";
    }

    //Запись новой лицензии стационара
    @RequestMapping(value = { "/stac/add_license_stac/{mcod}" }, method = RequestMethod.POST)
    public String saveLicenseStac(Model model, LicenseStacForm licenseStacForm, @PathVariable("mcod") Integer mcod) {
        String message = "";
        try {
            message = licenseService.saveNewLicenseStac(licenseStacForm);
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удалось сохранить лицензию";
            model.addAttribute("message", message);}
        return "redirect:/license/stac/" + licenseStacForm.getMcod().toString() + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    //Вывод формы для редактирования лицензии стационара
    @RequestMapping(value = { "/stac/edit_license_stac/{id}" }, method = RequestMethod.GET)
    public String editLicenseStac(Model model, @PathVariable("id") Integer id) {
        LicenseStacForm form = licenseService.getEditLicenseStacForm(id);
        model.addAttribute("editLicenseStacForm", form);
        model.addAttribute("id", id);
        model.addAttribute("lpu", lpuService.getLpuByMcod(form.getMcod()));
        return "edit_license_stac";
    }

    //Запись изменений лицензии стационара
    @RequestMapping(value = { "/stac/edit_license_stac/{id}" }, method = RequestMethod.POST)
    public String saveEditLicenseStac(Model model, LicenseStacForm licenseStacForm, @PathVariable("id") Integer id) {
        String message = "";
        try {
            message = licenseService.saveEditLicenseStac(id, licenseStacForm);
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удалось сохранить зменения";
            model.addAttribute("message", message);}
        return "redirect:/license/stac/" + licenseStacForm.getMcod().toString() + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    // Удаление лицензии стационара
    @RequestMapping(value = { "/stac/delete_license_stac/{id}" }, method = RequestMethod.POST)
    public String saveEditLicenseStac(Model model, @PathVariable("id") Integer id) {
        String message = "";
        String mcod = licenseService.getEditLicenseStacForm(id).getMcod().toString();
        try {
            message = licenseService.deleteLicenseStac(id);
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удалось далить";
            model.addAttribute("message", message);}
        return "redirect:/license/stac/" + mcod + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    //########### Блок для управления лицензиями ПОЛИКЛИНИКИ ###########

    //Просмотр списка лицензий поликлиники для ЛПУ
    @RequestMapping(value = { "/pol/{mcod}" }, method = RequestMethod.GET)
    public String ShowLicensePol(Model model, @PathVariable("mcod") Integer mcod, @Param("message") String message) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        model.addAttribute("licensePolList", licenseService.getLicensePolInfosByMcod(mcod));
        model.addAttribute("message", message);
        return "license_pol";
    }

    //Вывод формы для заполнения новой лицензии поликлиники
    @RequestMapping(value = { "/pol/add_license_pol/{mcod}" }, method = RequestMethod.GET)
    public String addLicensePol(Model model, @PathVariable("mcod") Integer mcod) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        LicensePolForm form = licenseService.getNewLicensePolForm();
        form.setMcod(mcod);
        model.addAttribute("newLicensePolForm", form);
        return "add_license_pol";
    }

    //Запись новой лицензии поликлиники
    @RequestMapping(value = { "/pol/add_license_pol/{mcod}" }, method = RequestMethod.POST)
    public String saveLicensePol(Model model, LicensePolForm licensePolForm, @PathVariable("mcod") Integer mcod) {
        String message = "";
        try {
            message = licenseService.saveNewLicensePol(licensePolForm);
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удалось сохранить лицензию";
            model.addAttribute("message", message);}
        return "redirect:/license/pol/" + licensePolForm.getMcod().toString() + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    //Вывод формы для редактирования лицензии поликлиники
    @RequestMapping(value = { "/pol/edit_license_pol/{id}" }, method = RequestMethod.GET)
    public String editLicensePol(Model model, @PathVariable("id") Integer id) {
        LicensePolForm form = licenseService.getEditLicensePolForm(id);
        model.addAttribute("editLicensePolForm", form);
        model.addAttribute("id", id);
        model.addAttribute("lpu", lpuService.getLpuByMcod(form.getMcod()));
        return "edit_license_pol";
    }

    //Запись изменений лицензии поликлиники
    @RequestMapping(value = { "/pol/edit_license_pol/{id}" }, method = RequestMethod.POST)
    public String saveEditLicensePol(Model model, LicensePolForm licensePolForm, @PathVariable("id") Integer id) {
        String message = "";
        try {
            message = licenseService.saveEditLicensePol(id, licensePolForm);
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удалось сохранить зменения";
            model.addAttribute("message", message);}
        return "redirect:/license/pol/" + licensePolForm.getMcod().toString() + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    // Удаление лицензии поликлиники
    @RequestMapping(value = { "/pol/delete_license_pol/{id}" }, method = RequestMethod.POST)
    public String saveEditLicensePol(Model model, @PathVariable("id") Integer id) {
        String message = "";
        String mcod = licenseService.getEditLicensePolForm(id).getMcod().toString();
        try {
            message = licenseService.deleteLicensePol(id);
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удалось далить";
            model.addAttribute("message", message);}
        return "redirect:/license/pol/" + mcod + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }
}
