package org.ktfoms.med.service;

import org.hibernate.JDBCException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.exception.ConstraintViolationException;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.dto.FundingNormaSmpDto;
import org.ktfoms.med.dto.SpFundingNormaSmp;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.FundingNormaSmp;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.entity.Price;
import org.ktfoms.med.form.EditFundingNormaForm;
import org.ktfoms.med.helper.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class LpuService {

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
        LocalDate nextMonthDate = LocalDate.of(year+(month+1)/12, (month+1)%12, 01);
        return lpuDao.getFundingNormaEntityList(thisMonthDate, nextMonthDate);
    }

    public boolean isExistFundingNormaByDate(LocalDate date){
        return lpuDao.getFundingNormaDtoListByDate(date).size() > 0;
    }


    public FundingNorma getFundingNormaById(int id) {
        return lpuDao.getFundingNormaById(id);
    }

    @Transactional
    public void saveFundingNorma(int id, EditFundingNormaForm editFundingNormaForm) {
        FundingNorma fn = lpuDao.getFundingNormaById(id);
        if (editFundingNormaForm.getQuantityInAstr() != "") {
            fn.setQuantityInAstr(Integer.parseInt(editFundingNormaForm.getQuantityInAstr()));
        }
        if (editFundingNormaForm.getQuantityInKap() != "") {
            fn.setQuantityInKap(Integer.parseInt(editFundingNormaForm.getQuantityInKap()));
        }
        if (editFundingNormaForm.getNorma() != "") {
            fn.setNorma(Double.parseDouble(editFundingNormaForm.getNorma().replace(',', '.')));
        }
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
            return "Файл с нормативами ПФ СМП успешно импортирован.";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
