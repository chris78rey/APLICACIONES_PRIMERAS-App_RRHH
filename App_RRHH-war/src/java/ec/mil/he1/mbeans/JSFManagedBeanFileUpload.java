/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.sessions.SegUrlsFacade;
import he1.utilities.SesionSeguridades;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean
@ViewScoped
public class JSFManagedBeanFileUpload implements Serializable {

    @Resource(name = "he1_pool")
    private DataSource he1_pool;

    private XSSFSheet sheet;
    InputStream inputstream;

    /**
     * Creates a new instance of JSFManagedBeanFileUpload
     */
    public JSFManagedBeanFileUpload() {
    }
    @EJB
    private SesionSeguridades encryptFacade;
    @EJB
    private SegUrlsFacade segUrlsFacade;
    FileInputStream file;

    private String grabar = "0";
    private String nombre_archivo = "";

    public void handleFileUpload(FileUploadEvent event) {
        try {
            nombre_archivo = event.getFile().getFileName();
            FacesMessage msg = new FacesMessage("Succesful", nombre_archivo + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            inputstream = event.getFile().getInputstream();
            file = (FileInputStream) inputstream;
            grabar = "1";

        } catch (IOException ex) {
            Logger.getLogger(JSFManagedBeanFileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String convertjava() {
        grabar = "0";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = he1_pool.getConnection();

            String sql = "Insert into CODIGO_MIFIN(CEDULA, CODIGO, MES , MES_NUMERO, ANIO , DESCRIPCION, ARCHIVO)  Values   "
                    + " (?, ?, ?, ? , ?, ? , ? )";
            int columna = 0;

            /*PrepareStatement*/
            preparedStatement
                    = connection.prepareStatement(sql);

            //variables donde cargar los datos por cada celda
            String cc = "";
            String codigo = "";
            String mes = "";
            String mes_numero = "";
            String anio = "";
            String descripcion = "";

            file = (FileInputStream) inputstream;

            // Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheetAt(0);
            // Iterate through each rows from first sheet
            Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.rowIterator();
            //aca se barre todas las filas
            while (rowIterator.hasNext()) {

                org.apache.poi.ss.usermodel.Row row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                //aca se tiene las columnas por ello encero
                columna = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            cell.getBooleanCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            cell.getNumericCellValue();
                            break;
                        case Cell.CELL_TYPE_STRING:
                            cell.getStringCellValue();
                            break;
                    }

                    if (columna == 0) {
                        cc = cell.getStringCellValue();

                    } else if (columna == 1) {
                        codigo = cell.getStringCellValue();

                    } else if (columna == 2) {
                        mes = cell.getStringCellValue();

                    } else if (columna == 3) {
                        mes_numero = cell.getStringCellValue();

                    } else if (columna == 4) {
                        anio = cell.getStringCellValue();

                    } else if (columna == 5) {
                        descripcion = cell.getStringCellValue();

                    }

                    columna++;
                }
                preparedStatement.setString(1, cc);
                preparedStatement.setString(2, codigo);
                preparedStatement.setString(3, mes);
                preparedStatement.setString(4, mes_numero);
                preparedStatement.setString(5, anio);
                preparedStatement.setString(6, descripcion);
                preparedStatement.setString(7, nombre_archivo);
                preparedStatement.addBatch();
                cc = "";
                codigo = "";
                mes = "";
                mes_numero = "";
                anio = "";
                descripcion = "";
                System.out.println("");
            }
            file.close();
            int[] affectedRecords = preparedStatement.executeBatch();
            addMessage("Se ha cargado la información en el sistema");

        } catch (IOException ex) {
            Logger.getLogger(JSFManagedBeanFileUpload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JSFManagedBeanFileUpload.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    preparedStatement = null;
                } catch (SQLException ex) {
                    Logger.getLogger(JSFManagedBeanFileUpload.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    connection = null;
                } catch (SQLException ex) {
                    Logger.getLogger(JSFManagedBeanFileUpload.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    /**
     * @return the grabar
     */
    public String getGrabar() {
        return grabar;
    }

    /**
     * @param grabar the grabar to set
     */
    public void setGrabar(String grabar) {
        this.grabar = grabar;
    }
}
