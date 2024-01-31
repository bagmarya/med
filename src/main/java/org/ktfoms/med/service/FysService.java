package org.ktfoms.med.service;

import org.ktfoms.med.dao.FysDao;
import org.ktfoms.med.entity.Fys;
import org.ktfoms.med.entity.LicensePol;
import org.ktfoms.med.entity.LicenseStac;
import org.ktfoms.med.entity.Price;
import org.ktfoms.med.helper.DbfHelper;
import org.ktfoms.med.helper.ExcelHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FysService {
    private final FysDao fysDao;

    public FysService(FysDao fysDao) {
        this.fysDao = fysDao;
    }

    @Transactional
    public String parseFysXls(InputStream in, boolean parsePrice) {
        try {
            List<Fys> fysList = ExcelHelper.parseFysXls(in, parsePrice);

            if (fysList.size()==0) { return "Входной файл пуст";}
            List<String> duplicates = fysList.stream().map(Fys::getKodUslMz)
                    //группируем в map (код -> количество вхождений)
                    .collect(Collectors.groupingBy(Function.identity()))
                    //проходим по группам
                    .entrySet()
                    .stream()
                    //отбираем коды, встречающихся более одного раза
                    .filter(e -> e.getValue().size() > 1)
                    //вытаскиваем ключи
                    .map(Map.Entry::getKey)
                    //собираем в список
                    .collect(Collectors.toList());
            String duplicatesMessage = "";
            if (duplicates.size() > 0) {
                duplicatesMessage = "Файл содержит одинаковые коды услуг " + duplicates;
            }
            fysDao.clearFys();
            fysList.forEach(fysDao::save);
            return "Файл справочника успешно импортирован. " + (parsePrice ? "" : "Без стоимости. ") + duplicatesMessage;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Transactional
    public String parsePriceXls(InputStream in) {
        try {
            List<Price> priceList = ExcelHelper.parsePriceXls(in);

            if (priceList.size()==0) { return "Входной файл пуст";}

            fysDao.clearPrice();
            priceList.forEach(fysDao::save);
            return "Файл со стоимостью успешно импортирован.";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @Transactional
    public String fysCalculate() {
        List<Fys> fysList = fysDao.getFysEntityList();
        List<Price> priceList = fysDao.getPriceEntityList();
        Map<String, Price> priceMap = priceList.stream().collect(Collectors.toMap(p -> p.getKod() + p.getMkr(), p -> p));
        System.out.println(priceMap);
        List<String> dentistryCodes = List.of("063", "064", "065", "066", "067"); //Коды специальности для стоматологии
        List<String> urgentMkr = List.of("ПН", "Д"); //Типы услуги для неотложки
        for (Fys fys:fysList){
            fys.setD1(0.0);
            fys.setV1(0.0);
            fys.setD1Uet(0.0);
            fys.setV1Uet(0.0);
            fys.setD2(0.0);
            fys.setV2(0.0);
            fys.setD2Uet(0.0);
            fys.setV2Uet(0.0);
            Price price = null;
            if (fys.isOms() & (fys.isPos() | fys.getVUet() > 0 | fys.getDUet() > 0)){
                if(dentistryCodes.contains(fys.getKodSp())){    //При условии что код специальности принадлежит стоматологии
                    price = priceMap.get("065П1");
                    fys.setD1(Math.round(price.getD1()*fys.getDUet()*100) / 100.0);
                    fys.setV1(Math.round(price.getV1()*fys.getVUet()*100) / 100.0);
                    fys.setD1Uet(Math.round(price.getD1()*fys.getDUet()*100) / 100.0);
                    fys.setV1Uet(Math.round(price.getV1()*fys.getVUet()*100) / 100.0);
                    fys.setD2(Math.round(price.getD2()*fys.getDUet()*100) / 100.0);
                    fys.setV2(Math.round(price.getV2()*fys.getVUet()*100) / 100.0);
                    fys.setD2Uet(Math.round(price.getD2()*fys.getDUet()*100) / 100.0);
                    fys.setV2Uet(Math.round(price.getV2()*fys.getVUet()*100) / 100.0);
                } else {  //Если код специальности - не стамотология
                    if(urgentMkr.contains(fys.getMkr())){   //Если неотложка
                        if(priceMap.containsKey(fys.getKodSp() + "Д")){price = priceMap.get(fys.getKodSp() + "Д");}
                        if(priceMap.containsKey(fys.getKodSp() + "ПН")){price = priceMap.get(fys.getKodSp() + "ПН");}
                        if(price!=null & fys.isPos()){
                            fys.setD1(price.getD1());
                            fys.setV1(price.getV1());
                            fys.setD2(price.getD2());
                            fys.setV2(price.getV2());}
                    } else { //а если не неотложка
                        if (fys.getMkr().equals("ПД") | (fys.getMkr().equals("ДПР") & fys.getNameYsl().contains("Диспансерный"))) {
                            if(priceMap.containsKey(fys.getKodSp() + fys.getMkr())){price = priceMap.get(fys.getKodSp() + fys.getMkr());}
                            if(price!=null & fys.isPos()){
                                fys.setD1(price.getD1());
                                fys.setV1(price.getV1());
                                fys.setD2(price.getD2());
                                fys.setV2(price.getV2());}
                        } else {
                            if(priceMap.containsKey(fys.getKodSp() + "П2")){price = priceMap.get(fys.getKodSp() + "П2");}
                            if(priceMap.containsKey(fys.getKodSp() + "П1")){price = priceMap.get(fys.getKodSp() + "П1");}
                            if(price!=null & fys.isPos()){
                                fys.setD1(price.getD1());
                                fys.setV1(price.getV1());
                                fys.setD2(price.getD2());
                                fys.setV2(price.getV2());}
                            }
                        }
                    }
                }

            if (!dentistryCodes.contains(fys.getKodSp()) & (fys.getVUet() > 0 | fys.getDUet() > 0)) {    //не стоматология с УЕТами
                price = priceMap.get("065П1");
                fys.setD1Uet(Math.round(price.getD1()*fys.getDUet()*100) / 100.0);
                fys.setV1Uet(Math.round(price.getV1()*fys.getVUet()*100) / 100.0);
                fys.setD2Uet(Math.round(price.getD2()*fys.getDUet()*100) / 100.0);
                fys.setV2Uet(Math.round(price.getV2()*fys.getVUet()*100) / 100.0);
            }
        }
        fysList.forEach(fysDao::save);
        return "Расчет произведен успешно";
    }

    public ByteArrayInputStream createFysDbf() {
        List<Fys> fysEntityList = fysDao.getFysEntityList();
        ByteArrayInputStream in = DbfHelper.createFysDbf(fysEntityList);
        return in;
    }

    public ByteArrayInputStream createObrcDbf() {
        List<Price> obrcPriceEntityList = fysDao.getObrcPriceEntityList();
        ByteArrayInputStream in = DbfHelper.createObrcDbf(obrcPriceEntityList);
        return in;
    }

    //Экспорт FYS из базы в xml (справочник медицинских услуг)
    @Transactional
    public String getFysXML() {
        List<Fys> fysEntityList = fysDao.getFysEntityList();
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<FYS>\n");//<?xml version="1.0" encoding="utf-8"?>
        for(Fys fys : fysEntityList){
            builder.append(
                    "    <USL>\n" +
                            "      <KOD_SP>" + fys.getKodSp() + "</KOD_SP>\n" +
                            "      <NAME_YSL>" + fys.getNameYsl() + "</NAME_YSL>\n" +
                            "      <KOD_USL_MZ>" + fys.getKodUslMz() + "</KOD_USL_MZ>\n" +
                            "      <RZ>" + fys.getRz() + "</RZ>\n" +
                            "      <TYP>" + fys.getTyp() + "</TYP>\n" +
                            "      <KLAS>" + fys.getKlas() + "</KLAS>\n" +
                            "      <VID>" + (fys.getVid() == null ? "" : fys.getVid()) + "</VID>\n" +
                            "      <PVID>" + (fys.getPvid() == null ? "" : fys.getPvid()) + "</PVID>\n" +
                            "      <OMS>" + (fys.isOms() ? "+" : "-") + "</OMS>\n" +
                            "      <POS>" + (fys.isPos() ? "+" : "-") + "</POS>\n" +
                            "      <MKR>" + fys.getMkr() + "</MKR>\n" +
                            "      <V_uet>" + fys.getVUet() + "</V_uet>\n" +
                            "      <D_uet>" + fys.getDUet() + "</D_uet>\n" +
                            "      <D1>" + fys.getD1() + "</D1>\n" +
                            "      <V1>" + fys.getV1() + "</V1>\n" +
                            "      <D1_uet>" + fys.getD1Uet() + "</D1_uet>\n" +
                            "      <V1_uet>" + fys.getV1Uet() + "</V1_uet>\n" +
                            "      <D2>" + fys.getD2() + "</D2>\n" +
                            "      <V2>" + fys.getV2() + "</V2>\n" +
                            "      <D2_uet>" + fys.getD2Uet() + "</D2_uet>\n" +
                            "      <V2_uet>" + fys.getV2Uet() + "</V2_uet>\n" +
                            "      <V021_D>" + (fys.getV021D() == null ? "" : fys.getV021D()) + "</V021_D>\n" +//fys.getV021D()
                            "      <V021_V>" + (fys.getV021V() == null ? "" : fys.getV021V()) + "</V021_V>\n" +//fys.getV021V()
                            "      <DIAG_N>" + (fys.getDiagN() == null ? "" : fys.getDiagN()) + "</DIAG_N>\n" +//fys.getDiagN()
                            "      <DIAG_K>" + (fys.getDiagK() == null ? "" : fys.getDiagK()) + "</DIAG_K>\n" +//fys.getDiagK()
                            "      <DIAG_DN>" + (fys.getDiagDn() == null ? "" : fys.getDiagDn()) + "</DIAG_DN>\n" +
                            "      <DS_category>" + (fys.getDsCategory() == null ? "" : fys.getDsCategory()) + "</DS_category>\n" +

                            "    </USL>\n");
        }
        builder.append("</FYS>\n");
        return builder.toString();
    }


}



