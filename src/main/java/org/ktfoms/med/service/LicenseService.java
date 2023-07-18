package org.ktfoms.med.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.ktfoms.med.dao.LicenseDao;
import org.ktfoms.med.dto.ProfilDto;
import org.ktfoms.med.dto.SpV002Profil;
import org.ktfoms.med.entity.Profil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LicenseService {
    private final LicenseDao licenseDao;

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
}
