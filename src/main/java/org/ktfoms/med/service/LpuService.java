package org.ktfoms.med.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.form.EditFundingNormaForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LpuService {

    private final LpuDao lpuDao;

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
}
