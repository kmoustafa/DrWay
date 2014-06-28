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
import servicelayer.server.dal.pojos.StatusLookUp;

/**
 *
 * @author eng.banoota
 */
public class StatusLookUpDAO {
    
     private final Session session;

    public StatusLookUpDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }
    
     public List<StatusLookUp> getAllStatuses(){
         try{
             List<StatusLookUp> statusLookUps = session.createCriteria(StatusLookUp.class).list();
             if(statusLookUps!=null && !statusLookUps.isEmpty()){
                 for(StatusLookUp statusLookUp : statusLookUps){
                     session.refresh(statusLookUp);
                 }
             }
            return statusLookUps;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
    public List<String> getAllStatusesNames(){
        try{
            return session.createCriteria(StatusLookUp.class).setProjection(Projections.property("name")).list();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public StatusLookUp getStatusById(int statusId){
        try{
        StatusLookUp statusLookUp = (StatusLookUp) session.get(StatusLookUp.class, statusId);
        if(statusLookUp!=null){
            session.refresh(statusLookUp);
        }
        return statusLookUp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
     public StatusLookUp getStatusByName(String statusName) {

         try{
            Criteria criteria = this.session.createCriteria(StatusLookUp.class);
            criteria.add(Restrictions.eq("name", statusName));
            StatusLookUp statusLookUp = (StatusLookUp) criteria.uniqueResult();
            if(statusLookUp!=null)
                session.refresh(statusLookUp);
            return statusLookUp;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
     
      public boolean update(StatusLookUp statusLookUp) {
        if(statusLookUp==null ){
            System.err.println("statusLookUp is null !");
            return false;
        }
        else if(statusLookUp.getName()==null ){
            System.err.println("statusLookUp.name is null !");
            return false;
        }
        else if(statusLookUp.getMaxSpeed()==0 ){
            System.err.println("statusLookUp.maxSpeed is zero !");
            return false;
        }
        else if(statusLookUp.getMinSpeed()==0 ){
            System.err.println("statusLookUp.minSpeed is zero !");
            return false;
        }
         
         else {
           try{
                session.getTransaction().begin();
                session.saveOrUpdate(statusLookUp);  
                session.getTransaction().commit();
                session.evict(statusLookUp);
                System.out.println("statusLookUp is updated");
                return true;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean delete(StatusLookUp statusLookUp) {
        if(statusLookUp==null ){
            System.err.println("statusLookUp is null !");
            return false;
        }
        else if(statusLookUp.getName()==null ){
            System.err.println("statusLookUp.name is null !");
            return false;
        }
         else if(statusLookUp.getMaxSpeed()==0 ){
            System.err.println("statusLookUp.maxSpeed is zero !");
            return false;
        }
        else if(statusLookUp.getMinSpeed()==0 ){
            System.err.println("statusLookUp.minSpeed is zero !");
            return false;
        }
         else {
             try{
                session.getTransaction().begin();
                session.delete(statusLookUp);  
                session.getTransaction().commit();
                System.out.println("statusLookUp is deleted");
                return true;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }

    public boolean insert(StatusLookUp statusLookUp) {
        if(statusLookUp==null ){
            System.err.println("statusLookUp is null !");
            return false;
        }
        else if(statusLookUp.getName()==null ){
            System.err.println("statusLookUp.name is null !");
            return false;
        }
         else {
            try{
                session.getTransaction().begin();
                session.persist(statusLookUp);  
                session.getTransaction().commit();
                session.evict(statusLookUp);
                System.out.println("statusLookUp is deleted");
                return true;
             }catch(Exception e){
                 e.printStackTrace();
                 System.err.println("DataBase Exception");
                 return false;
             }
        }
    }
}
