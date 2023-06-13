package org.ktfoms.med.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import org.ktfoms.med.dao.FapDao;
import org.ktfoms.med.dao.FysDao;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.dto.FundingNormaDto;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.Fys;
import org.ktfoms.med.entity.Lpu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ktfoms.med.helper.ExcelHelper;



@Service
public class ExcelService {

    @Autowired
    LpuDao lpuDao;
    @Autowired
    FapDao fapDao;
    @Autowired
    FysDao fysDao;
    public ByteArrayInputStream loadFinFapExcel(Integer year) {
        List<FapFinDto> fapFinDtos = fapDao.getFapFinDtoList(year);
        List<Lpu> lpuEntityList= lpuDao.getLpuEntityList();

        ByteArrayInputStream in = ExcelHelper.fapFinDtosToExcel(fapFinDtos, lpuEntityList);
        return in;
    }

    public ByteArrayInputStream loadFysExcel() {
        List<Fys> fsEntityList = fysDao.getFysEntityList();
        ByteArrayInputStream in = ExcelHelper.fysEntityListToExcel(fsEntityList);
        return in;
    }

    public InputStream loadNormPdExcel(LocalDate date) {
        List<FundingNormaDto> fundingNormaDtoList = lpuDao.getFundingNormaDtoListByDate(date);
        ByteArrayInputStream in = ExcelHelper.FundingNormaDtoListToExcel(fundingNormaDtoList);
        return in;
    }
}
