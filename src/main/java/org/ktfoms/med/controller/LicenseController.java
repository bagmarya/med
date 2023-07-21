package org.ktfoms.med.controller;

import org.ktfoms.med.service.LicenseService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/license")
public class LicenseController {
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private LpuService lpuService;
    @RequestMapping(value = { "/stac/{mcod}" }, method = RequestMethod.GET)
    public String fysCalculate(Model model, @PathVariable("mcod") Integer mcod) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        model.addAttribute("licenseStacList", licenseService.getLicenseStacInfosByMcod(mcod));
        return "license_stac";
    }

}
