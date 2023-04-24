package org.ktfoms.med.service;

import org.ktfoms.med.dao.FapDao;
import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FapService {
    private final FapDao fapDao;

    public FapService(FapDao fapDao){
        this.fapDao = fapDao;
    }

    public Fap getFapById(Integer id){
        return fapDao.getById(id);
    }

    public FapDto getFapDtoById(int id){
        return fapDao.getFapDtoById(id);
    }

    public FapFin getFapFinById(Integer id){
        return fapDao.getFapFinById(id);
    }

    public List<FapFinDto> getFapFinDtoList(){
        return fapDao.getFapFinDtoList();
    }

}
