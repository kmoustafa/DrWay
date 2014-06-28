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
import servicelayer.server.dal.daos.DateLookUpDAO;
import servicelayer.server.dal.pojos.DateLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "dates")
@SessionScoped
public class DatesBean {
    private List<DateLookUp> dateLookUpList;
    private DataModel<DateLookUp> dateLookUpModel ;
    private DateLookUpDAO dateLookUpDAO;
    private DateLookUp dateLookUp;
    
    public DatesBean(){
        dateLookUp = new DateLookUp();
        dateLookUpDAO = HibernateUtil.getDATE_LOOK_UP_DAO();
        dateLookUpList = dateLookUpDAO.getAllDates();
        dateLookUpModel = new ListDataModel<DateLookUp>(dateLookUpList);
    }

    public List<DateLookUp> getDateLookUpList() {
        return dateLookUpList;
    }

    public void setDateLookUpList(List<DateLookUp> dateLookUpList) {
        this.dateLookUpList = dateLookUpList;
    }

    public DataModel<DateLookUp> getDateLookUpModel() {
        return dateLookUpModel;
    }

    public void setDateLookUpModel(DataModel<DateLookUp> dateLookUpModel) {
        this.dateLookUpModel = dateLookUpModel;
    }

    public DateLookUpDAO getDateLookUpDAO() {
        return dateLookUpDAO;
    }

    public void setDateLookUpDAO(DateLookUpDAO dateLookUpDAO) {
        this.dateLookUpDAO = dateLookUpDAO;
    }

    public DateLookUp getDateLookUp() {
        return dateLookUp;
    }

    public void setDateLookUp(DateLookUp dateLookUp) {
        this.dateLookUp = dateLookUp;
    }
    
    public String goToAdd (){
        dateLookUp = new DateLookUp();
        return "add_dateLookup";
    }
    public String goToUpdate(int id){
        dateLookUp = dateLookUpDAO.getDateById(id);
        return "edit_dateLookup";
    }
    
     public String addDateType(){
        if(dateLookUp!=null){
            if(dateLookUpDAO.insert(dateLookUp)){
            System.out.println("date Type  Should be updated");
            dateLookUpList = dateLookUpDAO.getAllDates();
            dateLookUpModel = new ListDataModel<DateLookUp>(dateLookUpList);
            return "dateLookUps";
            }
            else{
                System.err.println("date Type didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("date Type  is null !");
            return "error_page";
        }
            
    }
    
    public String updateDateType(){
        if(dateLookUp!=null){
            if(dateLookUpDAO.update(dateLookUp)){
            System.out.println("date Type  Should be updated");
            dateLookUpList = dateLookUpDAO.getAllDates();
            dateLookUpModel = new ListDataModel<DateLookUp>(dateLookUpList);
            return "dateLookUps";
            }
            else{
                System.err.println("date Type  didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("date Type  is null !");
            return "error_page";
        }
    }
    
    public String deletedateType(){
        if(dateLookUpModel.getRowData()!=null){
            if(dateLookUpDAO.delete(dateLookUpModel.getRowData())){
                System.out.println("date Type  Should be deleted");
                dateLookUpList = dateLookUpDAO.getAllDates();
                dateLookUpModel = new ListDataModel<DateLookUp>(dateLookUpList);
                return "dateLookUps";
            }
            else{
                System.err.println("date Type  didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("date Type  is null !");
                return "error_page";
            }
        
    }
}
