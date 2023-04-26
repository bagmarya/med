package org.ktfoms.med.service;

import org.ktfoms.med.dao.FapDao;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FapService {
    private final FapDao fapDao;

    public FapService(FapDao fapDao){
        this.fapDao = fapDao;
    }

    public Fap getFapById(Integer id){
        return fapDao.getById(id);
    }

    public FapDto getFapDtoById(int id){
        return fapDao.getFapDtoById(id);
    }

    public FapFin getFapFinById(Integer id){
        return fapDao.getFapFinById(id);
    }

    public List<FapFinDto> getFapFinDtoList(){
        return fapDao.getFapFinDtoList();
    }

    public void getFileSpfinfap() throws IOException {

        String fileName = "D:\\sp_fin_fap.xml";
        String encoding = "windows-1251";
        Writer output = new OutputStreamWriter(new FileOutputStream(fileName), encoding);
        output.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?>\n");
        output.write("<spfinfap>\n" +
                "<version>1.0</version>\n" +
                "<date>" + LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")) + "</date>\n");
        for (FapFinDto rec: getFapFinDtoList()) {
            output.write("<FAP>" +
                    "<MKOD>" + rec.getMkod() + "</MKOD>" +
                    "<NAME_PODR>" + rec.getNamePodr() + "</NAME_PODR>" +
                    "<LPU>" + rec.getMoLpu() + "</LPU>" +
                    "<PODR>" + rec.getPodr() + "</PODR>" +

                    "<G_FIN1>" + rec.getGFin1() + "</G_FIN1>" +
                    "<K_YKOMP1>" + rec.getKYkomp1() + "</K_YKOMP1>" +
                    "<SUMM_ASTRA1>" + rec.getSummAstra1() + "</SUMM_ASTRA1>" +
                    "<SUMM_KAPIT1>" + rec.getSummKapit1() + "</SUMM_KAPIT1>" +

                    "<G_FIN2>" + rec.getGFin2() + "</G_FIN2>" +
                    "<K_YKOMP2>" + rec.getKYkomp2() + "</K_YKOMP2>" +
                    "<SUMM_ASTRA2>" + rec.getSummAstra2() + "</SUMM_ASTRA2>" +
                    "<SUMM_KAPIT2>" + rec.getSummKapit2() + "</SUMM_KAPIT2>" +

                    "<G_FIN3>" + rec.getGFin3() + "</G_FIN3>" +
                    "<K_YKOMP3>" + rec.getKYkomp3() + "</K_YKOMP3>" +
                    "<SUMM_ASTRA3>" + rec.getSummAstra3() + "</SUMM_ASTRA3>" +
                    "<SUMM_KAPIT3>" + rec.getSummKapit3() + "</SUMM_KAPIT3>" +

                    "<G_FIN4>" + rec.getGFin4() + "</G_FIN4>" +
                    "<K_YKOMP4>" + rec.getKYkomp4() + "</K_YKOMP4>" +
                    "<SUMM_ASTRA4>" + rec.getSummAstra4() + "</SUMM_ASTRA4>" +
                    "<SUMM_KAPIT4>" + rec.getSummKapit4() + "</SUMM_KAPIT4>" +

                    "<G_FIN5>" + rec.getGFin5() + "</G_FIN5>" +
                    "<K_YKOMP5>" + rec.getKYkomp5() + "</K_YKOMP5>" +
                    "<SUMM_ASTRA5>" + rec.getSummAstra5() + "</SUMM_ASTRA5>" +
                    "<SUMM_KAPIT5>" + rec.getSummKapit5() + "</SUMM_KAPIT5>" +

                    "<G_FIN6>" + rec.getGFin6() + "</G_FIN6>" +
                    "<K_YKOMP6>" + rec.getKYkomp6() + "</K_YKOMP6>" +
                    "<SUMM_ASTRA6>" + rec.getSummAstra6() + "</SUMM_ASTRA6>" +
                    "<SUMM_KAPIT6>" + rec.getSummKapit6() + "</SUMM_KAPIT6>" +

                    "<G_FIN7>" + rec.getGFin7() + "</G_FIN7>" +
                    "<K_YKOMP7>" + rec.getKYkomp7() + "</K_YKOMP7>" +
                    "<SUMM_ASTRA7>" + rec.getSummAstra7() + "</SUMM_ASTRA7>" +
                    "<SUMM_KAPIT7>" + rec.getSummKapit7() + "</SUMM_KAPIT7>" +

                    "<G_FIN8>" + rec.getGFin8() + "</G_FIN8>" +
                    "<K_YKOMP8>" + rec.getKYkomp8() + "</K_YKOMP8>" +
                    "<SUMM_ASTRA8>" + rec.getSummAstra8() + "</SUMM_ASTRA8>" +
                    "<SUMM_KAPIT8>" + rec.getSummKapit8() + "</SUMM_KAPIT8>" +

                    "<G_FIN9>" + rec.getGFin9() + "</G_FIN9>" +
                    "<K_YKOMP9>" + rec.getKYkomp9() + "</K_YKOMP9>" +
                    "<SUMM_ASTRA9>" + rec.getSummAstra9() + "</SUMM_ASTRA9>" +
                    "<SUMM_KAPIT9>" + rec.getSummKapit9() + "</SUMM_KAPIT9>" +


                    "<G_FIN10>" + rec.getGFin10() + "</G_FIN10>" +
                    "<K_YKOMP10>" + rec.getKYkomp10() + "</K_YKOMP10>" +
                    "<SUMM_ASTRA10>" + rec.getSummAstra10() + "</SUMM_ASTRA10>" +
                    "<SUMM_KAPIT10>" + rec.getSummKapit10() + "</SUMM_KAPIT10>" +

                    "<G_FIN11>" + rec.getGFin11() + "</G_FIN11>" +
                    "<K_YKOMP11>" + rec.getKYkomp11() + "</K_YKOMP11>" +
                    "<SUMM_ASTRA11>" + rec.getSummAstra11() + "</SUMM_ASTRA11>" +
                    "<SUMM_KAPIT11>" + rec.getSummKapit11() + "</SUMM_KAPIT11>" +

                    "<G_FIN12>" + rec.getGFin12() + "</G_FIN12>" +
                    "<K_YKOMP12>" + rec.getKYkomp12() + "</K_YKOMP12>" +
                    "<SUMM_ASTRA12>" + rec.getSummAstra12() + "</SUMM_ASTRA12>" +
                    "<SUMM_KAPIT12>" + rec.getSummKapit12() + "</SUMM_KAPIT12>" +
                    "</FAP>\n");
        }
        output.write("</spfinfap>");
        output.flush();
        output.close();


    }
}
