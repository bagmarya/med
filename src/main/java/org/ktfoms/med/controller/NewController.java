package org.ktfoms.med.controller;

import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.dto.Spfinfap;
import org.ktfoms.med.service.FapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sp")
public class NewController {

    @Autowired
    SpringTemplateEngine springTemplateEngine;
    @Autowired
    private FapService fapService;

    @GetMapping(value ="test",produces = {MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String test(){
        Map<String, String> pinfo = new HashMap<>();
        Context context = new Context();
        context.setVariable("pinfo", pinfo);
        pinfo.put("lastname", "Jordan");
        pinfo.put("firstname", "Michael");
        pinfo.put("country", "USA");

        String content = springTemplateEngine.process("person-details",context);
        return content;

    }

    @GetMapping(value ="finfap",produces = {MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String finfap(){
        Context context = new Context();

        List<FapFinDto> fapfins = fapService.getFapFinDtoList().subList(1,5);
        context.setVariable("fapfins", fapfins);

        Map<String, String> pinfo = new HashMap<>();
        context.setVariable("pinfo", pinfo);
        pinfo.put("lastname", "Jordan");
        pinfo.put("firstname", "Michael");
        pinfo.put("country", "USA");

        String content = springTemplateEngine.process("sp_fin_fap",context);
        return content;

    }



    @RequestMapping(value="/ff" , method = RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE)
//            , method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_XML_VALUE), headers = { "Content-Type = application/xml" }
    @ResponseBody
    public Spfinfap returnsp() {

        Spfinfap spr = new Spfinfap("1.0", LocalDate.now().toString(), fapService.getFapFinDtoList());
        return spr;
    }
}
