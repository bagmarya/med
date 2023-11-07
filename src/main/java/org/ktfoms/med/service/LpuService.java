package org.ktfoms.med.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.dto.FundingNormaDto;
import org.ktfoms.med.dto.FundingNormaSmpDto;
import org.ktfoms.med.dto.FundingNormaSmpInfo;
import org.ktfoms.med.dto.LpuF003Dto;
import org.ktfoms.med.dto.SpF003Lpu;
import org.ktfoms.med.dto.SpFundingNormaSmp;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.FundingNormaSmp;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.form.EditFundingNormaForm;
import org.ktfoms.med.helper.ExcelHelper;
import org.ktfoms.med.enums.Month;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Service
public class LpuService {

    private static final Logger logger = LoggerFactory.getLogger(LpuService.class);

    private final LpuDao lpuDao;

    @Autowired
    private ResourceLoader resourceLoader;

    public LpuService(LpuDao lpuDao){
        this.lpuDao = lpuDao;
    }

    public Lpu getLpuById(Integer id){
        return lpuDao.getById(id);
    }

    public List<FundingNorma> getFundingNormaInfos(){
        Integer month = LocalDate.now().getMonthValue();
        Integer year = LocalDate.now().getYear();
        LocalDate thisMonthDate = LocalDate.of(year, month, 01);
        LocalDate nextMonthDate = LocalDate.of(year+(month+1)/12, (month%12+1), 01);
        return lpuDao.getFundingNormaEntityList(thisMonthDate, nextMonthDate);
    }

    //Есть ли в базе записи за такой-то месяц (дата - первое число месяца)
    public boolean isExistFundingNormaByDate(LocalDate date){
        logger.info("Проверяем наличие в базе записей за " +date);
        return lpuDao.getFundingNormaDtoListByDate(date).size() > 0;
    }

    //Проверка на наличие заполненных ячеек с численностью и нормативом финансирования в записях конкретного месяца
    public boolean isEmptyFundingNormaByDate(LocalDate date){
        logger.info("Проверяем наличие в записях за запрошенный период значимых данных." + date);
        List<FundingNormaDto> dtoList = lpuDao.getFundingNormaDtoListByDate(date);
        if (dtoList.stream()
                .flatMap(d-> Stream.of(d.getQuantityInAstr(), d.getQuantityInKap(), d.getNorma()))
                .filter(Objects::nonNull)
                .toList().size() == 0) {
            logger.info("Записи в базе есть, но ячейки значений пусты");
            return true;
        }
        if (dtoList.stream()
                .flatMap(d-> Stream.of(d.getQuantityInAstr(), d.getQuantityInKap(), d.getNorma()))
                .filter(Objects::nonNull).map(Number::doubleValue).allMatch(e -> e == 0.0)){
            logger.info("Записи в базе есть, но в ячейках только нули");
            return true;
        }
        return false;
    }

    public FundingNorma getFundingNormaById(int id) {
        return lpuDao.getFundingNormaById(id);
    }

    @Transactional
    public void saveFundingNorma(int id, EditFundingNormaForm editFundingNormaForm) {
        FundingNorma fn = lpuDao.getFundingNormaById(id);
        if (editFundingNormaForm.getQuantityInAstr() != "") {
            fn.setQuantityInAstr(Integer.parseInt(editFundingNormaForm.getQuantityInAstr()));
        } else {
            fn.setQuantityInAstr(null);
        }
        if (editFundingNormaForm.getQuantityInKap() != "") {
            fn.setQuantityInKap(Integer.parseInt(editFundingNormaForm.getQuantityInKap()));
        } else {
            fn.setQuantityInKap(null);}
        if (editFundingNormaForm.getNorma() != "") {
            fn.setNorma(Double.parseDouble(editFundingNormaForm.getNorma().replace(',', '.')));
        } else {
            fn.setNorma(null);}
        System.out.println(fn);
        lpuDao.save(fn);
    }


    public void addPeriodFundingNorma(Integer month, Integer year) {
        LocalDate fundingDate = LocalDate.of(year, month, 1);
        System.out.println(fundingDate);//todo: убрать принты
        List<String> oldList = lpuDao.getFundingNormaEntityList(fundingDate)
                .stream()
                .map(FundingNorma::getMoLpu)
                .collect(Collectors.toList());
        List<Lpu> lpuList = lpuDao.getFundedLpuList();
        for (Lpu lpu:lpuList){
            if (!oldList.contains(lpu.getKodSp())){
                FundingNorma newFN = new FundingNorma();
                newFN.setLpuId(lpu.getId());
                newFN.setMoLpu(lpu.getKodSp());
                newFN.setFundingDate(fundingDate);
                newFN.setMNameF(lpu.getMNameF());
                lpuDao.save(newFN);
            }

        }

    }

    public Lpu getLpuByOid(String oid) {
            return lpuDao.getLpuByOid(oid);
    }

    public List<Lpu> getLpuEntityList() {
        return lpuDao.getLpuEntityList();
    }

    @Transactional
    public void parseSpFundingNormaSmp() throws IOException {
        ObjectMapper objectMapper = new XmlMapper();
        SpFundingNormaSmp spFundingNormaSmp = objectMapper.readValue(
                resourceLoader.getResource("file:PD_TARIF.xml").getContentAsString(Charset.forName("UTF-8")),
                SpFundingNormaSmp.class);
        for (FundingNormaSmpDto fns: spFundingNormaSmp.getZap()) {
            lpuDao.save(new FundingNormaSmp(fns));
        }
    }

    @Transactional
    public String parseSpFundingNormaSmp(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new XmlMapper();
        SpFundingNormaSmp spFundingNormaSmp = objectMapper.readValue(
                file.getResource().getContentAsString(StandardCharsets.UTF_8),
                SpFundingNormaSmp.class);
        for (FundingNormaSmpDto fns: spFundingNormaSmp.getZap()) {
            lpuDao.save(new FundingNormaSmp(fns));
        }
        logger.info("Справочник норм ПФ СМП успешно перезагружен из xml. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Справочник норм ПФ СМП успешно загружен";
    }
    public String getFileSpFundingNormaSmp(){
        List<FundingNormaSmp> fundingNormaSmpList = lpuDao.getFundingNormaSmpEntityList();
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<PACKET>\n" +
                "  <ZGLV>PD_TARIF</ZGLV>\n" +
                "  <VERSION>1.0</VERSION>\n");
        builder.append("  <DATA>" + LocalDate.now() + "</DATA>\n");
        for(FundingNormaSmp fns : fundingNormaSmpList){
            builder.append("  <ZAP>\n" +
                    "    <USL_OK>" + fns.getUslOk() + "</USL_OK>\n" +
                    "    <SMO>" + fns.getSmo() + "</SMO>\n" +
                    "    <MCOD>" + fns.getMcod() + "</MCOD>\n" +
                    "    <KOL_ZL>" + fns.getKolZl() + "</KOL_ZL>\n" +
//                    "    <TARIF>" + (new DecimalFormat("#0.00").format(fns.getTarif())) + "</TARIF>\n" +
                    "    <TARIF>" + String.format(Locale.ROOT, "%.2f", fns.getTarif()) + "</TARIF>\n" +
                    "    <DATEBEG>" + fns.getDatebeg() + "</DATEBEG>\n" +
                    "    <DATEEND>" + fns.getDateend() + "</DATEEND>\n" +
                    "  </ZAP>\n");
        }
        builder.append("</PACKET>");
        return builder.toString();
    }
    public String getFileSpFundingNormaSmp(Integer year){
        List<FundingNormaSmp> fundingNormaSmpList = lpuDao.getFundingNormaSmpEntityList(year);
        System.out.println(fundingNormaSmpList);
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<PACKET>\n" +
                "  <ZGLV>PD_TARIF</ZGLV>\n" +
                "  <VERSION>1.0</VERSION>\n");
        builder.append("  <DATA>" + LocalDate.now() + "</DATA>\n");
        for(FundingNormaSmp fns : fundingNormaSmpList){
            builder.append("  <ZAP>\n" +
                    "    <USL_OK>" + fns.getUslOk() + "</USL_OK>\n" +
                    "    <SMO>" + fns.getSmo() + "</SMO>\n" +
                    "    <MCOD>" + fns.getMcod() + "</MCOD>\n" +
                    "    <KOL_ZL>" + fns.getKolZl() + "</KOL_ZL>\n" +
//                    "    <TARIF>" + (new DecimalFormat("#0.00").format(fns.getTarif())) + "</TARIF>\n" +
                    "    <TARIF>" + String.format(Locale.ROOT, "%.2f", fns.getTarif()) + "</TARIF>\n" +
                    "    <DATEBEG>" + fns.getDatebeg() + "</DATEBEG>\n" +
                    "    <DATEEND>" + fns.getDateend() + "</DATEEND>\n" +
                    "  </ZAP>\n");
        }
        builder.append("</PACKET>");
        return builder.toString();
    }

    @Transactional
    public String parseFundingNormaSmpXlsx(InputStream in) {
        try {
            List<FundingNormaSmp> fundingNormaSmpList = ExcelHelper.parseFundingNormaSmpXlsx(in);

            if (fundingNormaSmpList.size() == 0) {
                return "Входной файл пуст";
            }

            fundingNormaSmpList.forEach(lpuDao::save);
            logger.info("Справочник норм ПФ СМП успешно загружен из excel. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
            return "Файл с нормативами ПФ СМП успешно импортирован.";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public List<FundingNormaSmpInfo> getFundingNormaSmpInfos() {
        Map<Integer, String> lpuMap = lpuDao.getLpuEntityList().stream().collect(toMap(Lpu::getMkod, Lpu::getMNameF));
        List<FundingNormaSmpInfo> infosList = new ArrayList<>();
        List<FundingNormaSmp> entityList = lpuDao.getFundingNormaSmpEntityList();
        Map<String, FundingNormaSmpInfo> infosMap = new TreeMap<>();
        for (FundingNormaSmp entity: entityList){
            String key = entity.getDatebeg().toString()+entity.getMcod().toString();
            if (!infosMap.containsKey(key)){
                FundingNormaSmpInfo newInfo =  new FundingNormaSmpInfo();
                newInfo.setMcod(entity.getMcod());
                newInfo.setLpuName(lpuMap.get(entity.getMcod()));
                newInfo.setDatebeg(entity.getDatebeg().toString());
                newInfo.setDateend(entity.getDateend().toString());
                if (entity.getSmo() == 45001){newInfo.setKolZlAstr(entity.getKolZl());}
                if (entity.getSmo() == 45002){newInfo.setKolZlKapit(entity.getKolZl());}
                newInfo.setTarif(entity.getTarif());
                infosMap.put(key, newInfo);
            } else {
                if (entity.getSmo() == 45001){infosMap.get(key).setKolZlAstr(entity.getKolZl());}
                if (entity.getSmo() == 45002){infosMap.get(key).setKolZlKapit(entity.getKolZl());}
            }
        }
        for (Map.Entry<String, FundingNormaSmpInfo> entry : infosMap.entrySet()) {
            infosList.add(entry.getValue());
        }
        return infosList;
    }

    @Transactional
    public String parseF003ForAddLpu(MultipartFile file) throws IOException {
        List<Integer> mcodesInBase = lpuDao.getMcodeList();
        ObjectMapper objectMapper = new XmlMapper();
        SpF003Lpu spLpu = objectMapper.readValue(
                file.getResource().getContentAsString(Charset.forName("windows-1251")),
                SpF003Lpu.class);
        for (LpuF003Dto dto: spLpu.getLpuList()) {
            if(mcodesInBase.contains(dto.getMcod())) {continue;}
            lpuDao.save(new Lpu(dto));
        }
        return "Список ЛПУ успешно загружен";
    }

    @Transactional
    public String parseF003ForUpdLpu(MultipartFile file) throws IOException {
        List<Integer> mcodesInBase = lpuDao.getMcodeList();
        ObjectMapper objectMapper = new XmlMapper();
        SpF003Lpu spLpu = objectMapper.readValue(
                file.getResource().getContentAsString(Charset.forName("windows-1251")),
                SpF003Lpu.class);
        for (LpuF003Dto dto: spLpu.getLpuList()) {

            if(mcodesInBase.contains(dto.getMcod())) {
                Lpu lpu = lpuDao.getLpuByMcod(dto.getMcod());
//                lpu.setKpp(dto.getKpp());
                lpu.setPostId(dto.getPostId());
//                lpu.setGvfio(dto.getFamRuk() + " " + dto.getImRuk() + " " + dto.getOtRuk());
                lpu.setTel(dto.getTel());
                lpu.setFax(dto.getFax());
                lpu.setEMail(dto.getEMail());
                if (dto.getDateBeg()!=""){lpu.setDateBeg(LocalDate.parse(dto.getDateBeg(), DateTimeFormatter.ofPattern("dd.MM.uuuu")));}
                if (dto.getDateEnd()!=""){lpu.setDateEnd(LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd.MM.uuuu")));}
                lpuDao.save(lpu);
            }

        }
        return "Информация о ЛПУ успешно обновлена";
    }

    @Transactional
    public String parseXlsxForUpdLpu(MultipartFile file) throws Exception {
        List<Integer> mcodesInBase = lpuDao.getMcodeList();
        InputStream in = new ByteArrayInputStream(file.getBytes());
        List<LpuF003Dto> listLpu = ExcelHelper.parseLpuXlsx(in);
        for (LpuF003Dto dto: listLpu) {

            if(mcodesInBase.contains(dto.getMcod())) {
                Lpu lpu = lpuDao.getLpuByMcod(dto.getMcod());
                lpu.setKpp(dto.getKpp());
                lpu.setCogrn(dto.getOgrn());
                lpu.setLpuinn(dto.getLpuinn());
                lpu.setMNameS(dto.getMNameS());
//                lpu.setMNameF(dto.getMNameF());
                lpuDao.save(lpu);
            }
        }
        return "Информация о ЛПУ успешно обновлена";
    }
    public Lpu getLpuByMcod(Integer mcod) {
        return lpuDao.getLpuByMcod(mcod);
    }

    // Метод заполняет указанный месяц в справочнике ПФ АПП данными из предыдущего месяца
    @Transactional
    public String fillNextMonthNormPd(Integer month, Integer year) {
        LocalDate date = LocalDate.of(year, month, 01);
        if(!isExistFundingNormaByDate(date.minusMonths(1))) {
            return "Данных за предшествующий месяц нет, нечего копировать";
        }
        if(!isExistFundingNormaByDate(date)){
            logger.info("Добавляем новые записи в спр. ПФ АПП за " + month + year);
            addPeriodFundingNorma(month, year);
        }
        List<FundingNorma> newEntityList = lpuDao.getFundingNormaEntityList(date);
        List<FundingNorma> oldEntityList = lpuDao.getFundingNormaEntityList(date.minusMonths(1));
        Map<String, FundingNorma> oldMap = oldEntityList.stream().collect(Collectors.toMap(d -> d.getMoLpu(), d -> d));
        for (FundingNorma fn:newEntityList) {
            FundingNorma source = oldMap.get(fn.getMoLpu());
            fn.setQuantityInAstr(source.getQuantityInAstr());
            fn.setQuantityInKap(source.getQuantityInKap());
            fn.setNorma(source.getNorma());
            lpuDao.save(fn);
        }
        logger.info("Месяц " + Month.of(month) + " " + year + "г справочника ПФ АПП заполнен данными скопировннными из предыдущего периода." +
                " Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Месяц " + Month.of(month) + " " + year + "г заполнен данными скопировннными из предыдущего периода";
    }

    public boolean isExistFundingNormaSmpByDate(LocalDate date) {
        logger.info("Проверяем наличие в базе записей за " +date);
        return lpuDao.getFundingNormaSmpEntityList(date).size() > 0;
    }

    @Transactional
    public String fillNextMonthNormaSmp(Integer month, Integer year) {
        LocalDate date = LocalDate.of(year, month, 01);
        if(!isExistFundingNormaSmpByDate(date.minusMonths(1))) {
            return "Данных за предшествующий месяц нет, нечего копировать";
        }
        if (isExistFundingNormaSmpByDate(date)) {lpuDao.clearFundingNormaSmp(date);}
        List<FundingNormaSmp> oldEntityList = lpuDao.getFundingNormaSmpEntityList(date.minusMonths(1));
        for (FundingNormaSmp fns:oldEntityList) {
            FundingNormaSmp newFundingNormaSmp = new FundingNormaSmp();
            newFundingNormaSmp.setMcod(fns.getMcod());
            newFundingNormaSmp.setSmo(fns.getSmo());
            newFundingNormaSmp.setUslOk(fns.getUslOk());
            newFundingNormaSmp.setKolZl(fns.getKolZl());
            newFundingNormaSmp.setTarif(fns.getTarif());
            newFundingNormaSmp.setDatebeg(fns.getDatebeg().plusMonths(1));
            newFundingNormaSmp.setDateend(fns.getDateend().plusMonths(1));
            lpuDao.save(newFundingNormaSmp);
        }
        logger.info("Месяц " + Month.of(month) + " " + year + "г справочника ПФ СМП заполнен данными скопировннными из предыдущего периода." +
                " Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Месяц " + Month.of(month) + " " + year + "г заполнен данными скопировннными из предыдущего периода";
    }
    @Transactional
    public EditFundingNormaForm getFundingNormaForm(int mcod, LocalDate datebeg, LocalDate dateend) {
        logger.info("Собираемся отредактировать запись справочника ПФ СМП для " + mcod + " за период с " + datebeg + " по " + dateend);
        EditFundingNormaForm editFundingNormaForm = new EditFundingNormaForm();
        List<FundingNormaSmp> entityList = lpuDao.getFundingNormaSmpEntityList(mcod, datebeg, dateend);
        for (FundingNormaSmp entity: entityList) {
            if (!Objects.isNull(entity.getKolZl())) {
                if (entity.getSmo() == 45001) {
                    editFundingNormaForm.setQuantityInAstr(entity.getKolZl().toString());
                }
                if (entity.getSmo() == 45002) {
                    editFundingNormaForm.setQuantityInKap(entity.getKolZl().toString());
                }
            }
            if (!Objects.isNull(entity.getTarif())) {
                editFundingNormaForm.setNorma(String.valueOf(entity.getTarif()));
            }
        }
        logger.info("Исходные данные: " + editFundingNormaForm);
        return editFundingNormaForm;
    }

    @Transactional
    public String saveFundingNormaSmp(int mcod, LocalDate datebeg, LocalDate dateend, EditFundingNormaForm form) {
        logger.info("Данные для редактирования: " + form + " MCOD = " + mcod + " период с " + datebeg + " по " + dateend);

        try {
            List<FundingNormaSmp> entityList = lpuDao.getFundingNormaSmpEntityList(mcod, datebeg, dateend);
            for (FundingNormaSmp entity : entityList) {
                if (!Objects.isNull(form.getQuantityInAstr()) && entity.getSmo() == 45001) {
                    entity.setKolZl(Integer.parseInt(form.getQuantityInAstr()));
                }
                if (!Objects.isNull(form.getQuantityInKap()) && entity.getSmo() == 45002) {
                    entity.setKolZl(Integer.parseInt(form.getQuantityInKap()));
                }
                if (!Objects.isNull(entity.getTarif())) {
                    entity.setTarif(Double.parseDouble(form.getNorma().replace(',', '.')));
                }
                lpuDao.save(entity);
            }
        } catch (NumberFormatException nfe) {
            logger.info("При вводе данных допущена ошибка. Запись оставлена без изменений");
            return "При вводе данных допущена ошибка. Запись оставлена без изменений";
        }
        logger.info("Отредактирована запись справочника ПФ СМП для " + mcod + " за период с " + datebeg + " по " + dateend
                + ". Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Запись для " + mcod + " отредактирована.";
    }

}
