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
import servicelayer.server.dal.daos.CarListLookUpDAO;
import servicelayer.server.dal.pojos.CarListLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "cars")
@SessionScoped
public class CarListBean {
    
    private List<CarListLookUp> carListLookUpList;
    private DataModel<CarListLookUp> carListLookUpModel ;
    private CarListLookUpDAO carListLookUpDAO;
    private CarListLookUp carListLookUp;
    private List<String> carListLookUpValues;
    
    public CarListBean(){
        carListLookUp = new CarListLookUp();
        carListLookUpDAO = HibernateUtil.getCAR_LIST_LOOK_UP_DAO();
        carListLookUpList = carListLookUpDAO.getAllCarLists();
        carListLookUpValues = carListLookUpDAO.getAllCarListsValues();
        carListLookUpModel = new ListDataModel<CarListLookUp>(carListLookUpList);
    }

    public List<CarListLookUp> getCarListLookUpList() {
        return carListLookUpList;
    }

    public void setCarListLookUpList(List<CarListLookUp> carListLookUpList) {
        this.carListLookUpList = carListLookUpList;
    }

    public DataModel<CarListLookUp> getCarListLookUpModel() {
        return carListLookUpModel;
    }

    public void setCarListLookUpModel(DataModel<CarListLookUp> carListLookUpModel) {
        this.carListLookUpModel = carListLookUpModel;
    }

    public CarListLookUpDAO getCarListLookUpDAO() {
        return carListLookUpDAO;
    }

    public void setCarListLookUpDAO(CarListLookUpDAO carListLookUpDAO) {
        this.carListLookUpDAO = carListLookUpDAO;
    }

    public CarListLookUp getCarListLookUp() {
        return carListLookUp;
    }

    public void setCarListLookUp(CarListLookUp carListLookUp) {
        this.carListLookUp = carListLookUp;
    }

    public List<String> getCarListLookUpValues() {
        return carListLookUpValues;
    }

    public void setCarListLookUpValues(List<String> carListLookUpValues) {
        this.carListLookUpValues = carListLookUpValues;
    }
    
    public String goToAdd (){
        carListLookUp = new CarListLookUp();
        return "add_carListLookup";
    }
    public String goToUpdate(int id){
        carListLookUp = carListLookUpDAO.getCarListById(id);
        return "edit_carListLookup";
    }
    
     public String addCarListType(){
        if(carListLookUp!=null){
            if(carListLookUpDAO.insert(carListLookUp)){
            System.out.println("carList Type  Should be updated");
            carListLookUpList = carListLookUpDAO.getAllCarLists();
            carListLookUpModel = new ListDataModel<CarListLookUp>(carListLookUpList);
            return "carListLookUps";
            }
            else{
                System.err.println("carList Type didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("carList Type  is null !");
            return "error_page";
        }
            
    }
    
    public String updateCarListType(){
        if(carListLookUp!=null){
            if(carListLookUpDAO.update(carListLookUp)){
            System.out.println("carList Type  Should be updated");
            carListLookUpList = carListLookUpDAO.getAllCarLists();
            carListLookUpModel = new ListDataModel<CarListLookUp>(carListLookUpList);
            return "carListLookUps";
            }
            else{
                System.err.println("carList Type  didn't updated !");
                return "error_page";
            }
        }
        else{
            System.err.println("carList Type  is null !");
            return "error_page";
        }
    }
    
    public String deletecarListType(){
        if(carListLookUpModel.getRowData()!=null){
            if(carListLookUpDAO.delete(carListLookUpModel.getRowData())){
                System.out.println("carList Type  Should be deleted");
                carListLookUpList = carListLookUpDAO.getAllCarLists();
                carListLookUpModel = new ListDataModel<CarListLookUp>(carListLookUpList);
                return "carListLookUps";
            }
            else{
                System.err.println("carList Type  didn't updated !");
                return "error_page";
            }
        }
        else{
                System.err.println("carList Type  is null !");
                return "error_page";
            }
        
    }
}
