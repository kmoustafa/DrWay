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
import servicelayer.server.dal.daos.ModeLookUpDAO;
import servicelayer.server.dal.pojos.ModeLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "modes")
@SessionScoped
public class ModesBean {
    
    private List<ModeLookUp> modeLookUpList;
    private DataModel<ModeLookUp> modeLookUpModel ;
    private ModeLookUpDAO modeLookUpDAO;
    private ModeLookUp modeLookUp;
    private List<String> modeLookUpValues;
    
    public ModesBean(){
        modeLookUp = new ModeLookUp();
        modeLookUpDAO = HibernateUtil.getMODE_LOOK_UP_DAO();
        modeLookUpList = modeLookUpDAO.getAllModes();
        modeLookUpValues = modeLookUpDAO.getAllModesValues();
        modeLookUpModel = new ListDataModel<ModeLookUp>(modeLookUpList);
    }

    public List<ModeLookUp> getModeLookUpList() {
        return modeLookUpList;
    }

    public void setModeLookUpList(List<ModeLookUp> modeLookUpList) {
        this.modeLookUpList = modeLookUpList;
    }

    public DataModel<ModeLookUp> getModeLookUpModel() {
        return modeLookUpModel;
    }

    public void setModeLookUpModel(DataModel<ModeLookUp> modeLookUpModel) {
        this.modeLookUpModel = modeLookUpModel;
    }

    public ModeLookUpDAO getModeLookUpDAO() {
        return modeLookUpDAO;
    }

    public void setModeLookUpDAO(ModeLookUpDAO modeLookUpDAO) {
        this.modeLookUpDAO = modeLookUpDAO;
    }

    public ModeLookUp getModeLookUp() {
        return modeLookUp;
    }

    public void setModeLookUp(ModeLookUp modeLookUp) {
        this.modeLookUp = modeLookUp;
    }

    public List<String> getModeLookUpValues() {
        return modeLookUpValues;
    }

    public void setModeLookUpValues(List<String> modeLookUpValues) {
        this.modeLookUpValues = modeLookUpValues;
    }
    
    public String goToAdd (){
        modeLookUp = new ModeLookUp();
        return "add_modeLookup";
    }
    public String goToUpdate(int id){
        modeLookUp = modeLookUpDAO.getModeById(id);
        return "edit_modeLookup";
    }
    
     public String addModeType(){
        if(modeLookUp!=null){
            if(modeLookUpDAO.insert(modeLookUp)){
            System.out.println("mode Type  Should be updated");
            modeLookUpList = modeLookUpDAO.getAllModes();
            modeLookUpModel = new ListDataModel<ModeLookUp>(modeLookUpList);
            return "modeLookups";
            }
            else{
                System.err.println("mode Type didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("mode Type  is null !");
            return "error_page";
        }
            
    }
    
    public String updateModeType(){
        if(modeLookUp!=null){
            if(modeLookUpDAO.update(modeLookUp)){
            System.out.println("mode Type  Should be updated");
            modeLookUpList = modeLookUpDAO.getAllModes();
            modeLookUpModel = new ListDataModel<ModeLookUp>(modeLookUpList);
            return "modeLookups";
            }
            else{
                System.err.println("mode Type  didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("mode Type  is null !");
            return "error_page";
        }
    }
    
    public String deletemodeType(){
        if(modeLookUpModel.getRowData()!=null){
            if(modeLookUpDAO.delete(modeLookUpModel.getRowData())){
                System.out.println("mode Type  Should be deleted");
                modeLookUpList = modeLookUpDAO.getAllModes();
                modeLookUpModel = new ListDataModel<ModeLookUp>(modeLookUpList);
                return "modeLookUps";
            }
            else{
                System.err.println("mode Type  didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("mode Type  is null !");
                return "error_page";
            }
        
    }
}
