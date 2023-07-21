package org.ktfoms.med.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.ktfoms.med.form.LicenseStacForm;
import org.ktfoms.med.service.LicenseService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private LpuService lpuService;

    @RequestMapping(value = { "/stac/{mcod}" }, method = RequestMethod.GET)
    public String ShowLicenseStac(Model model, @PathVariable("mcod") Integer mcod, @Param("message") String message) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        model.addAttribute("licenseStacList", licenseService.getLicenseStacInfosByMcod(mcod));
        model.addAttribute("message", message);
        return "license_stac";
    }

    @RequestMapping(value = { "/stac/add_license_stac/{mcod}" }, method = RequestMethod.GET)
    public String addLicenseStac(Model model, @PathVariable("mcod") Integer mcod) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        LicenseStacForm form = licenseService.getNewLicenseStacForm();
        form.setMcod(mcod);
        model.addAttribute("newLicenseStacForm", form);
        return "add_license_stac";
    }

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

}
