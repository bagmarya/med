package org.ktfoms.med.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.ktfoms.med.dao.FapDao;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.dto.FapFinDto;
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
    public ByteArrayInputStream load(Integer year) {
        List<FapFinDto> fapFinDtos = fapDao.getFapFinDtoList(year);
        List<Lpu> lpuEntityList= lpuDao.getLpuEntityList();

        ByteArrayInputStream in = ExcelHelper.fapFinDtosToExcel(fapFinDtos, lpuEntityList);
        return in;
    }

}
