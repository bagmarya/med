package org.ktfoms.med.service;

import org.ktfoms.med.dao.FysDao;
import org.ktfoms.med.entity.Fys;
import org.ktfoms.med.entity.Price;
import org.ktfoms.med.helper.DbfHelper;
import org.ktfoms.med.helper.ExcelHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
            //TODO: узнать что делать с проверкой на дубликаты
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
            if (fys.isOms() & (fys.isPos() | fys.getVUet() > 0 | fys.getDUet() > 0)){
                if(dentistryCodes.contains(fys.getKodSp())){    //При условии что код специальности принадлежит стоматологии
                    Price price = priceMap.get("065П1");
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
                        fys.setD1(800.12);
                        fys.setV1(800.12);
                        fys.setD2(800.12);
                        fys.setV2(800.12);
                    } else { //а если не неотложка
                        Price price = null;
                        if(priceMap.containsKey(fys.getKodSp() + "П2")){price = priceMap.get(fys.getKodSp() + "П2");}
                        if(priceMap.containsKey(fys.getKodSp() + "П1")){price = priceMap.get(fys.getKodSp() + "П1");}
                        if(price!=null & fys.isPos()){
                            fys.setD1(price.getD1());
                            fys.setV1(price.getV1());
                            fys.setD2(price.getD2());
                            fys.setV2(price.getV2());
                        }
                    }

                }
            }
            if (!dentistryCodes.contains(fys.getKodSp()) & (fys.getVUet() > 0 | fys.getDUet() > 0)) {    //не стоматология с УЕТами
                Price price = priceMap.get("065П1");
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

    @Transactional
    public String updateSpezCode() {
        fysDao.clearSpezCode();
        fysDao.fillSpezCode();
        return "Список врачебных специальностей успешно заполнен";
    }
}



