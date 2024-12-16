package org.ktfoms.med.helper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.poi.EmptyFileException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

//В случае .xlsx использовать:
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//В случае .xls использовать:


import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.dto.FundingNormaDto;
import org.ktfoms.med.dto.LpuF003Dto;
import org.ktfoms.med.entity.FundingNormaSmp;
import org.ktfoms.med.entity.Fys;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.entity.Price;

public class ExcelHelper {
//    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//    static String SHEET = "sheet1";

    public static ByteArrayInputStream fapFinDtosToExcel(List<FapFinDto> fapFinDtoList, List<Lpu> lpuEntityList) {

        String[] headers = { "№ п/п", "Наименование ЦРБ", "Наименование ФАП"
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."
                , "Годовая сумма финанасового обеспечения, руб.", "К-т укомплектованности"
                , "Сумма финансового обеспечения на месяц, Астрамед, руб.", "Сумма финансового обеспечения на месяц, Капитал, руб."

        };

        String[] month = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

        Map<Integer, Lpu> lpuByMkod = lpuEntityList.stream().collect(Collectors.toMap(Lpu::getMkod, l -> l));

        TreeMap<Integer, List<FapFinDto>> fapFinDtosByMkod = new TreeMap<Integer, List<FapFinDto>>();
        fapFinDtoList.forEach(ffd -> {
                    if (fapFinDtosByMkod.containsKey(ffd.getMkod())){
                        fapFinDtosByMkod.get(ffd.getMkod()).add(ffd);
                    }else {
                        fapFinDtosByMkod.put(ffd.getMkod(), new ArrayList<>());
                        fapFinDtosByMkod.get(ffd.getMkod()).add(ffd);
                    }
                });

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("sheet1");
            sheet.setColumnWidth(0, 1000);
            sheet.setColumnWidth(1, 10000);
            sheet.setColumnWidth(2, 10000);

            XSSFCellStyle styleHeader = (XSSFCellStyle) workbook.createCellStyle();
            styleHeader.setBorderBottom(BorderStyle.MEDIUM);
            styleHeader.setBorderLeft(BorderStyle.MEDIUM);
            styleHeader.setBorderRight(BorderStyle.MEDIUM);
            styleHeader.setBorderTop(BorderStyle.MEDIUM);
            styleHeader.setWrapText(true);
//            styleHeader.setShrinkToFit(true);
            XSSFCellStyle styleBody = (XSSFCellStyle) workbook.createCellStyle();
            styleBody.setBorderBottom(BorderStyle.THIN);
            styleBody.setBorderLeft(BorderStyle.THIN);
            styleBody.setBorderRight(BorderStyle.THIN);
            styleBody.setBorderTop(BorderStyle.THIN);
            styleBody.setWrapText(true);
//            styleBody.setShrinkToFit(true);


            Row nameRow = sheet.createRow(0);

            nameRow.createCell(1).setCellValue("Справочник тарифов на финансовое обеспечение ФАПов на дату " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("d.MM.uuuu")));


//            for (int col = 1; col <= MONTH.length; col++) {
//
//            }

            Row headerMonthRow = sheet.createRow(1);
            for (int col = 1; col <= month.length; col++) {
                sheet.addMergedRegion(new CellRangeAddress(1, 1, col*4-1, col*4+2));
                Cell cellMonth = headerMonthRow.createCell(col*4-1);
                cellMonth.setCellValue(month[col-1]);
                cellMonth.setCellStyle(styleHeader);
                headerMonthRow.createCell(col*4).setCellStyle(styleHeader);
                headerMonthRow.createCell(col*4+1).setCellStyle(styleHeader);
                headerMonthRow.createCell(col*4+2).setCellStyle(styleHeader);
            }


            // Header
            Row headerRow = sheet.createRow(2);

            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
                cell.setCellStyle(styleHeader);
            }


// Заполняем заглавную строку для ЛПУ
            int rowIdx = 3;
            int counter = 1;
            for (Map.Entry<Integer, List<FapFinDto>> entry : fapFinDtosByMkod.entrySet()) {
                List<FapFinDto> fapFinDtos = entry.getValue();
                String lpuName = lpuByMkod.get(entry.getKey()).getMNameF();

                Row row = sheet.createRow(rowIdx++);
                Cell cell = row.createCell(0);
                cell.setCellStyle(styleBody);

                cell = row.createCell(1);
                cell.setCellValue(lpuName);
                cell.setCellStyle(styleBody);

//Заполняем строки по фап
                for (FapFinDto ffd : fapFinDtos) {
                    Row subrow = sheet.createRow(rowIdx++);

                    cell = subrow.createCell(0);
                    cell.setCellValue(counter++);
                    cell.setCellStyle(styleBody);

                    subrow.createCell(1).setCellStyle(styleBody);

                    cell = subrow.createCell(2);
                    cell.setCellValue(ffd.getNamePodr());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(3);
                    cell.setCellValue(ffd.getGFin1());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(4);
                    cell.setCellValue(ffd.getKYkomp1());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(5);
                    cell.setCellValue(ffd.getSummAstra1());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(6);
                    cell.setCellValue(ffd.getSummKapit1());
                    cell.setCellStyle(styleBody);

                    //block 2
                    cell = subrow.createCell(7);
                    cell.setCellValue(ffd.getGFin2());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(8);
                    cell.setCellValue(ffd.getKYkomp2());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(9);
                    cell.setCellValue(ffd.getSummAstra2());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(10);
                    cell.setCellValue(ffd.getSummKapit2());
                    cell.setCellStyle(styleBody);

//block 3
                    cell = subrow.createCell(11);
                    cell.setCellValue(ffd.getGFin3());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(12);
                    cell.setCellValue(ffd.getKYkomp3());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(13);
                    cell.setCellValue(ffd.getSummAstra3());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(14);
                    cell.setCellValue(ffd.getSummKapit3());
                    cell.setCellStyle(styleBody);


//block 4
                    cell = subrow.createCell(15);
                    cell.setCellValue(ffd.getGFin4());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(16);
                    cell.setCellValue(ffd.getKYkomp4());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(17);
                    cell.setCellValue(ffd.getSummAstra4());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(18);
                    cell.setCellValue(ffd.getSummKapit4());
                    cell.setCellStyle(styleBody);


//block 5
                    cell = subrow.createCell(19);
                    cell.setCellValue(ffd.getGFin5());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(20);
                    cell.setCellValue(ffd.getKYkomp5());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(21);
                    cell.setCellValue(ffd.getSummAstra5());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(22);
                    cell.setCellValue(ffd.getSummKapit5());
                    cell.setCellStyle(styleBody);


//block 6
                    cell = subrow.createCell(23);
                    cell.setCellValue(ffd.getGFin6());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(24);
                    cell.setCellValue(ffd.getKYkomp6());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(25);
                    cell.setCellValue(ffd.getSummAstra6());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(26);
                    cell.setCellValue(ffd.getSummKapit6());
                    cell.setCellStyle(styleBody);


//block 7
                    cell = subrow.createCell(27);
                    cell.setCellValue(ffd.getGFin7());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(28);
                    cell.setCellValue(ffd.getKYkomp7());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(29);
                    cell.setCellValue(ffd.getSummAstra7());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(30);
                    cell.setCellValue(ffd.getSummKapit7());
                    cell.setCellStyle(styleBody);


//block 8
                    cell = subrow.createCell(31);
                    cell.setCellValue(ffd.getGFin8());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(32);
                    cell.setCellValue(ffd.getKYkomp8());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(33);
                    cell.setCellValue(ffd.getSummAstra8());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(34);
                    cell.setCellValue(ffd.getSummKapit8());
                    cell.setCellStyle(styleBody);


//block 9
                    cell = subrow.createCell(35);
                    cell.setCellValue(ffd.getGFin9());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(36);
                    cell.setCellValue(ffd.getKYkomp9());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(37);
                    cell.setCellValue(ffd.getSummAstra9());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(38);
                    cell.setCellValue(ffd.getSummKapit9());
                    cell.setCellStyle(styleBody);


//block 10
                    cell = subrow.createCell(39);
                    cell.setCellValue(ffd.getGFin10());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(40);
                    cell.setCellValue(ffd.getKYkomp10());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(41);
                    cell.setCellValue(ffd.getSummAstra10());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(42);
                    cell.setCellValue(ffd.getSummKapit10());
                    cell.setCellStyle(styleBody);


//block 11
                    cell = subrow.createCell(43);
                    cell.setCellValue(ffd.getGFin11());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(44);
                    cell.setCellValue(ffd.getKYkomp11());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(45);
                    cell.setCellValue(ffd.getSummAstra11());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(46);
                    cell.setCellValue(ffd.getSummKapit11());
                    cell.setCellStyle(styleBody);


//block 12
                    cell = subrow.createCell(47);
                    cell.setCellValue(ffd.getGFin12());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(48);
                    cell.setCellValue(ffd.getKYkomp12());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(49);
                    cell.setCellValue(ffd.getSummAstra12());
                    cell.setCellStyle(styleBody);

                    cell = subrow.createCell(50);
                    cell.setCellValue(ffd.getSummKapit12());
                    cell.setCellStyle(styleBody);


                }

                Row totalrow = sheet.createRow(rowIdx++);

                totalrow.createCell(0).setCellStyle(styleBody);;

                cell = totalrow.createCell(1);
                cell.setCellValue("Итого по " + lpuName);
                cell.setCellStyle(styleBody);

                cell = totalrow.createCell(2);
                cell.setCellValue(fapFinDtos.size());
                cell.setCellStyle(styleBody);

// Заполняем суммы по ЛПУ

                {
                cell = totalrow.createCell(3);
                cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin1).mapToDouble(Double::doubleValue).sum());
                cell.setCellStyle(styleBody);

                totalrow.createCell(4).setCellStyle(styleBody);;

                cell = totalrow.createCell(5);
                cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra1).mapToDouble(Double::doubleValue).sum());
                cell.setCellStyle(styleBody);

                cell = totalrow.createCell(6);
                cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit1).mapToDouble(Double::doubleValue).sum());
                cell.setCellStyle(styleBody);

//Block 2
                    cell = totalrow.createCell(7);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin2).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(8).setCellStyle(styleBody);;

                    cell = totalrow.createCell(9);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra2).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(10);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit2).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 3
                    cell = totalrow.createCell(11);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin3).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(12).setCellStyle(styleBody);;

                    cell = totalrow.createCell(13);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra3).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(14);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit3).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 4
                    cell = totalrow.createCell(15);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin4).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(16).setCellStyle(styleBody);;

                    cell = totalrow.createCell(17);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra4).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(18);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit4).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 5
                    cell = totalrow.createCell(19);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin5).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(20).setCellStyle(styleBody);;

                    cell = totalrow.createCell(21);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra5).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(22);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit5).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 6
                    cell = totalrow.createCell(23);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin6).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(24).setCellStyle(styleBody);;

                    cell = totalrow.createCell(25);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra6).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(26);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit6).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 7
                    cell = totalrow.createCell(27);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin7).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(28).setCellStyle(styleBody);;

                    cell = totalrow.createCell(29);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra7).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(30);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit7).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 8
                    cell = totalrow.createCell(31);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin8).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(32).setCellStyle(styleBody);;

                    cell = totalrow.createCell(33);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra8).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(34);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit8).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 9
                    cell = totalrow.createCell(35);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin9).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(36).setCellStyle(styleBody);;

                    cell = totalrow.createCell(37);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra9).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(38);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit9).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 10
                    cell = totalrow.createCell(39);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin10).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(40).setCellStyle(styleBody);;

                    cell = totalrow.createCell(41);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra10).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(42);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit10).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 11
                    cell = totalrow.createCell(43);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin11).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(44).setCellStyle(styleBody);;

                    cell = totalrow.createCell(45);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra11).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(46);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit11).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

//Block 12
                    cell = totalrow.createCell(47);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getGFin12).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    totalrow.createCell(48).setCellStyle(styleBody);;

                    cell = totalrow.createCell(49);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummAstra12).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);

                    cell = totalrow.createCell(50);
                    cell.setCellValue(fapFinDtos.stream().map(FapFinDto::getSummKapit12).mapToDouble(Double::doubleValue).sum());
                    cell.setCellStyle(styleBody);


                }

            }


            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static String valueAsString (Cell cell) {
        if (cell == null) {return null;}
        if (cell.getCellType() == null) {return null;}
        CellType cellType = cell.getCellType();
        //перебираем возможные типы ячеек
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return null;
        }

    }

    public static Integer valueAsInteger (Cell cell) {
        if (cell == null) {return null;}
        if (cell.getCellType() == null) {return null;}
        CellType cellType = cell.getCellType();
        //перебираем возможные типы ячеек
        switch (cellType) {
            case STRING:
                return Integer.parseInt(cell.getStringCellValue().trim());
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            default:
                return null;
        }

    }

    public static Long valueAsLong (Cell cell) {
        if (cell == null) {return null;}
        if (cell.getCellType() == null) {return null;}
        CellType cellType = cell.getCellType();
        //перебираем возможные типы ячеек
        switch (cellType) {
            case STRING:
                return Long.parseLong(cell.getStringCellValue().trim());
            case NUMERIC:
                return (long) cell.getNumericCellValue();
            default:
                return null;
        }
    }

    public static Double valueAsNum (Cell cell) {
        if (cell == null) {return null;}
        if (cell.getCellType() == null) {return null;}
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC, FORMULA:
                return cell.getNumericCellValue();
            default:
                return Double.valueOf(0);
        }
    }

    //рабочая версия метода, исправленная для нового (от янв 2024) формата справочника FYS
    public static List<Fys> parseFysXls(InputStream in, boolean parsePrice) throws Exception {
        List<String> cyrillicChars = List.of("А", "а", "В", "в","Б", "б");
        List<Fys> fysList = new ArrayList<>();
        HSSFWorkbook workBook = null;
        try {
            workBook = new HSSFWorkbook(in);
        } catch (NotOLE2FileException e) {
            e.printStackTrace();
            throw new Exception("Файл невыбран или неверный формат файла");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //разбираем первый лист входного файла на объектную модель
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        //пропускаем строку с заголовком таблицы
        it.next();
        //проходим по всему листу
        int rowCounter = 1;
        while (it.hasNext()) {
            Row row = it.next();
            rowCounter++;
            Fys fys = new Fys();
            if (row.getCell(0) == null) {break;}
            if (cyrillicChars.contains(ExcelHelper.valueAsString(row.getCell(2)).substring(0,1))
                    | cyrillicChars.contains(ExcelHelper.valueAsString(row.getCell(3)).substring(0,1))) {
                throw new Exception("Файл содержит кириллические символы в полях KOD_USL_MZ или RZ в строке " + rowCounter);}
            fys.setKodSp(ExcelHelper.valueAsString(row.getCell(0)));
            fys.setNameYsl(ExcelHelper.valueAsString(row.getCell(1)));
            fys.setKodUslMz(ExcelHelper.valueAsString(row.getCell(2)));
            fys.setRz(ExcelHelper.valueAsString(row.getCell(3)));
            fys.setTyp(ExcelHelper.valueAsString(row.getCell(4)));
            fys.setKlas(ExcelHelper.valueAsString(row.getCell(5)));
            fys.setVid(ExcelHelper.valueAsString(row.getCell(6)));
            fys.setPvid(ExcelHelper.valueAsString(row.getCell(7)));
            fys.setOms(ExcelHelper.valueAsString(row.getCell(8)).equals("+"));
            fys.setPos(ExcelHelper.valueAsString(row.getCell(9)).equals("+"));
            fys.setMkr(ExcelHelper.valueAsString(row.getCell(10)));
            fys.setVUet(ExcelHelper.valueAsNum(row.getCell(11)));
            if (ExcelHelper.valueAsNum(row.getCell(12)) > 0) {
                fys.setDUet(ExcelHelper.valueAsNum(row.getCell(12)));
            } else {
                fys.setDUet(ExcelHelper.valueAsNum(row.getCell(11)));
            }

            if (parsePrice){
                fys.setD1(ExcelHelper.valueAsNum(row.getCell(13)));
                fys.setV1(ExcelHelper.valueAsNum(row.getCell(14)));
                fys.setD1Uet(ExcelHelper.valueAsNum(row.getCell(15)));
                fys.setV1Uet(ExcelHelper.valueAsNum(row.getCell(16)));
                fys.setD2(ExcelHelper.valueAsNum(row.getCell(17)));
                fys.setV2(ExcelHelper.valueAsNum(row.getCell(18)));
                fys.setD2Uet(ExcelHelper.valueAsNum(row.getCell(19)));
                fys.setV2Uet(ExcelHelper.valueAsNum(row.getCell(20)));
            } else {
                fys.setD1((double) 0);
                fys.setV1((double) 0);
                fys.setD1Uet((double) 0);
                fys.setV1Uet((double) 0);
                fys.setD2((double) 0);
                fys.setV2((double) 0);
                fys.setD2Uet((double) 0);
                fys.setV2Uet((double) 0);
            }

            fys.setV021D(ExcelHelper.valueAsString(row.getCell(21)));
            fys.setV021V(ExcelHelper.valueAsString(row.getCell(22)));
            fys.setDiagN(ExcelHelper.valueAsString(row.getCell(23)));
            fys.setDiagK(ExcelHelper.valueAsString(row.getCell(24)));
            fys.setDiagDn(ExcelHelper.valueAsString(row.getCell(25)));
            fys.setDsCategory(ExcelHelper.valueAsString(row.getCell(26)));
            fys.setAnest(ExcelHelper.valueAsInteger(row.getCell(27)));
            fysList.add(fys);
        }
        return fysList;
    }

    public static List<Price> parsePriceXls (InputStream in) throws Exception {
        List<Price> priceList = new ArrayList<>();
        HSSFWorkbook workBook = null;
        try {
            workBook = new HSSFWorkbook(in);
        } catch (NotOLE2FileException e) {
            e.printStackTrace();
            throw new Exception("Файл невыбран или неверный формат файла");
        } catch (IOException e) {
            e.printStackTrace();
        }

            Sheet sheet = workBook.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();

            //пропускаем строку с заголовком таблицы
            it.next();

            //проходим по всему листу
            while (it.hasNext()) {
                Row row = it.next();
                if (row.getCell(0) == null) {
                    break;
                }
                Price p = new Price();
                p.setKod(ExcelHelper.valueAsString(row.getCell(0)));
                p.setSpez(ExcelHelper.valueAsString(row.getCell(1)));
                p.setMkr(ExcelHelper.valueAsString(row.getCell(2)));
                p.setD1(ExcelHelper.valueAsNum(row.getCell(3)));
                p.setV1(ExcelHelper.valueAsNum(row.getCell(4)));
                p.setD2(ExcelHelper.valueAsNum(row.getCell(5)));
                p.setV2(ExcelHelper.valueAsNum(row.getCell(6)));
                priceList.add(p);
            }
            return priceList;
        }

//Метод для конвертации справочника FYS в EXCEL
    public static ByteArrayInputStream fysEntityListToExcel(List<Fys> fysEntityList) {
        String[] headers = {"KOD_SP", "NAME_YSL", "KOD_USL_MZ", "RZ", "TYP", "KLAS",
                "VID", "PVID", "OMS", "POS", "MKR", "V_uet", "D_uet", "D1", "V1", "D1_uet", "V1_uet",
                "D2", "V2", "D2_uet", "V2_uet", "V021_D", "V021_V", "DIAG_N", "DIAG_K", "DIAG_DN", "DS_category", "ANEST"
        };
        try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            //Создадим стиль ячейки со значением денежной суммы
            CellStyle numStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            numStyle.setDataFormat(format.getFormat("0.00"));

            Sheet sheet = workbook.createSheet("sheet1");
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
            }
            int rowIdx = 1;
            for (Fys fys : fysEntityList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(fys.getKodSp());
                row.createCell(1).setCellValue(fys.getNameYsl());
                row.createCell(2).setCellValue(fys.getKodUslMz());
                row.createCell(3).setCellValue(fys.getRz());
                row.createCell(4).setCellValue(fys.getTyp());
                row.createCell(5).setCellValue(fys.getKlas());
                row.createCell(6).setCellValue(fys.getVid());
                row.createCell(7).setCellValue(fys.getPvid());
                row.createCell(8).setCellValue(fys.isOms() ? "+" : "-");
                row.createCell(9).setCellValue(fys.isPos() ? "+" : "-");
                row.createCell(10).setCellValue(fys.getMkr());
                row.createCell(11).setCellValue(fys.getVUet());
                row.createCell(12).setCellValue(fys.getDUet());
                row.createCell(13).setCellValue(fys.getD1());
                row.createCell(14).setCellValue(fys.getV1());
                row.createCell(15).setCellValue(fys.getD1Uet());
                row.createCell(16).setCellValue(fys.getV1Uet());
                row.createCell(17).setCellValue(fys.getD2());
                row.createCell(18).setCellValue(fys.getV2());
                row.createCell(19).setCellValue(fys.getD2Uet());
                row.createCell(20).setCellValue(fys.getV2Uet());
                row.createCell(21).setCellValue(fys.getV021D());
                row.createCell(22).setCellValue(fys.getV021V());
                row.createCell(23).setCellValue(fys.getDiagN());
                row.createCell(24).setCellValue(fys.getDiagK());
                row.createCell(25).setCellValue(fys.getDiagDn());
                row.createCell(26).setCellValue(fys.getDsCategory());
                row.createCell(27).setCellValue((fys.getAnest() == null) ? "" : "1");
                row.getCell(11).setCellStyle(numStyle);
                row.getCell(12).setCellStyle(numStyle);
                row.getCell(13).setCellStyle(numStyle);
                row.getCell(14).setCellStyle(numStyle);
                row.getCell(15).setCellStyle(numStyle);
                row.getCell(16).setCellStyle(numStyle);
                row.getCell(17).setCellStyle(numStyle);
                row.getCell(18).setCellStyle(numStyle);
                row.getCell(19).setCellStyle(numStyle);
                row.getCell(20).setCellStyle(numStyle);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream FundingNormaDtoListToExcel(List<FundingNormaDto> fundingNormaDtoList) {
        String[] headers = {"M_NAMES", "OGRN", "D_N", "CHIS_2778", "CHIS_2779", "FIN_N"};
        try (Workbook workbook = new HSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("sheet1");
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
            }
            int rowIdx = 1;
            for (FundingNormaDto fnd : fundingNormaDtoList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(fnd.getMNameS());
                row.createCell(1).setCellValue(fnd.getOgrn().toString());
                row.createCell(2).setCellValue(fnd.getFormatedFundingDate());
                if (fnd.getQuantityInAstr() != null){row.createCell(3).setCellValue(fnd.getQuantityInAstr());}
                if (fnd.getQuantityInKap() != null){row.createCell(4).setCellValue(fnd.getQuantityInKap());}
                row.createCell(5).setCellValue(fnd.getNorma());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<FundingNormaSmp> parseFundingNormaSmpXlsx(InputStream in) throws Exception {
        List<FundingNormaSmp> fundingNormaSmpList = new ArrayList<>();
        XSSFWorkbook workBook = null;
        try {
            workBook = new XSSFWorkbook(in);
        } catch (EmptyFileException e) {
            e.printStackTrace();
            throw new Exception("Файл невыбран");
        } catch (NotOfficeXmlFileException e) {
            e.printStackTrace();
            throw new Exception("Неверный формат файла");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert workBook != null;
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();

        //пропускаем строку с заголовком таблицы
        it.next();

        //проходим по всему листу
        while (it.hasNext()) {
            Row row = it.next();
            if (row.getCell(0) == null) {
                break;
            }
            Double tarif = ExcelHelper.valueAsNum(row.getCell(2));
            if (tarif == null){continue;}
            if (tarif == 0){continue;}
            Integer mcod = ExcelHelper.valueAsInteger(row.getCell(5));
            Integer kolZlAstr = ExcelHelper.valueAsInteger(row.getCell(3));
            Integer kolZlKapit = ExcelHelper.valueAsInteger(row.getCell(4));

            LocalDate datebeg = LocalDate.of(
                    ExcelHelper.valueAsInteger(row.getCell(0)),
                    ExcelHelper.valueAsInteger(row.getCell(1)),
                    1
            );
            LocalDate dateend = LocalDate.of(
                    ExcelHelper.valueAsInteger(row.getCell(0)),
                    ExcelHelper.valueAsInteger(row.getCell(1)) + 1,
                    1
            ).minusDays(1);
            System.out.println(tarif);
            System.out.println(mcod);
            System.out.println(kolZlAstr);
            System.out.println(kolZlKapit);
            System.out.println(dateend);
            FundingNormaSmp fnpAstr = new FundingNormaSmp();
            fnpAstr.setUslOk(4);
            fnpAstr.setSmo(45001);
            fnpAstr.setMcod(mcod);
            fnpAstr.setKolZl(kolZlAstr);
            fnpAstr.setTarif(tarif);
            fnpAstr.setDatebeg(datebeg);
            fnpAstr.setDateend(dateend);
            fundingNormaSmpList.add(fnpAstr);

            FundingNormaSmp fnpKapit = new FundingNormaSmp();
            fnpKapit.setUslOk(4);
            fnpKapit.setSmo(45002);
            fnpKapit.setMcod(mcod);
            fnpKapit.setKolZl(kolZlKapit);
            fnpKapit.setTarif(tarif);
            fnpKapit.setDatebeg(datebeg);
            fnpKapit.setDateend(dateend);
            fundingNormaSmpList.add(fnpKapit);
        }
        return fundingNormaSmpList;
    }

    public static List<LpuF003Dto> parseLpuXlsx(InputStream in) throws Exception {
        List<LpuF003Dto> lpuList = new ArrayList<>();
        XSSFWorkbook workBook = null;
        try {
            workBook = new XSSFWorkbook(in);
        } catch (EmptyFileException e) {
            e.printStackTrace();
            throw new Exception("Файл невыбран");
        } catch (NotOfficeXmlFileException e) {
            e.printStackTrace();
            throw new Exception("Неверный формат файла");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert workBook != null;
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();

        //пропускаем строку с заголовком таблицы
        it.next();

        //проходим по всему листу
        while (it.hasNext()) {
            Row row = it.next();
            if (row.getCell(0) == null) {
                break;
            }
            LpuF003Dto dto = new LpuF003Dto();
            dto.setMcod(ExcelHelper.valueAsInteger(row.getCell(4))) ;
            dto.setMNameF(ExcelHelper.valueAsString(row.getCell(5)));
            dto.setMNameS(ExcelHelper.valueAsString(row.getCell(6)));
            dto.setLpuinn(ExcelHelper.valueAsLong(row.getCell(7)));
            dto.setKpp(ExcelHelper.valueAsInteger(row.getCell(8)));
            dto.setOgrn(ExcelHelper.valueAsLong(row.getCell(9)));
            lpuList.add(dto);
        }
        return lpuList;
    }
}