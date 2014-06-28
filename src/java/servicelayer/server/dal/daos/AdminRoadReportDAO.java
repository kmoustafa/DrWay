/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.daos;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.AdminRoadReport;
import servicelayer.server.dal.pojos.Road;

/**
 *
 * @author eng.banoota
 */
public class AdminRoadReportDAO {
    private final Session session;
    
    public AdminRoadReportDAO (SessionFactory sessionFactory){
        session = sessionFactory.openSession();
    }
    
    public int numberOfAdminReportsOnRoad (int RoadId){
        return  0;
    }
    
    public Double getReportBlockingRatio(Road road, Date date) {
        try{
        return (Double) session.createCriteria(AdminRoadReport.class).setProjection(Projections.avg("blockingRatio"))
                .add(Restrictions.eq("road", road)).add(Restrictions.lt("startTimeStamp", new java.sql.Date(date.getTime())))
                .add(Restrictions.gt("endTimeStamp", date)).uniqueResult();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<AdminRoadReport> getAllReports() {
        try{
            List<AdminRoadReport> adminRoadReports = session.createCriteria(AdminRoadReport.class).list();
            if(adminRoadReports!=null && !adminRoadReports.isEmpty() ){
                for(AdminRoadReport adminRoadReport : adminRoadReports){
                    session.refresh(adminRoadReport);
                }
            }
            return adminRoadReports;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<AdminRoadReport> getAllRoadReports(Road road){
        try{
            List<AdminRoadReport> adminRoadReports = session.createCriteria(AdminRoadReport.class).add(Restrictions.eq("road", road)).list();
            if(adminRoadReports!=null && !adminRoadReports.isEmpty() ){
                for(AdminRoadReport adminRoadReport : adminRoadReports){
                    session.refresh(adminRoadReport);
                }
            }
            return adminRoadReports;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public AdminRoadReport getAdminRoadReportById (int id){
        try{
            AdminRoadReport adminRoadReport = (AdminRoadReport)session.get(AdminRoadReport.class, id);
            session.refresh(adminRoadReport);
            return adminRoadReport;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean update(AdminRoadReport adminRoadReport) {
        if(adminRoadReport==null ){
            System.err.println("adminRoadReport is null !");
            return false;
        }
        else if(adminRoadReport.getBlockingRatio() ==0 ){
            System.err.println("adminRoadReport.ration is null !");
            return false;
        }
         else {
            try{
                 session.getTransaction().begin();
                 session.saveOrUpdate(adminRoadReport);  
                 session.getTransaction().commit();
                 session.evict(adminRoadReport);
                 System.out.println("adminRoadReport is updated");
                 return true;
              }catch(Exception e){
                  e.printStackTrace();
                  System.err.println("DataBase Exception");
                  return false;
              }
        }
    }

    public boolean delete(AdminRoadReport adminRoadReport) {
        if(adminRoadReport==null ){
            System.err.println("adminRoadReport is null !");
            return false;
        }
        else if(adminRoadReport.getBlockingRatio() ==0 ){
            System.err.println("adminRoadReport.ratio is null !");
            return false;
        }
         else {
            try{
                 session.getTransaction().begin();
                 session.delete(adminRoadReport);  
                 session.getTransaction().commit();
                 System.out.println("adminRoadReport is deleted");
                 return true;
              }catch(Exception e){
                  e.printStackTrace();
                  System.err.println("DataBase Exception");
                  return false;
              }
        }
    }

    public boolean insert(AdminRoadReport adminRoadReport) {
        if(adminRoadReport==null ){
            System.err.println("adminRoadReport is null !");
            return false;
        }
        else if(adminRoadReport.getBlockingRatio() ==0 ){
            System.err.println("adminRoadReport.ratio is null !");
            return false;
        }
         else {
            try{
                 session.getTransaction().begin();
                 session.persist(adminRoadReport);  
                 session.getTransaction().commit();
                 session.evict(adminRoadReport);
                 System.out.println("adminRoadReport is inserted");
                 return true;
              }catch(Exception e){
                  e.printStackTrace();
                  System.err.println("DataBase Exception");
                  return false;
              }
        }
    }
}
