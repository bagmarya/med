package org.ktfoms.med.controller;

import org.ktfoms.med.dto.Spfinfap;
import org.ktfoms.med.service.FapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequestMapping(value="/sp")
public class NewController {
    @Autowired
    private FapService fapService;
    @RequestMapping(value="/ff" , method = RequestMethod.GET, produces=MediaType.APPLICATION_XML_VALUE, headers = { "Content-Type = application/xml" })
//            , method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Spfinfap returnsp() {

        Spfinfap spr = new Spfinfap("1.0", LocalDate.now().toString(), fapService.getFapFinDtoList());
        return spr;
    }
}
