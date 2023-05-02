package org.ktfoms.med.service;

import org.ktfoms.med.dao.FapDao;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.FundingNorma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FapService {
    private final FapDao fapDao;
    private final LpuDao lpuDao;
    @Autowired
    private ResourceLoader resourceLoader;

    public FapService(FapDao fapDao, LpuDao lpuDao){
        this.fapDao = fapDao;
        this.lpuDao = lpuDao;
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

    public List<FapFinDto> getFapFinDtoList(Integer year){
        return fapDao.getFapFinDtoList(year);
    }


    // todo: переписать без использования ФС.
    public String  getFileSpfinfap(Integer year) throws IOException {

        String fileName = "target/classes/templates/sp_fin_fap.xml";
        String encoding = "windows-1251";
        Writer output = new OutputStreamWriter(new FileOutputStream(fileName), encoding);
        output.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?>\n");
        output.write("<spfinfap>\n" +
                "<version>1.0</version>\n" +
                "<date>" + LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")) + "</date>\n");
        for (FapFinDto rec : getFapFinDtoList(year)) {
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
        return resourceLoader.getResource("file:D:\\data\\javaprojects\\med\\target\\classes\\templates\\sp_fin_fap.xml").getContentAsString(Charset.forName("windows-1251"));

    }
    //todo: убрать принты, когда станут не нужны.
    //todo: добавить проверку наличия данных за запрошенный год
    @Transactional
    public void fillNextMonth(Integer month, Integer year) throws NoSuchFieldException {
        if (month <1 | month > 12){
            throw new NoSuchFieldException("The month number should be between 1 and 12");
        }
        if (month == 1){
            throw new NoSuchFieldException("the method for transferring data from the previous year" +
                    " has not yet been implemented");
        }
        List<FapFin> fapFinEntityList = fapDao.getFapFinEntityList(year);
        fapFinEntityList.stream().map(FapFin::getPodr).forEach(System.out::println);
        fapFinEntityList.stream().forEach(s -> s.fillMonth(month));
        fapFinEntityList.stream().forEach(fapDao::save);
    }

    //todo: убрать принты, когда станут не нужны.
    //TODO: добавить проверку наличия  в базе норм подушевого финансирования на запрошенный месяц
    //todo: добавить проверку наличия данных финансирования ФАП за запрошенный год
    @Transactional
    public void fundingCalc(Integer month, Integer year){
//        Date fundingDate = new Date(year-1900, month-1, 1);
        LocalDate fundingDate = LocalDate.of(year, month, 1);
        System.out.println(fundingDate);
        Map<String, FundingNorma> fundingNormaMap = lpuDao.getFundingNormaEntityList(fundingDate)
                .stream().collect(Collectors.toMap(fn -> fn.getMoLpu(), fn -> fn));
        List<FapFin> fapFinEntityList = fapDao.getFapFinEntityList(year);
        for (FapFin ff: fapFinEntityList){
            String lpuKey = ff.getPodr().substring(0,30);
            System.out.println(lpuKey);
            FundingNorma fn = fundingNormaMap.get(lpuKey);
            double rateAstra = (fn.getQuantityInAstr() * 100 / (fn.getQuantityInAstr() + fn.getQuantityInKap()))/100.0;
            System.out.println(rateAstra);
            switch(month){
                case 1:
                    ff.setSummAstra1(Math.round(ff.getGFin1() * ff.getKYkomp1() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit1((Math.round(ff.getGFin1() * ff.getKYkomp1() * 100 / 12) / 100.0) - ff.getSummAstra1());
                    break;
                case 2:
                    ff.setSummAstra2(Math.round(ff.getGFin2() * ff.getKYkomp2() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit2((Math.round(ff.getGFin2() * ff.getKYkomp2() * 100 / 12) / 100.0) - ff.getSummAstra2());
                    break;
                case 3:
                    ff.setSummAstra3(Math.round(ff.getGFin3() * ff.getKYkomp3() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit3((Math.round(ff.getGFin3() * ff.getKYkomp3() * 100 / 12) / 100.0) - ff.getSummAstra3());
                    break;
                case 4:
                    ff.setSummAstra4(Math.round(ff.getGFin4() * ff.getKYkomp4() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit4((Math.round(ff.getGFin4() * ff.getKYkomp4() * 100 / 12) / 100.0) - ff.getSummAstra4());
                    break;
                case 5:
                    ff.setSummAstra5(Math.round(ff.getGFin5() * ff.getKYkomp5() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit5((Math.round(ff.getGFin5() * ff.getKYkomp5() * 100 / 12) / 100.0) - ff.getSummAstra5());
                    break;
                case 6:
                    ff.setSummAstra6(Math.round(ff.getGFin6() * ff.getKYkomp6() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit6((Math.round(ff.getGFin6() * ff.getKYkomp6() * 100 / 12) / 100.0) - ff.getSummAstra6());
                    break;
                case 7:
                    ff.setSummAstra7(Math.round(ff.getGFin7() * ff.getKYkomp7() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit7((Math.round(ff.getGFin7() * ff.getKYkomp7() * 100 / 12) / 100.0) - ff.getSummAstra7());
                    break;
                case 8:
                    ff.setSummAstra8(Math.round(ff.getGFin8() * ff.getKYkomp8() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit8((Math.round(ff.getGFin8() * ff.getKYkomp8() * 100 / 12) / 100.0) - ff.getSummAstra8());
                    break;
                case 9:
                    ff.setSummAstra9(Math.round(ff.getGFin9() * ff.getKYkomp9() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit9((Math.round(ff.getGFin9() * ff.getKYkomp9() * 100 / 12) / 100.0) - ff.getSummAstra9());
                    break;
                case 10:
                    ff.setSummAstra10(Math.round(ff.getGFin10() * ff.getKYkomp10() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit10((Math.round(ff.getGFin10() * ff.getKYkomp10() * 100 / 12) / 100.0) - ff.getSummAstra10());
                    break;
                case 11:
                    ff.setSummAstra11(Math.round(ff.getGFin11() * ff.getKYkomp11() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit11((Math.round(ff.getGFin11() * ff.getKYkomp11() * 100 / 12) / 100.0) - ff.getSummAstra11());
                    break;
                case 12:
                    ff.setSummAstra12(Math.round(ff.getGFin12() * ff.getKYkomp12() * rateAstra * 100 / 12) / 100.0);
                    ff.setSummKapit12((Math.round(ff.getGFin12() * ff.getKYkomp12() * 100 / 12) / 100.0) - ff.getSummAstra12());
                    break;
            }
        }
        fapFinEntityList.stream().forEach(fapDao::save);
    }

//    public String  getFileSpfinfap() throws IOException {
//
//        String fileName = "target/classes/templates/sp_fin_fap.xml";
//        String encoding = "windows-1251";
//        Writer output = new OutputStreamWriter(new FileOutputStream(fileName), encoding);
//        output.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?>\n");
//        output.write("<spfinfap>\n" +
//                "<version>1.0</version>\n" +
//                "<date>" + LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")) + "</date>\n");
//        for (FapFinDto rec: getFapFinDtoList()) {
//            output.write("<FAP>" +
//                    "<MKOD>" + rec.getMkod() + "</MKOD>" +
//                    "<NAME_PODR>" + rec.getNamePodr() + "</NAME_PODR>" +
//                    "<LPU>" + rec.getMoLpu() + "</LPU>" +
//                    "<PODR>" + rec.getPodr() + "</PODR>" +
//
//                    "<G_FIN1>" + rec.getGFin1() + "</G_FIN1>" +
//                    "<K_YKOMP1>" + rec.getKYkomp1() + "</K_YKOMP1>" +
//                    "<SUMM_ASTRA1>" + rec.getSummAstra1() + "</SUMM_ASTRA1>" +
//                    "<SUMM_KAPIT1>" + rec.getSummKapit1() + "</SUMM_KAPIT1>" +
//
//                    "<G_FIN2>" + rec.getGFin2() + "</G_FIN2>" +
//                    "<K_YKOMP2>" + rec.getKYkomp2() + "</K_YKOMP2>" +
//                    "<SUMM_ASTRA2>" + rec.getSummAstra2() + "</SUMM_ASTRA2>" +
//                    "<SUMM_KAPIT2>" + rec.getSummKapit2() + "</SUMM_KAPIT2>" +
//
//                    "<G_FIN3>" + rec.getGFin3() + "</G_FIN3>" +
//                    "<K_YKOMP3>" + rec.getKYkomp3() + "</K_YKOMP3>" +
//                    "<SUMM_ASTRA3>" + rec.getSummAstra3() + "</SUMM_ASTRA3>" +
//                    "<SUMM_KAPIT3>" + rec.getSummKapit3() + "</SUMM_KAPIT3>" +
//
//                    "<G_FIN4>" + rec.getGFin4() + "</G_FIN4>" +
//                    "<K_YKOMP4>" + rec.getKYkomp4() + "</K_YKOMP4>" +
//                    "<SUMM_ASTRA4>" + rec.getSummAstra4() + "</SUMM_ASTRA4>" +
//                    "<SUMM_KAPIT4>" + rec.getSummKapit4() + "</SUMM_KAPIT4>" +
//
//                    "<G_FIN5>" + rec.getGFin5() + "</G_FIN5>" +
//                    "<K_YKOMP5>" + rec.getKYkomp5() + "</K_YKOMP5>" +
//                    "<SUMM_ASTRA5>" + rec.getSummAstra5() + "</SUMM_ASTRA5>" +
//                    "<SUMM_KAPIT5>" + rec.getSummKapit5() + "</SUMM_KAPIT5>" +
//
//                    "<G_FIN6>" + rec.getGFin6() + "</G_FIN6>" +
//                    "<K_YKOMP6>" + rec.getKYkomp6() + "</K_YKOMP6>" +
//                    "<SUMM_ASTRA6>" + rec.getSummAstra6() + "</SUMM_ASTRA6>" +
//                    "<SUMM_KAPIT6>" + rec.getSummKapit6() + "</SUMM_KAPIT6>" +
//
//                    "<G_FIN7>" + rec.getGFin7() + "</G_FIN7>" +
//                    "<K_YKOMP7>" + rec.getKYkomp7() + "</K_YKOMP7>" +
//                    "<SUMM_ASTRA7>" + rec.getSummAstra7() + "</SUMM_ASTRA7>" +
//                    "<SUMM_KAPIT7>" + rec.getSummKapit7() + "</SUMM_KAPIT7>" +
//
//                    "<G_FIN8>" + rec.getGFin8() + "</G_FIN8>" +
//                    "<K_YKOMP8>" + rec.getKYkomp8() + "</K_YKOMP8>" +
//                    "<SUMM_ASTRA8>" + rec.getSummAstra8() + "</SUMM_ASTRA8>" +
//                    "<SUMM_KAPIT8>" + rec.getSummKapit8() + "</SUMM_KAPIT8>" +
//
//                    "<G_FIN9>" + rec.getGFin9() + "</G_FIN9>" +
//                    "<K_YKOMP9>" + rec.getKYkomp9() + "</K_YKOMP9>" +
//                    "<SUMM_ASTRA9>" + rec.getSummAstra9() + "</SUMM_ASTRA9>" +
//                    "<SUMM_KAPIT9>" + rec.getSummKapit9() + "</SUMM_KAPIT9>" +
//
//
//                    "<G_FIN10>" + rec.getGFin10() + "</G_FIN10>" +
//                    "<K_YKOMP10>" + rec.getKYkomp10() + "</K_YKOMP10>" +
//                    "<SUMM_ASTRA10>" + rec.getSummAstra10() + "</SUMM_ASTRA10>" +
//                    "<SUMM_KAPIT10>" + rec.getSummKapit10() + "</SUMM_KAPIT10>" +
//
//                    "<G_FIN11>" + rec.getGFin11() + "</G_FIN11>" +
//                    "<K_YKOMP11>" + rec.getKYkomp11() + "</K_YKOMP11>" +
//                    "<SUMM_ASTRA11>" + rec.getSummAstra11() + "</SUMM_ASTRA11>" +
//                    "<SUMM_KAPIT11>" + rec.getSummKapit11() + "</SUMM_KAPIT11>" +
//
//                    "<G_FIN12>" + rec.getGFin12() + "</G_FIN12>" +
//                    "<K_YKOMP12>" + rec.getKYkomp12() + "</K_YKOMP12>" +
//                    "<SUMM_ASTRA12>" + rec.getSummAstra12() + "</SUMM_ASTRA12>" +
//                    "<SUMM_KAPIT12>" + rec.getSummKapit12() + "</SUMM_KAPIT12>" +
//                    "</FAP>\n");
//        }
//        output.write("</spfinfap>");
//        output.flush();
//        output.close();
//        return IOUtils.toString(resourceLoader.getResource("file:D:\\data\\javaprojects\\med\\target\\classes\\templates\\sp_fin_fap.xml").getInputStream());
//    }
}
