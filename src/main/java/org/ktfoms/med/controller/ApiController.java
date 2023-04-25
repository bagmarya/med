package org.ktfoms.med.controller;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.Spfinfap;
import org.ktfoms.med.dto.Test;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.DataOutput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    private LpuService lpuService;
    @Autowired
    private FapService fapService;

    @GetMapping("/lpu/{id}")
    public Lpu  getLpuById(@PathVariable("id") int id) {
        return lpuService.getLpuById(id);
    }

    @GetMapping("/fap/{id}")
    public Fap getFapById(@PathVariable("id") int id) {
        return fapService.getFapById(id);
    }

    @GetMapping("/fapd/{id}")
    public FapDto getFapDById(@PathVariable("id") int id) {
        return fapService.getFapDtoById(id);
    }

    @GetMapping("/fapf/{id}")
    public FapFin getFapFById(@PathVariable("id") int id) {
        return fapService.getFapFinById(id);
    }

    @GetMapping(value = "/sp_fin_fap", produces = "application/xml")
    public Spfinfap getFapFById() throws IOException {
        LocalDate d = LocalDate.now();
        Spfinfap spr = new Spfinfap("1.0", LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")), fapService.getFapFinDtoList().subList(1,5));

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
//        File file = new File("D:\\new.xml");
//        xmlMapper.writeValue(file, spr);


        String fileName = "D:\\new.xml";
        String encoding = "windows-1251";

        Writer output = new OutputStreamWriter(new FileOutputStream(fileName), encoding);

        output.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?>\n");

//        output.write("АБВГДфбвгдё");
//        String s = xmlMapper.valueToTree(spr).toString();
        xmlMapper.writeValue(output, spr);
//        output.write(s);
//        output.flush();
//        output.close();

        return spr;
    }

}
