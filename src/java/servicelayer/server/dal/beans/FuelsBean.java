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
import servicelayer.server.dal.daos.FuelLookUpDAO;
import servicelayer.server.dal.pojos.FuelLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "fuels")
@SessionScoped
public class FuelsBean {
    
    private List<FuelLookUp> fuelLookUpList;
    private DataModel<FuelLookUp> fuelLookUpModel ;
    private FuelLookUpDAO fuelLookUpDAO;
    private FuelLookUp fuelLookUp;
    private List<String> fuelLookUpValues;
    
    public FuelsBean(){
        fuelLookUp = new FuelLookUp();
        fuelLookUpDAO = HibernateUtil.getFUEL_COST_LOOK_UP_DAO();
        fuelLookUpList = fuelLookUpDAO.getAllFuels();
        fuelLookUpValues = fuelLookUpDAO.getAllFuelsValues();
        fuelLookUpModel = new ListDataModel<FuelLookUp>(fuelLookUpList);
    }

    public List<FuelLookUp> getFuelLookUpList() {
        return fuelLookUpList;
    }

    public void setFuelLookUpList(List<FuelLookUp> fuelLookUpList) {
        this.fuelLookUpList = fuelLookUpList;
    }

    public DataModel<FuelLookUp> getFuelLookUpModel() {
        return fuelLookUpModel;
    }

    public void setFuelLookUpModel(DataModel<FuelLookUp> fuelLookUpFuell) {
        this.fuelLookUpModel = fuelLookUpFuell;
    }

    public FuelLookUpDAO getFuelLookUpDAO() {
        return fuelLookUpDAO;
    }

    public void setFuelLookUpDAO(FuelLookUpDAO fuelLookUpDAO) {
        this.fuelLookUpDAO = fuelLookUpDAO;
    }

    public FuelLookUp getFuelLookUp() {
        return fuelLookUp;
    }

    public void setFuelLookUp(FuelLookUp fuelLookUp) {
        this.fuelLookUp = fuelLookUp;
    }

    public List<String> getFuelLookUpValues() {
        return fuelLookUpValues;
    }

    public void setFuelLookUpValues(List<String> fuelLookUpValues) {
        this.fuelLookUpValues = fuelLookUpValues;
    }
    
    public String goToAdd (){
        fuelLookUp = new FuelLookUp();
        return "add_fuelLookup";
    }
    public String goToUpdate(int id){
        fuelLookUp = fuelLookUpDAO.getFuelById(id);
        return "edit_fuelLookup";
    }
    
     public String addFuelType(){
        if(fuelLookUp!=null){
            if(fuelLookUpDAO.insert(fuelLookUp)){
            System.out.println("fuel Type  Should be updated");
            fuelLookUpList = fuelLookUpDAO.getAllFuels();
            fuelLookUpModel = new ListDataModel<FuelLookUp>(fuelLookUpList);
            return "fuelTypeLookUps";
            }
            else{
                System.err.println("fuel Type didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("fuel Type  is null !");
            return "error_page";
        }
            
    }
    
    public String updateFuelType(){
        if(fuelLookUp!=null){
            if(fuelLookUpDAO.update(fuelLookUp)){
            System.out.println("fuel Type  Should be updated");
            fuelLookUpList = fuelLookUpDAO.getAllFuels();
            fuelLookUpModel = new ListDataModel<FuelLookUp>(fuelLookUpList);
            return "fuelTypeLookUps";
            }
            else{
                System.err.println("fuel Type  didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("fuel Type  is null !");
            return "error_page";
        }
    }
    
    public String deletefuelType(){
        if(fuelLookUpModel.getRowData()!=null){
            if(fuelLookUpDAO.delete(fuelLookUpModel.getRowData())){
                System.out.println("fuel Type  Should be deleted");
                fuelLookUpList = fuelLookUpDAO.getAllFuels();
                fuelLookUpModel = new ListDataModel<FuelLookUp>(fuelLookUpList);
                return "fuelTypeLookUps";
            }
            else{
                System.err.println("fuel Type  didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("fuel Type  is null !");
                return "error_page";
            }
        
    }
    
}
