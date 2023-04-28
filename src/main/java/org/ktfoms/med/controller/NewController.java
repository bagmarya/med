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
// "Этот класс написан исключительно для формирования xml  с помощью шаблонов
// но при кодировании cp1251 выходят подстановочные последовательности вместо букв.
// можно будет возобновить работу класса, если это будет приемлемо
@RestController
@RequestMapping("/sp")
public class NewController {

    @Autowired
    SpringTemplateEngine springTemplateEngine;
    @Autowired
    private FapService fapService;


    @GetMapping(value ="finfap",produces = {MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String finfap(){
        Context context = new Context();

        List<FapFinDto> fapfins = fapService.getFapFinDtoList(2023).subList(1,5);
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

        Spfinfap spr = new Spfinfap("1.0", LocalDate.now().toString(), fapService.getFapFinDtoList(2023));
        return spr;
    }
}
