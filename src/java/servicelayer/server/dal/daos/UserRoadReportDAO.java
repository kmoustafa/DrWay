/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.UserRoadReport;
import servicelayer.server.dal.pojos.Road;

/**
 *
 * @author eng.banoota
 */
public class UserRoadReportDAO {
     private final Session session;
     
     public UserRoadReportDAO (SessionFactory sessionFactory){
        session = sessionFactory.openSession();
    }
     
     public int insertReport(UserRoadReport report){
        
        try {
            this.session.beginTransaction();
            this.session.persist(report);
            this.session.getTransaction().commit();
            return 0;
        } catch (HibernateException e) {
            this.session.getTransaction().rollback();
            e.printStackTrace();
            return -1;
        }
        
    }
     public List<UserRoadReport> getAllReports() {
         try{
         List<UserRoadReport> userRoadReports = session.createCriteria(UserRoadReport.class).list();
            if(userRoadReports!=null && !userRoadReports.isEmpty()){
            
                for(UserRoadReport userRoadReport : userRoadReports){
                    session.refresh(userRoadReport);
                }
            }
         return userRoadReports;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
    
    public List<UserRoadReport> getAllRoadReports(Road road){
       try{
         List<UserRoadReport> userRoadReports = session.createCriteria(UserRoadReport.class).add(Restrictions.eq("road", road)).list();
            if(userRoadReports!=null && !userRoadReports.isEmpty()){
            
                for(UserRoadReport userRoadReport : userRoadReports){
                    session.refresh(userRoadReport);
                }
            }
         return userRoadReports;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
    
    public UserRoadReport getUserRoadReportById (int id){
        UserRoadReport userRoadReport = (UserRoadReport)session.get(UserRoadReport.class, id);
        if(userRoadReport!=null)
            session.refresh(userRoadReport);
        return userRoadReport;
    }
    
    public boolean update(UserRoadReport userRoadReport) {
        if(userRoadReport==null ){
            System.err.println("userRoadReport is null !");
            return false;
        }
        else if(userRoadReport.getRoad() ==null ){
            System.err.println("userRoadReport.road is null !");
            return false;
        }
         else {
            try{
                 session.getTransaction().begin();
                 session.saveOrUpdate(userRoadReport);  
                 session.getTransaction().commit();
                 System.out.println("userRoadReport is updated");
                 return true;
              }catch(Exception e){
                  e.printStackTrace();
                  System.err.println("DataBase Exception");
                  return false;
              }
        }
    }

    public boolean delete(UserRoadReport adminRoadReport) {
        if(adminRoadReport==null ){
            System.err.println("userRoadReport is null !");
            return false;
        }
        else if(adminRoadReport.getRoad() ==null ){
            System.err.println("userRoadReport.road is null !");
            return false;
        }
         else {
            try{
                 session.getTransaction().begin();
                 session.delete(adminRoadReport);  
                 session.getTransaction().commit();
                 System.out.println("userRoadReport is deleted");
                 return true;
              }catch(Exception e){
                  e.printStackTrace();
                  System.err.println("DataBase Exception");
                  return false;
              }
        }
    }

    public boolean insert(UserRoadReport adminRoadReport) {
        if(adminRoadReport==null ){
            System.err.println("userRoadReport is null !");
            return false;
        }
        else if(adminRoadReport.getRoad() == null ){
            System.err.println("userRoadReport.road is null !");
            return false;
        }
         else {
            try{
                 session.getTransaction().begin();
                 session.persist(adminRoadReport);  
                 session.getTransaction().commit();
                 System.out.println("userRoadReport is inserted");
                 return true;
              }catch(Exception e){
                  e.printStackTrace();
                  System.err.println("DataBase Exception");
                  return false;
              }
        }
    }
}
