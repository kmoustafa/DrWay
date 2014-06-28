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
import servicelayer.server.dal.pojos.ModeLookUp;

/**
 *
 * @author eng.banoota
 */
public class ModeLookUpDAO {
    
     private final Session session;

    public ModeLookUpDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }
    
    public List<ModeLookUp> getAllModes(){
        try{
        List<ModeLookUp> modeLookUps = session.createCriteria(ModeLookUp.class).list();
        if(modeLookUps!= null && !modeLookUps.isEmpty()){
            for(ModeLookUp modeLookUp : modeLookUps){
                session.refresh(modeLookUp);
            }
        }
        return modeLookUps;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getAllModesValues(){
        try{
        return session.createCriteria(ModeLookUp.class).setProjection(Projections.property("modeValue")).list();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public ModeLookUp getModeById(int modeId){
        try {
        ModeLookUp modeLookUp = (ModeLookUp) session.get(ModeLookUp.class, modeId);
        if(modeLookUp!= null)
            session.refresh(modeLookUp);
        return modeLookUp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
     public ModeLookUp getModeByValue(String modeValue) {

         try{
        Criteria criteria = this.session.createCriteria(ModeLookUp.class);
        criteria.add(Restrictions.eq("Mode_Value", modeValue));
        ModeLookUp modeLookUp = (ModeLookUp) criteria.uniqueResult();
        if(modeLookUp!=null)
            session.refresh(modeLookUp);
        return modeLookUp;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
     
      public boolean update(ModeLookUp modeLookUp) {
        if(modeLookUp==null ){
            System.err.println("modeLookUp is null !");
            return false;
        }
        else if(modeLookUp.getModeValue()==null ){
            System.err.println("modeLookUp.value is null !");
            return false;
        }
         
         else {
           try{
                session.getTransaction().begin();
                session.saveOrUpdate(modeLookUp);  
                session.getTransaction().commit();
                session.evict(modeLookUp);
                System.out.println("modeLookUp is updated");
                return true;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean delete(ModeLookUp modeLookUp) {
        if(modeLookUp==null ){
            System.err.println("modeLookUp is null !");
            return false;
        }
        else if(modeLookUp.getModeValue()==null ){
            System.err.println("modeLookUp.value is null !");
            return false;
        }
         else {
             try{
                session.getTransaction().begin();
                session.delete(modeLookUp);  
                session.getTransaction().commit();
                System.out.println("modeLookUp is deleted");
                return true;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean insert(ModeLookUp modeLookUp) {
        if(modeLookUp==null ){
            System.err.println("modeLookUp is null !");
            return false;
        }
        else if(modeLookUp.getModeValue()==null ){
            System.err.println("modeLookUp.value is null !");
            return false;
        }
         else {
            try{
                session.getTransaction().begin();
                session.persist(modeLookUp);  
                session.getTransaction().commit();
                session.evict(modeLookUp);
                System.out.println("modeLookUp is deleted");
                return true;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }
}
