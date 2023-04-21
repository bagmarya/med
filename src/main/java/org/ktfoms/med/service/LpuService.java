package org.ktfoms.med.service;

import org.ktfoms.med.dao.LpuDao;
import org.ktfoms.med.entity.Lpu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LpuService {

    private final LpuDao lpuDao;

    public LpuService(LpuDao lpuDao){
        this.lpuDao = lpuDao;
    }

    public Lpu getLpuById(Integer id){
        return lpuDao.getById(id);
    }

}
