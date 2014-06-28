/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.ReportTypeLookUp;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
public class ReportTypeLookupDAO {
    private final Session session;
    
    public ReportTypeLookupDAO(SessionFactory sessionFactory){
        session = sessionFactory.openSession();
    }
    
    public List<ReportTypeLookUp> getList() {
        try {
            List<ReportTypeLookUp> reportTypeLookUps = session.createCriteria(ReportTypeLookUp.class).list();
            if (reportTypeLookUps != null && !reportTypeLookUps.isEmpty()) {
                for (ReportTypeLookUp reportTypeLookUp : reportTypeLookUps) {
                    session.refresh(reportTypeLookUp);
                }
            }
            return reportTypeLookUps;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ReportTypeLookUp getReportTypeLookUpById(int id) {
        try {
            ReportTypeLookUp reportTypeLookUp = (ReportTypeLookUp) this.session.get(ReportTypeLookUp.class, id);
            session.refresh(reportTypeLookUp);
            return reportTypeLookUp;
        } catch (HibernateException e) {
            e.printStackTrace();
            ReportTypeLookUp lookUp = new ReportTypeLookUp();
            lookUp.setReportTypeId(-1);
        }
        return null;
    }

    public List<ReportTypeLookUp> getAllReportTypes(){
        try{
            List<ReportTypeLookUp>  reportTypeLookUps = session.createCriteria(ReportTypeLookUp.class).list();
            if(reportTypeLookUps!=null && !reportTypeLookUps.isEmpty()){
                for(ReportTypeLookUp reportTypeLookUp : reportTypeLookUps){
                    session.refresh(reportTypeLookUp);
                }
            }
            return reportTypeLookUps;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> getAllReportTypesName(){
        return session.createCriteria(ReportTypeLookUp.class).setProjection(Projections.property("reportTypeValue")).list();
    }
    public ReportTypeLookUp getReportLookupById(int id){
        try{
            ReportTypeLookUp reportTypeLookUp = (ReportTypeLookUp) session.get(ReportTypeLookUp.class, id);
            if(reportTypeLookUp!=null){
                session.refresh(reportTypeLookUp);
            }
            return reportTypeLookUp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
     public ReportTypeLookUp getReportLookupByName(String reportTypeName) {

         try{
            Criteria criteria = this.session.createCriteria(ReportTypeLookUp.class);
            criteria.add(Restrictions.eq("reportTypeValue", reportTypeName));
            ReportTypeLookUp reportTypeLookUp = (ReportTypeLookUp) criteria.uniqueResult();
            if(reportTypeLookUp!=null)
                session.refresh(reportTypeLookUp);
            return reportTypeLookUp;
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }
    }
     
      public boolean update(ReportTypeLookUp reportTypeLookUp) {
        if(reportTypeLookUp==null ){
            System.err.println("reportTypeLookUp is null !");
            return false;
        }
        else if(reportTypeLookUp.getReportTypeValue()==null ){
            System.err.println("reportTypeLookUp.value is null !");
            return false;
        }
         
         else {
           try{
                session.getTransaction().begin();
                session.saveOrUpdate(reportTypeLookUp);  
                session.getTransaction().commit();
                session.evict(reportTypeLookUp);
                System.out.println("reportTypeLookUp is updated");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateReportTypeVersion())
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

    public boolean delete(ReportTypeLookUp reportTypeLookUp) {
        if(reportTypeLookUp==null ){
            System.err.println("reportTypeLookUp is null !");
            return false;
        }
        else if(reportTypeLookUp.getReportTypeValue()==null ){
            System.err.println("reportTypeLookUp.value is null !");
            return false;
        }
         else {
             try{
                session.getTransaction().begin();
                session.delete(reportTypeLookUp);  
                session.getTransaction().commit();
                System.out.println("reportTypeLookUp is deleted");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateReportTypeVersion())
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

    public boolean insert(ReportTypeLookUp reportTypeLookUp) {
        if(reportTypeLookUp==null ){
            System.err.println("reportTypeLookUp is null !");
            return false;
        }
        else if(reportTypeLookUp.getReportTypeValue()==null ){
            System.err.println("reportTypeLookUp.value is null !");
            return false;
        }
         else {
            try{
                session.getTransaction().begin();
                session.persist(reportTypeLookUp);  
                session.getTransaction().commit();
                session.evict(reportTypeLookUp);
                System.out.println("reportTypeLookUp is deleted");
                CurrentVersionDAO currentVersionDAO = new CurrentVersionDAO(HibernateUtil.getSessionFactory());
                if(currentVersionDAO.updateReportTypeVersion())
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
