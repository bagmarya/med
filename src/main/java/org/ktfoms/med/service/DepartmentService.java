package org.ktfoms.med.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.ktfoms.med.dao.DepartmentDao;
import org.ktfoms.med.dto.DepartmentDto;
import org.ktfoms.med.dto.SpDepartment;
import org.ktfoms.med.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentDao departmentDao;
    @Autowired
    private ResourceLoader resourceLoader;

    public DepartmentService(DepartmentDao departmentDao){
        this.departmentDao = departmentDao;
    }

    public List<Department> getFapDepartmentListByLpu(String lpu) {
        return departmentDao.getDepartmentListByLpu(lpu).stream()
                .filter(f -> f.getKodVidPodr() == 1166 || f.getKodVidPodr() == 1167)
                .filter(f -> f.getDateLiq() == null)
                .collect(Collectors.toList());
    }

    //Метод загружает справочник подразделений по Курганской области из общего минздравовского справочника подразделений
    @Transactional
    public String parseSpDepartment() throws IOException {
        ObjectMapper objectMapper = new XmlMapper();
//        System.out.println(Runtime.getRuntime().totalMemory());
//        System.out.println(Runtime.getRuntime().maxMemory());
//        System.out.println(Runtime.getRuntime().freeMemory());
        String s = resourceLoader.getResource("file:dep.xml").getContentAsString(StandardCharsets.UTF_8);
        SpDepartment spDepartment = objectMapper.readValue(s, SpDepartment.class);
        departmentDao.clearDepartment();
        for (DepartmentDto dto: spDepartment.getDepartments()) {
            if ( dto.getRegion() != null && dto.getRegion() == 45 || dto.getDepartOid() != null && dto.getDepartOid().contains("1.2.643.5.1.13.13.12.2.45")) {
                departmentDao.save(new Department(dto));
            }
        }
        spDepartment=null;
        s=null;
        return "Справочник структурных подразделений успешно загружен";
    }

}
