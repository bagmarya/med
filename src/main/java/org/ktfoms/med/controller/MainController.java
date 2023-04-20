package org.ktfoms.med.controller;

import org.ktfoms.med.dto.Test;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;

@Controller
public class MainController {

    @Autowired
    private LpuService lpuService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        Test res = new Test(4, "testname");
        System.out.println(res.getName() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + res.getId());
        model.addAttribute("today", Calendar.getInstance());
        return "index";
    }


}
