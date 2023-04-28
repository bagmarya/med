package org.ktfoms.med.controller;

import org.ktfoms.med.dto.Test;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.util.Calendar;

@Controller
public class MainController {

    @Autowired
    private LpuService lpuService;
    @Autowired
    private FapService fapService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        System.out.println("!!!!!!!!!!!!!!!сервер отвечает!!!!!!!!!!!!!!!!");
        model.addAttribute("today", Calendar.getInstance());
        return "index";
    }


//Вернет краказябры, даже если найдет шаблон в виндовой кодировке
//    @RequestMapping(value = { "/sp" }, method = RequestMethod.GET, produces = "application/xml;charset=windows-1251")
//    public String sp() throws IOException {
//        fapService.getFileSpfinfap();
//        return "sp_fin_fap.xml";
//    }

}
