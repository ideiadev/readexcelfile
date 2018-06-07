/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Pais;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sessions.AbstractFacade;
import sessions.PaisFacade;
import util.ReadWriteExcelFile;

/**
 *
 * @author franciscomiguel30
 */
@ManagedBean
@RequestScoped
public class IndexBean implements Serializable{
    private static final long serialVersionUID = 1L;
    @EJB
    private PaisFacade paisFacade;
    private ReadWriteExcelFile excelFile;

    public IndexBean() {
        excelFile = new ReadWriteExcelFile();
    }
    public void init() {
        try {
            excelFile.readXLSFile("/home/franciscomiguel30/Documents/FranciscoMiguel12636/PX-2018/PX-2018-war/web/endereco.xls", 0, (AbstractFacade) paisFacade, new Pais());
        } catch (IOException ex) {
            Logger.getLogger(IndexBean.class.getName()).log(Level.SEVERE, null, ex);
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    }
    
    public void setExcelFile(ReadWriteExcelFile excelFile) {
        this.excelFile = excelFile;
    }

    public ReadWriteExcelFile getExcelFile() {
        return excelFile;
    }
    
    

}
