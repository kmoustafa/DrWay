/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import servicelayer.server.dal.daos.ReportTypeLookupDAO;
import servicelayer.server.dal.pojos.ReportTypeLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "reportTypes")
@SessionScoped
public class ReportTypeBean {
    private List<ReportTypeLookUp> reportTypeLookUpList;
    private DataModel<ReportTypeLookUp> reportTypeLookUpModel ;
    private ReportTypeLookupDAO reportTypeLookupDAO;
    private ReportTypeLookUp reportTypeLookUp;
    private List<String> reporTypeNames;
    
    public ReportTypeBean(){
        reportTypeLookUp = new ReportTypeLookUp();
        reportTypeLookupDAO = HibernateUtil.getREPORT_TYPE_LOOK_UP_DAO();
        reportTypeLookUpList = reportTypeLookupDAO.getAllReportTypes();
        reporTypeNames = reportTypeLookupDAO.getAllReportTypesName();
        reportTypeLookUpModel = new ListDataModel<ReportTypeLookUp>(reportTypeLookUpList);
    }

    public List<ReportTypeLookUp> getReportTypeLookUpList() {
        return reportTypeLookUpList;
    }

    public void setReportTypeLookUpList(List<ReportTypeLookUp> reportTypeLookUpList) {
        this.reportTypeLookUpList = reportTypeLookUpList;
    }

    public DataModel<ReportTypeLookUp> getReportTypeLookUpModel() {
        return reportTypeLookUpModel;
    }

    public void setReportTypeLookUpModel(DataModel<ReportTypeLookUp> reportTypeLookUpModel) {
        this.reportTypeLookUpModel = reportTypeLookUpModel;
    }

    public ReportTypeLookupDAO getReportTypeLookupDAO() {
        return reportTypeLookupDAO;
    }

    public void setReportTypeLookupDAO(ReportTypeLookupDAO reportTypeLookupDAO) {
        this.reportTypeLookupDAO = reportTypeLookupDAO;
    }

    public ReportTypeLookUp getReportTypeLookUp() {
        return reportTypeLookUp;
    }

    public void setReportTypeLookUp(ReportTypeLookUp reportTypeLookUp) {
        this.reportTypeLookUp = reportTypeLookUp;
    }

    public List<String> getReporTypeNames() {
        return reportTypeLookupDAO.getAllReportTypesName();
    }

    public void setReporTypeNames(List<String> reporTypeNames) {
        this.reporTypeNames = reportTypeLookupDAO.getAllReportTypesName();
    }
    
    public String goToAdd (){
        reportTypeLookUp = new ReportTypeLookUp();
        return "add_reportTypeLookup";
    }
    public String goToUpdate(int currentMainPointId){
        reportTypeLookUp = reportTypeLookupDAO.getReportLookupById(currentMainPointId);
        return "edit_reportTypeLookup";
    }
    
     public String addReportType(){
        if(reportTypeLookUp!=null){
            if(reportTypeLookupDAO.insert(reportTypeLookUp)){
            System.out.println("Report Type  Should be updated");
            reportTypeLookUpList = reportTypeLookupDAO.getAllReportTypes();
            reportTypeLookUpModel = new ListDataModel<ReportTypeLookUp>(reportTypeLookUpList);
            return "reportTypeLookUps";
            }
            else{
                System.err.println("Report Type didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("Report Type  is null !");
            return "error_page";
        }
            
    }
    
    public String updateReportType(){
        if(reportTypeLookUp!=null){
            if(reportTypeLookupDAO.update(reportTypeLookUp)){
            System.out.println("Report Type  Should be updated");
            reportTypeLookUpList = reportTypeLookupDAO.getAllReportTypes();
            reportTypeLookUpModel = new ListDataModel<ReportTypeLookUp>(reportTypeLookUpList);
            return "reportTypeLookUps";
            }
            else{
                System.err.println("Report Type  didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("Report Type  is null !");
            return "error_page";
        }
    }
    
    public String deleteReportType(){
        if(reportTypeLookUpModel.getRowData()!=null){
            if(reportTypeLookupDAO.delete(reportTypeLookUpModel.getRowData())){
                System.out.println("Report Type  Should be deleted");
                reportTypeLookUpList = reportTypeLookupDAO.getAllReportTypes();
                reportTypeLookUpModel = new ListDataModel<ReportTypeLookUp>(reportTypeLookUpList);
                return "reportTypeLookUps";
            }
            else{
                System.err.println("Report Type  didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("Report Type  is null !");
                return "error_page";
            }
        
    }
}
