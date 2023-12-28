package org.ktfoms.med.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.ktfoms.med.dao.LicenseDao;
import org.ktfoms.med.dto.LicensePolDto;
import org.ktfoms.med.dto.LicensePolInfo;
import org.ktfoms.med.dto.LicenseStacDto;
import org.ktfoms.med.dto.LicenseStacInfo;
import org.ktfoms.med.dto.ProfilDto;
import org.ktfoms.med.dto.SpLicense;
import org.ktfoms.med.dto.SpV002Profil;
import org.ktfoms.med.entity.LicensePol;
import org.ktfoms.med.entity.LicenseStac;
import org.ktfoms.med.entity.Profil;
import org.ktfoms.med.form.LicensePolForm;
import org.ktfoms.med.form.LicenseStacForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
    private final LicenseDao licenseDao;
    @Autowired
    private Environment env;
    public LicenseService(LicenseDao licenseDao) {
        this.licenseDao = licenseDao;
    }

    @Transactional
    public String parseSpV002(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new XmlMapper();
        SpV002Profil spV002Profil = objectMapper.readValue(
                file.getResource().getContentAsString(Charset.forName("windows-1251")),
                SpV002Profil.class);
        System.out.println(spV002Profil.getZap().size());
        licenseDao.clearProfil();
        for (ProfilDto p: spV002Profil.getZap()) {
            if (p.getDateEnd() == "" || (LocalDate.parse(p.getDateEnd(), DateTimeFormatter.ofPattern("dd.MM.uuuu"))).isAfter(LocalDate.now())) {
            System.out.println(p);
            licenseDao.save(new Profil(p));
            }
        }
        return "Классификатор профилей оказанной медицинской помощи успешно обновлен";
    }

    @Transactional
    public String parseSpLicense(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new XmlMapper();
        SpLicense spLicense = objectMapper.readValue(
                file.getResource().getContentAsString(Charset.forName("windows-1251")),
                SpLicense.class);
        for (LicenseStacDto dto: spLicense.getStacionar()) {
            licenseDao.save(new LicenseStac(dto));
        }
        for (LicensePolDto dto: spLicense.getPoliclinic()) {
            licenseDao.save(new LicensePol(dto));
        }
        return "Справочник лицензий успешно загружен";
    }

    //Экспорт лицензий из базы в xml (справочник лицензий)
    @Transactional
    public String getFileLicences() {
        List<LicenseStac> licenseStacList = licenseDao.getLicenseStacList();
        List<LicensePol> licensePolList = licenseDao.getLicensePolList();
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n" +
                "<Root>\n" +
                "  <Stacionar>\n");
        for(LicenseStac licenseStac : licenseStacList){
            builder.append(
                    "    <License>\n" +
                    "      <MCOD>" + licenseStac.getMcod() + "</MCOD>\n" +
                    "      <PROF_CODE>" + licenseStac.getProfil() + "</PROF_CODE>\n" +
                    "      <VOZRAST>" + licenseStac.getAge() + "</VOZRAST>\n" +
                    "      <DATE_BEG>" + licenseStac.getDateBeg() + "</DATE_BEG>\n" +
                    "      <DATE_END>" + licenseStac.getDateEnd() + "</DATE_END>\n" +
                    "      <TIP_STAC>" + licenseStac.getStacType() + "</TIP_STAC>\n" +
                    "      <OPLAT>" + licenseStac.getPayType() + "</OPLAT>\n" +
                    "    </License>\n");
        }
        builder.append("  </Stacionar>\n");
        builder.append("  <Policlinic>\n");
        for(LicensePol licensePol : licensePolList){
            builder.append(
                    "    <License>\n" +
                            "      <MCOD>" + licensePol.getMcod() + "</MCOD>\n" +
                            "      <SPEZ_CODE>" + licensePol.getSpez() + "</SPEZ_CODE>\n" +
                            "      <KATEGOR>" + licensePol.getCategory() + "</KATEGOR>\n" +
                            "      <VOZRAST>" + licensePol.getAge() + "</VOZRAST>\n" +
                            "      <DATE_BEG>" + licensePol.getDateBeg() + "</DATE_BEG>\n" +
                            "      <DATE_END>" + licensePol.getDateEnd() + "</DATE_END>\n" +
                            "    </License>\n");
        }
        builder.append("  </Policlinic>\n");
        builder.append("</Root>\n");
        return builder.toString();
    }

    // Получение информации по лицензиям для последующего вывода на странице лицензий стационара для одной ЛПУ
    public List<LicenseStacInfo> getLicenseStacInfosByMcod(Integer mcod) {
        List<LicenseStacInfo> licenseStacInfos = new ArrayList<>();
        List<LicenseStac> licenseStacList = licenseDao.getLicenseStacList(mcod);
        Map<String, String> ageMap = licenseDao.getAgeMap();
        Map<Integer,String> profMap = licenseDao.getProfilMap();
        Map<Integer,String> stacTypeMap = licenseDao.getStacTypeMap();
        Map<Integer,String> payTypeMap = licenseDao.getPayTypeMap();
        for (LicenseStac entity : licenseStacList){
            LicenseStacInfo lsi = new LicenseStacInfo();
            lsi.setId(entity.getId());
            lsi.setMcod(entity.getMcod());
            lsi.setProfil(profMap.get(entity.getProfil()));
            lsi.setAge(ageMap.get(entity.getAge()));
            lsi.setStacType(stacTypeMap.get(entity.getStacType()));
            lsi.setPayType(payTypeMap.get(entity.getPayType()));
            lsi.setDateBeg(entity.getDateBeg().format(DateTimeFormatter.ofPattern("dd.MM.uuuu")));
            lsi.setDateEnd(entity.getDateEnd().format(DateTimeFormatter.ofPattern("dd.MM.uuuu")));
            licenseStacInfos.add(lsi);
        }

        return licenseStacInfos;
    }

    //Генерация формы для новой лицензии стационара
    public LicenseStacForm getNewLicenseStacForm() {
        LicenseStacForm form = new LicenseStacForm();
        form.setStacTypeNames(licenseDao.getStacTypeMap());
        //Для формы нужен словарь профилей отсортированный по значениям,
        // чтобы проще было найти нужный профиль МП в выпадающем списке
        form.setProfilNames(licenseDao.getProfilMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new)));
        form.setAgeNames(licenseDao.getAgeMap());
        form.setPayTypeNames(licenseDao.getPayTypeMap());

        return form;
    }

    //Сохранение новой лицензии стационара
    public String saveNewLicenseStac (LicenseStacForm licenseStacForm) {
        licenseDao.save(new LicenseStac(licenseStacForm));
        logger.info("Добавлена лицензия стационара. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        logger.info(licenseStacForm.toString());
        return "Лицензия добавлена";
    }

    //Генерация формы для редактирования лицензии стационара
    //(форма предзаполняется данными из базы)
    public LicenseStacForm getEditLicenseStacForm(Integer id) {
        LicenseStacForm form = new LicenseStacForm();
        form.setStacTypeNames(licenseDao.getStacTypeMap());
        //Для формы нужен словарь профилей отсортированный по значениям,
        // чтобы проще было найти нужный профиль МП в выпадающем списке
        form.setProfilNames(licenseDao.getProfilMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new)));
        form.setAgeNames(licenseDao.getAgeMap());
        form.setPayTypeNames(licenseDao.getPayTypeMap());
        LicenseStac entity = licenseDao.getLicenseStacById(id);
        form.setMcod(entity.getMcod());
        form.setStacType(entity.getStacType());
        form.setProfil(entity.getProfil());
        form.setAge(entity.getAge());
        form.setPayType(entity.getPayType());
        form.setDateBeg(entity.getDateBeg().toString());
        form.setDateEnd(entity.getDateEnd().toString());
        return form;
    }

    // Сохранение изменений лицензии стационара
    public String saveEditLicenseStac(Integer id, LicenseStacForm form) {
        LicenseStac entity = licenseDao.getLicenseStacById(id);
        logger.info("Изменение лицензии стационара. " + entity.toString());
        entity.setMcod(form.getMcod());
        entity.setStacType(form.getStacType());
        entity.setProfil(form.getProfil());
        entity.setAge(form.getAge());
        entity.setPayType(form.getPayType());
        entity.setDateBeg(LocalDate.parse(form.getDateBeg()));
        entity.setDateEnd(LocalDate.parse(form.getDateEnd()));
        licenseDao.save(entity);
        logger.info("Изменена лицензия стационара. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        logger.info(entity.toString());
        return "Изменения сохранены";
    }

    // Удаление лицензии стационара
    public String deleteLicenseStac(Integer id) {
        LicenseStac entity = licenseDao.getLicenseStacById(id);
        logger.info("Удаление лицензии стационара. " + entity.toString());
        licenseDao.deleteLicenseStac(id);
        logger.info("Удалена лицензия стационара. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Лицензия удалена";
    }

    // Получение информации по лицензиям для последующего вывода на странице лицензий поликлиники для одной ЛПУ
    public List<LicensePolInfo> getLicensePolInfosByMcod(Integer mcod) {
        List<LicensePolInfo> licensePolInfos = new ArrayList<>();
        List<LicensePol> licensePolList = licenseDao.getLicensePolList(mcod);
        Map<String, String> ageMap = licenseDao.getAgeMap();
        Map<String, String> spezMap = licenseDao.getSpezMap();
        Map<String, String> categoryMap = licenseDao.getCategoryMap();
        for (LicensePol entity : licensePolList){
            LicensePolInfo lpi = new LicensePolInfo();
            lpi.setId(entity.getId());
            lpi.setMcod(entity.getMcod());
            lpi.setSpez(spezMap.get(entity.getSpez()));
            lpi.setAge(ageMap.get(entity.getAge()));
            lpi.setCategory(categoryMap.get(entity.getCategory()));
            lpi.setDateBeg(entity.getDateBeg().format(DateTimeFormatter.ofPattern("dd.MM.uuuu")));
            lpi.setDateEnd(entity.getDateEnd().format(DateTimeFormatter.ofPattern("dd.MM.uuuu")));
            licensePolInfos.add(lpi);
        }

        return licensePolInfos;
    }

    //Генерация формы для создания новой лицензии поликлиники
    //(форма предзаполняется вариантами значений полей, таких как возраст, специальность и категория)
    public LicensePolForm getNewLicensePolForm() {
        LicensePolForm form = new LicensePolForm();
        //Для формы нужен словарь специальностей отсортированный по значениям,
        // чтобы проще было найти нужную специальность в выпадающем списке
        form.setSpezNames(licenseDao.getSpezMap()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new)));
        form.setAgeNames(licenseDao.getAgeMap());
        form.setCategoryNames(licenseDao.getCategoryMap());
        return form;
    }

    //Сохранение новой лицензии поликлиники
    public String saveNewLicensePol (LicensePolForm licensePolForm) {
        licenseDao.save(new LicensePol(licensePolForm));
        logger.info("Добавлена лицензия поликлиники. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        logger.info(licensePolForm.toString());
        return "Лицензия добавлена";
    }

    //Генерация формы для редактирования лицензии поликлиники
    //(форма предзаполняется данными из базы)
    public LicensePolForm getEditLicensePolForm(Integer id) {
        LicensePolForm form = new LicensePolForm();
        //Для формы нужен словарь специальностей отсортированный по значениям,
        // чтобы проще было найти нужную специальность в выпадающем списке
        form.setSpezNames(licenseDao.getSpezMap()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new)));
        form.setAgeNames(licenseDao.getAgeMap());
        form.setCategoryNames(licenseDao.getCategoryMap());
        LicensePol entity = licenseDao.getLicensePolById(id);
        form.setMcod(entity.getMcod());
        form.setCategory(entity.getCategory());
        form.setSpez(entity.getSpez());
        form.setAge(entity.getAge());
        form.setDateBeg(entity.getDateBeg().toString());
        form.setDateEnd(entity.getDateEnd().toString());
        return form;
    }

    //Сохранение изменений лицензии поликлиники
    public String saveEditLicensePol(Integer id, LicensePolForm form) {
        LicensePol entity = licenseDao.getLicensePolById(id);
        logger.info("Изменение лицензии поликлиники. " + entity.toString());
        entity.setMcod(form.getMcod());
        entity.setCategory(form.getCategory());
        entity.setSpez(form.getSpez());
        entity.setAge(form.getAge());
        entity.setDateBeg(LocalDate.parse(form.getDateBeg()));
        entity.setDateEnd(LocalDate.parse(form.getDateEnd()));
        licenseDao.save(entity);
        logger.info("Изменена лицензия поликлиники. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        logger.info(entity.toString());
        return "Изменения сохранены";
    }

    // Удаление лицензии поликлиники
    public String deleteLicensePol(Integer id) {
        LicensePol entity = licenseDao.getLicensePolById(id);
        logger.info("Удаление лицензии поликлиники. " + entity.toString());
        licenseDao.deleteLicensePol(id);
        logger.info("Удалена лицензия поликлиники. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Лицензия удалена";
    }

    //Публикация справочника лицензий на сайт предполагает помещение файла справочника
    // и архива zip со справочником по пути заданному в конфигурационном файле:
    public String publishLicences() throws IOException {
        String fileName = "Licences.xml";
        String zipName = "Licences.zip";
        //Берем путь для сохранения их конфигов
        String path = env.getProperty("puths.publication_path");
        String encoding = "windows-1251";
        //Создаем файл
        Writer output = new OutputStreamWriter(new FileOutputStream(path + fileName), encoding);
        //Получаем справочник в виде строки и пишем ее в файл
        output.write(getFileLicences());
        output.flush();
        output.close();
        // Теперь добавим архив:
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + zipName));
            FileInputStream fis= new FileInputStream(path + fileName);)
        {
            ZipEntry entry1=new ZipEntry(fileName);
            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // добавляем содержимое к архиву
            zout.write(buffer);
            // закрываем
            zout.closeEntry();
        }
        logger.info("Справочник лицензий опубликован на сайте. Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return "Опубликовано по пути " + path;
    }
}
