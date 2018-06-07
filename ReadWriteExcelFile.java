/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sessions.AbstractFacade;

/**
 *
 * @authors Vivaldo Cândido, Francisco Miguel, Domingos Fernando 
 * data_update : 07/06/2018
 * 
 */
public class ReadWriteExcelFile {

    private int linha = 0;
    private int coluna = 0;
    static String m[][];

    public void readXLSFile(String pathFile, int num_folha, AbstractFacade<Object> abstractFacade, Object obj) throws IOException {

        InputStream excelFileToRead = new FileInputStream(pathFile);
        HSSFWorkbook wb = new HSSFWorkbook(excelFileToRead);

        HSSFSheet sheet = wb.getSheetAt(num_folha);
        m = new String[sheet.getPhysicalNumberOfRows()][obj.getClass().getDeclaredFields().length];
        HSSFRow row;
        HSSFCell cell;
        Iterator rows = sheet.rowIterator();

        while (rows.hasNext()) {
            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            setColuna(0);
            while (cells.hasNext()) {
                cell = (HSSFCell) cells.next();
                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    m[getLinha()][getColuna()] = cell.getStringCellValue().trim();
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    m[getLinha()][getColuna()] = (int) cell.getNumericCellValue() + "";
                } else {
                    //U Can Handel Boolean, Formula, Errors
                }
                setColuna(getColuna() + 1);
            }

            setLinha(getLinha() + 1);
        }
        gravarModelos(abstractFacade, obj);
        setColuna(0);
        setLinha(0);
    }

    private void gravarModelos(AbstractFacade<Object> abstractFacade, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String pk = null;
        for (int i = 1; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (j == 0) {
                    pk = m[i][j];
                }
                try {
                    if (verifyAllDataTypeBefore(fields[j].getType())) {
                        fields[j].setAccessible(true);
                        if (fields[j].getType() == int.class || fields[j].getType() == Integer.class) {
                            fields[j].set(obj, Integer.parseInt(m[i][j].trim()));
                        } else if (fields[j].getType() == String.class) {
                            fields[j].set(obj, m[i][j]);
                        }else if (fields[j].getType() == Double.class) {
                         fields[j].set(obj, Double.parseDouble(m[i][j]));
                         } else if (fields[j].getType() == Date.class) {
                         fields[j].set(obj, new java.sql.Date(DataUtils.strToDate(m[i][j]).getTime()));
                         } else {
                            Constructor tmp = fields[j].getType().getConstructor(String.class);
                            Object instance = tmp.newInstance(m[i][j]);
                            fields[j].set(obj, instance);
                        }
                    }

                } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException | InstantiationException | InvocationTargetException ex) {
                    Logger.getLogger(ReadWriteExcelFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (pk != null) {
                if (abstractFacade.find(Integer.parseInt(pk)) == null) {
                    abstractFacade.create(obj);
                } else {
//                    abstractFacade.edit(obj);
                }
                pk = null;
            }
        }
    }

    public boolean verifyAllDataTypeBefore(Class tipo) {
        return tipo == String.class || tipo == Date.class 
               || tipo == Integer.class || tipo == Double.class;
    }

    public static String[][] getM() {
        return m;
    }

    public static void setM(String[][] m) {
        ReadWriteExcelFile.m = m;
    }

    /**
     * @return the linha
     */
    public int getLinha() {
        return linha;
    }

    /**
     * @param linha the linha to set
     */
    public void setLinha(int linha) {
        this.linha = linha;
    }

    /**
     * @return the coluna
     */
    public int getColuna() {
        return coluna;
    }

    /**
     * @param coluna the coluna to set
     */
    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

}
