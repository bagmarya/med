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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    public String parseSpDepartmentXml() throws IOException {
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
    @Transactional
    public String parseSpDepartment() throws IOException {
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        try {
            File file = new File("dep.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            line = br.readLine();
            while((line = br.readLine()) != null){
//                line = line.replaceAll("([^\";]{1});([^\";]{1})", "$1,$2");
                line = line.replaceAll("true", "\"true\"");
                line = line.replaceAll("false", "\"false\"");
                line = line.replaceAll("([а-яёА-ЯЁ]{1});", "$1,");
                line = line.replaceAll(";([а-яёА-ЯЁ]{1})", ",$1");
                line = line.replaceAll("([\s]{1});", "$1,");
                line = line.replaceAll(";([\s]{1})", ",$1");
                String[] fields = line.split(";");
                for (int i = 0; i < fields.length; i++) {
                    if (!fields[i].isBlank()) {
                        fields[i] = fields[i].replaceAll("^\"|\"$", "");
                    }
                }
                DepartmentDto dto = new DepartmentDto();
                dto.setMoOid(fields[0]);
                dto.setDepartOid(fields[1]);
                dto.setDateBeg(fields[2]);
                dto.setDateMod(fields[3]);
                dto.setDateLiq(fields[4]);
                dto.setDepartName(fields[5]);
                dto.setDepartTypeCode(Integer.parseInt(fields[6]));
                dto.setDepartTypeName(fields[7]);
                dto.setDepartKindCode(Integer.parseInt(fields[8]));
                dto.setDepartKindName(fields[9]);
                dto.setRegion(fields[23].isBlank() ? null : Integer.parseInt(fields[23]));
                dto.setPrefixArea(fields[28]);
                dto.setAreaName(fields[29]);
                dto.setPrefixStreet(fields[30]);
                dto.setStreetName(fields[31]);
                dto.setAddressHouse(fields[32]);
                dto.setAddressBuilding(fields[33]);
                dto.setAddressStruct(fields[34]);
                departmentDtoList.add(dto);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(departmentDtoList.size() == 0){
            return "Загружаемый файл справочника пуст.";
        }
        departmentDao.clearDepartment();
        for (DepartmentDto dto: departmentDtoList) {
            if ( dto.getRegion() != null && dto.getRegion() == 45 || dto.getDepartOid() != null && dto.getDepartOid().contains("1.2.643.5.1.13.13.12.2.45")) {
                departmentDao.save(new Department(dto));
            }
        }
        return "Справочник структурных подразделений успешно загружен.";
    }
}
