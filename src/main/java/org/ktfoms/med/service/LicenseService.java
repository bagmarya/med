package org.ktfoms.med.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.ktfoms.med.dao.LicenseDao;
import org.ktfoms.med.dto.LicensePolDto;
import org.ktfoms.med.dto.LicenseStacDto;
import org.ktfoms.med.dto.ProfilDto;
import org.ktfoms.med.dto.SpLicense;
import org.ktfoms.med.dto.SpV002Profil;
import org.ktfoms.med.entity.FundingNormaSmp;
import org.ktfoms.med.entity.LicensePol;
import org.ktfoms.med.entity.LicenseStac;
import org.ktfoms.med.entity.Profil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
}
