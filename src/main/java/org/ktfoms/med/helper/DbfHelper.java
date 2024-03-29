package org.ktfoms.med.helper;


import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import org.ktfoms.med.entity.Fys;
import org.ktfoms.med.entity.Price;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class DbfHelper {

    //метод для формирования справочника FYS в формате DBF
    public static ByteArrayInputStream createFysDbf(List<Fys> fysList)  {
        OutputStream fos = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
            // Определение полей файла DBF
            DBFField[] fields = new DBFField[27];
            {fields[0] = new DBFField();
            fields[0].setName("KOD_SP");
            fields[0].setType(DBFDataType.CHARACTER);
            fields[0].setLength(3);

            fields[1] = new DBFField();
            fields[1].setName("NAME_YSL");
            fields[1].setType(DBFDataType.CHARACTER);
            fields[1].setLength(254);

            fields[2] = new DBFField();
            fields[2].setName("KOD_USL_MZ");
            fields[2].setType(DBFDataType.CHARACTER);
            fields[2].setLength(16);

            fields[3] = new DBFField();
            fields[3].setName("RZ");
            fields[3].setType(DBFDataType.CHARACTER);
            fields[3].setLength(1);

            fields[4] = new DBFField();
            fields[4].setName("TYP");
            fields[4].setType(DBFDataType.CHARACTER);
            fields[4].setLength(3);

            fields[5] = new DBFField();
            fields[5].setName("KLAS");
            fields[5].setType(DBFDataType.CHARACTER);
            fields[5].setLength(3);

            fields[6] = new DBFField();
            fields[6].setName("VID");
            fields[6].setType(DBFDataType.CHARACTER);
            fields[6].setLength(3);

            fields[7] = new DBFField();
            fields[7].setName("PVID");
            fields[7].setType(DBFDataType.CHARACTER);
            fields[7].setLength(3);

            fields[8] = new DBFField();
            fields[8].setName("OMS");
            fields[8].setType(DBFDataType.CHARACTER);
            fields[8].setLength(1);

            fields[9] = new DBFField();
            fields[9].setName("POS");
            fields[9].setType(DBFDataType.CHARACTER);
            fields[9].setLength(1);

            fields[10] = new DBFField();
            fields[10].setName("MKR");
            fields[10].setType(DBFDataType.CHARACTER);
            fields[10].setLength(5);

            fields[11] = new DBFField();
            fields[11].setName("V_uet");
            fields[11].setType(DBFDataType.NUMERIC);
            fields[11].setLength(7);
            fields[11].setDecimalCount(2);

            fields[12] = new DBFField();
            fields[12].setName("D_uet");
            fields[12].setType(DBFDataType.NUMERIC);
            fields[12].setLength(7);
            fields[12].setDecimalCount(2);


            fields[13] = new DBFField();
            fields[13].setName("D1");
            fields[13].setType(DBFDataType.NUMERIC);
            fields[13].setLength(9);
            fields[13].setDecimalCount(2);


            fields[14] = new DBFField();
            fields[14].setName("V1");
            fields[14].setType(DBFDataType.NUMERIC);
            fields[14].setLength(9);
            fields[14].setDecimalCount(2);


            fields[15] = new DBFField();
            fields[15].setName("D1_uet");
            fields[15].setType(DBFDataType.NUMERIC);
            fields[15].setLength(9);
            fields[15].setDecimalCount(2);


            fields[16] = new DBFField();
            fields[16].setName("V1_uet");
            fields[16].setType(DBFDataType.NUMERIC);
            fields[16].setLength(9);
            fields[16].setDecimalCount(2);


            fields[17] = new DBFField();
            fields[17].setName("D2");
            fields[17].setType(DBFDataType.NUMERIC);
            fields[17].setLength(9);
            fields[17].setDecimalCount(2);


            fields[18] = new DBFField();
            fields[18].setName("V2");
            fields[18].setType(DBFDataType.NUMERIC);
            fields[18].setLength(9);
            fields[18].setDecimalCount(2);


            fields[19] = new DBFField();
            fields[19].setName("D2_uet");
            fields[19].setType(DBFDataType.NUMERIC);
            fields[19].setLength(9);
            fields[19].setDecimalCount(2);


            fields[20] = new DBFField();
            fields[20].setName("V2_uet");
            fields[20].setType(DBFDataType.NUMERIC);
            fields[20].setLength(9);
            fields[20].setDecimalCount(2);

            fields[21] = new DBFField();
            fields[21].setName("V021_D");
            fields[21].setType(DBFDataType.CHARACTER);
            fields[21].setLength(3);

            fields[22] = new DBFField();
            fields[22].setName("V021_V");
            fields[22].setType(DBFDataType.CHARACTER);
            fields[22].setLength(3);

            fields[23] = new DBFField();
            fields[23].setName("DIAG_N");
            fields[23].setType(DBFDataType.CHARACTER);
            fields[23].setLength(7);

            fields[24] = new DBFField();
            fields[24].setName("DIAG_K");
            fields[24].setType(DBFDataType.CHARACTER);
            fields[24].setLength(7);

            fields[25] = new DBFField();
            fields[25].setName("DIAG_DN");
            fields[25].setType(DBFDataType.CHARACTER);
            fields[25].setLength(254);

            fields[26] = new DBFField();
            fields[26].setName("DS_cat");
            fields[26].setType(DBFDataType.CHARACTER);
            fields[26].setLength(2);}
            DBFWriter writer = new DBFWriter(out);//экземпляр DBFWriter для записи DBF
            writer.setCharset(Charset.forName("Cp866")); // определим кодировку выходного файла

            // задать структуру таблицы
            writer.setFields(fields);
            // запись объектов:
            for(Fys fys:fysList){
                Object[] rowData = new Object[27];
                rowData[0] = fys.getKodSp();
                rowData[1] = fys.getNameYsl();
                rowData[2] = fys.getKodUslMz();
                rowData[3] = fys.getRz();
                rowData[4] = fys.getTyp();
                rowData[5] = fys.getKlas();
                rowData[6] = fys.getVid();
                rowData[7] = fys.getPvid();
                rowData[8] = fys.isOms() ? "+" : "-";
                rowData[9] = fys.isPos() ? "+" : "-";
                rowData[10] = fys.getMkr();
                rowData[11] = fys.getVUet();
                rowData[12] = fys.getDUet();

                rowData[13] = fys.getD1();
                rowData[14] = fys.getV1();
                rowData[15] = fys.getD1Uet();
                rowData[16] = fys.getV1Uet();
                rowData[17] = fys.getD2();
                rowData[18] = fys.getV2();
                rowData[19] = fys.getD2Uet();
                rowData[20] = fys.getV2Uet();

                rowData[21] = fys.getV021D();
                rowData[22] = fys.getV021V();
                rowData[23] = fys.getDiagN();
                rowData[24] = fys.getDiagK();
                rowData[25] = fys.getDiagDn();
                rowData[26] = fys.getDsCategory();
                writer.addRecord(rowData);
            }
            // вывод данных
            writer.write();
            byte[] byteArray = out.toByteArray();
            // для того, чтобы кодировка при открытии файла определялась автоматически,
            // прописываем в 29-й байт заголовка информацию о кодовой странице OEM866
            byteArray[29] = 0x26;
            return new ByteArrayInputStream(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ByteArrayInputStream createObrcDbf(List<Price> obrcList) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();){
            // Определение полей файла DBF
            DBFField[] fields = new DBFField[6];
            fields[0] = new DBFField();
            fields[0].setName("KOD_SP");
            fields[0].setType(DBFDataType.CHARACTER);
            fields[0].setLength(3);

            fields[1] = new DBFField();
            fields[1].setName("NAME _SP");
            fields[1].setType(DBFDataType.CHARACTER);
            fields[1].setLength(254);

            fields[2] = new DBFField();
            fields[2].setName("D1");
            fields[2].setType(DBFDataType.NUMERIC);
            fields[2].setLength(9);
            fields[2].setDecimalCount(2);

            fields[3] = new DBFField();
            fields[3].setName("V1");
            fields[3].setType(DBFDataType.NUMERIC);
            fields[3].setLength(9);
            fields[3].setDecimalCount(2);

            fields[4] = new DBFField();
            fields[4].setName("D2");
            fields[4].setType(DBFDataType.NUMERIC);
            fields[4].setLength(9);
            fields[4].setDecimalCount(2);


            fields[5] = new DBFField();
            fields[5].setName("V2");
            fields[5].setType(DBFDataType.NUMERIC);
            fields[5].setLength(9);
            fields[5].setDecimalCount(2);

            DBFWriter writer = new DBFWriter(out);  //экземпляр DBFWriter для записи DBF
            writer.setCharset(Charset.forName("Cp866")); // определим кодировку выходного файла
            writer.setFields(fields);  // задать структуру таблицы
            // запись объектов:
            for(Price price:obrcList){
                Object[] rowData = new Object[6];
                rowData[0] = price.getKod();
                rowData[1] = price.getSpez();
                rowData[2] = price.getD1();
                rowData[3] = price.getV1();
                rowData[4] = price.getD2();
                rowData[5] = price.getV2();
                writer.addRecord(rowData);
            }
            // вывод данных
            writer.write();
            byte[] byteArray = out.toByteArray();
            // для того, чтобы кодировка при открытии файла определялась автоматически,
            // прописываем в 29-й байт заголовка информацию о кодовой странице OEM866
            byteArray[29] = 0x26;
            return new ByteArrayInputStream(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
