/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.FuelLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author MIRO
 */
public class FuelLookUpDAO {

    private final Session session;

    public FuelLookUpDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public List<FuelLookUp> getList() {
        try{
        List<FuelLookUp>  fuelLookUps = session.createCriteria(FuelLookUp.class).list();
        if(fuelLookUps!=null && !fuelLookUps.isEmpty()){
            for(FuelLookUp fuelLookUp : fuelLookUps){
                session.refresh(fuelLookUp);
            }
        }
        return fuelLookUps;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<FuelLookUp> getAllFuels(){
        try{
        List<FuelLookUp>  fuelLookUps = session.createCriteria(FuelLookUp.class).list();
        if(fuelLookUps!=null && !fuelLookUps.isEmpty()){
            for(FuelLookUp fuelLookUp : fuelLookUps){
                session.refresh(fuelLookUp);
            }
        }
        return fuelLookUps;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> getAllFuelsValues(){
        return session.createCriteria(FuelLookUp.class).list();
    }
   
    public FuelLookUp getFuelById(int fuelId){
        try{
        FuelLookUp fuelLookUp = (FuelLookUp) session.get(FuelLookUp.class, fuelId);
        if(fuelLookUp!= null){
            session.refresh(fuelLookUp);
        }
        return fuelLookUp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
     public FuelLookUp getFuelByValue(String fuelValue) {

         try{
            Criteria criteria = this.session.createCriteria(FuelLookUp.class);
            criteria.add(Restrictions.eq("Fuel_Value", fuelValue));
            FuelLookUp fuelLookUp = (FuelLookUp) criteria.uniqueResult();
            if(fuelLookUp!=null)
                session.refresh(fuelLookUp);
            return fuelLookUp;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
     
      public boolean update(FuelLookUp fuelLookUp) {
        if(fuelLookUp==null ){
            System.err.println("fuelLookUp is null !");
            return false;
        }
        else if(fuelLookUp.getValue()==null ){
            System.err.println("fuelLookUp.value is null !");
            return false;
        }
        else if(fuelLookUp.getModel()==null ){
            System.err.println("fuelLookUp.model is null !");
            return false;
        }
         
         else {
           try{
                session.getTransaction().begin();
                session.saveOrUpdate(fuelLookUp);  
                session.getTransaction().commit();
                session.evict(fuelLookUp);
                System.out.println("fuelLookUp is updated");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateMainPointListVersion())
                    return true;
                else
                    return false;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean delete(FuelLookUp fuelLookUp) {
        if(fuelLookUp==null ){
            System.err.println("fuelLookUp is null !");
            return false;
        }
        else if(fuelLookUp.getValue()==null ){
            System.err.println("fuelLookUp.value is null !");
            return false;
        }
        else if(fuelLookUp.getModel()==null ){
            System.err.println("fuelLookUp.model is null !");
            return false;
        }
         
         else {
             try{
                session.getTransaction().begin();
                session.delete(fuelLookUp);  
                session.getTransaction().commit();
                System.out.println("fuelLookUp is deleted");
                 CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateMainPointListVersion())
                    return true;
                else
                    return false;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean insert(FuelLookUp fuelLookUp) {
        if(fuelLookUp==null ){
            System.err.println("fuelLookUp is null !");
            return false;
        }
        else if(fuelLookUp.getValue()==null ){
            System.err.println("fuelLookUp.value is null !");
            return false;
        }
        else if(fuelLookUp.getModel()==null ){
            System.err.println("fuelLookUp.model is null !");
            return false;
        }
         
         else {
            try{
                session.getTransaction().begin();
                session.persist(fuelLookUp);  
                session.getTransaction().commit();
                session.evict(fuelLookUp);
                System.out.println("fuelLookUp is deleted");
                 CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateMainPointListVersion())
                    return true;
                else
                    return false;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }
}
