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
import servicelayer.server.dal.daos.StatusLookUpDAO;
import servicelayer.server.dal.pojos.StatusLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "statuses")
@SessionScoped
public class StatusesBean {
    private List<StatusLookUp> statusLookUpList;
    private DataModel<StatusLookUp> statusLookUpModel ;
    private StatusLookUpDAO statusLookUpDAO;
    private StatusLookUp statusLookUp;
    private List<String> statusLookUpValues;
    
    public StatusesBean(){
        statusLookUp = new StatusLookUp();
        statusLookUpDAO = HibernateUtil.getSTATUS_LOOK_UP_DAO();
        statusLookUpList = statusLookUpDAO.getAllStatuses();
        statusLookUpValues = statusLookUpDAO.getAllStatusesNames();
        statusLookUpModel = new ListDataModel<StatusLookUp>(statusLookUpList);
    }

    public List<StatusLookUp> getStatusLookUpList() {
        return statusLookUpList;
    }

    public void setStatusLookUpList(List<StatusLookUp> statusLookUpList) {
        this.statusLookUpList = statusLookUpList;
    }

    public DataModel<StatusLookUp> getStatusLookUpModel() {
        return statusLookUpModel;
    }

    public void setStatusLookUpModel(DataModel<StatusLookUp> statusLookUpModel) {
        this.statusLookUpModel = statusLookUpModel;
    }

    public StatusLookUpDAO getStatusLookUpDAO() {
        return statusLookUpDAO;
    }

    public void setStatusLookUpDAO(StatusLookUpDAO statusLookUpDAO) {
        this.statusLookUpDAO = statusLookUpDAO;
    }

    public StatusLookUp getStatusLookUp() {
        return statusLookUp;
    }

    public void setStatusLookUp(StatusLookUp statusLookUp) {
        this.statusLookUp = statusLookUp;
    }

    public List<String> getStatusLookUpValues() {
        return statusLookUpValues;
    }

    public void setStatusLookUpValues(List<String> statusLookUpValues) {
        this.statusLookUpValues = statusLookUpValues;
    }
    
    public String goToAdd (){
        statusLookUp = new StatusLookUp();
        return "add_statusLookup";
    }
    public String goToUpdate(int id){
        statusLookUp = statusLookUpDAO.getStatusById(id);
        return "edit_statusLookup";
    }
    
     public String addStatusType(){
        if(statusLookUp!=null){
            if(statusLookUpDAO.insert(statusLookUp)){
            System.out.println("status Type  Should be updated");
            statusLookUpList = statusLookUpDAO.getAllStatuses();
            statusLookUpModel = new ListDataModel<StatusLookUp>(statusLookUpList);
            return "statusLookUps";
            }
            else{
                System.err.println("status Type didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("status Type  is null !");
            return "error_page";
        }
            
    }
    
    public String updateStatusType(){
        if(statusLookUp!=null){
            if(statusLookUpDAO.update(statusLookUp)){
            System.out.println("status Type  Should be updated");
            statusLookUpList = statusLookUpDAO.getAllStatuses();
            statusLookUpModel = new ListDataModel<StatusLookUp>(statusLookUpList);
            return "statusLookUps";
            }
            else{
                System.err.println("status Type  didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("status Type  is null !");
            return "error_page";
        }
    }
    
    public String deletestatusType(){
        if(statusLookUpModel.getRowData()!=null){
            if(statusLookUpDAO.delete(statusLookUpModel.getRowData())){
                System.out.println("status Type  Should be deleted");
                statusLookUpList = statusLookUpDAO.getAllStatuses();
                statusLookUpModel = new ListDataModel<StatusLookUp>(statusLookUpList);
                return "statusLookUps";
            }
            else{
                System.err.println("status Type  didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("status Type  is null !");
                return "error_page";
            }
        
    }
}

