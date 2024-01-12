package org.ktfoms.med.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.ktfoms.med.service.DepartmentService;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private FapService fapService;
    @Autowired
    private LpuService lpuService;
    // Загрузка "справочника  структурных подразделений" с сайта минздрава - грузим только ФАП
    @PostMapping("/upload_departments_xml")
    public String uploadSpDepartmentXml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = departmentService.parseSpDepartment(file);
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

    //Список подразлелений являющихся ФАП по ЛПУ (вложенная) для справочника ФАП
    @RequestMapping(value = { "/fap_dep_by_lpu/{lpu}" }, method = RequestMethod.GET)
    public String fapByLpu(Model model, @PathVariable("lpu") String lpu) {
        model.addAttribute("lpu", lpuService.getLpuByOid(lpu));
        model.addAttribute("fapDepList", departmentService.getFapDepartmentListByLpu(lpu));
        model.addAttribute("fapLicensesList", fapService.getFapLicensiesByLpu(lpu));
        return "fap_dep_by_lpu";
    }
}
