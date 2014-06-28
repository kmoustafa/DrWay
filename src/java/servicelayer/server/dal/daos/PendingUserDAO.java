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
import servicelayer.server.dal.pojos.PendingUser;
import servicelayer.server.util.ErrorCodesConstants;

/**
 *
 * @author eng.banoota
 */
public class PendingUserDAO {
    
     private final Session session;

    public PendingUserDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }
    
    public List<PendingUser> getAllPendingUsers (){
        try{
            List<PendingUser> pendingUsers = session.createCriteria(PendingUser.class).list();
            if(pendingUsers!=null && !pendingUsers.isEmpty()){
               for(PendingUser pendingUser : pendingUsers){
                   session.refresh(pendingUser);
               } 
            }
            return pendingUsers;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> getAllPendingUserNames(){
        return session.createCriteria(PendingUser.class).setProjection(Projections.property("name")).list();
    }
    
    public int updatePendingUser(PendingUser pendingUser) {

        if (pendingUser != null) {
            try {
                this.session.getTransaction().begin();
                this.session.saveOrUpdate(pendingUser);
                this.session.getTransaction().commit();
            } catch (HibernateException e) {
                System.err.println("Error in Updating PendingUser!!!");
                this.session.getTransaction().rollback();
                return ErrorCodesConstants.DATABASE_PROBLEM;
            }
            return ErrorCodesConstants.SUCCESS;
        }
        return ErrorCodesConstants.INVALID_PARAMETER;
    }

    public PendingUser getPendingUserByEmail(String email) {
        try {
            PendingUser result;
            Criteria cr = session.createCriteria(PendingUser.class);
            cr.add(Restrictions.eq("email", email));
            List<PendingUser> getUsers = cr.list();
            if (getUsers != null && !getUsers.isEmpty()) {
                result = (PendingUser) getUsers.get(0);
                session.refresh(result);
                return result;
            }
            return null;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PendingUser userExistAsPending(PendingUser pendingUser) {
        try {
            if (pendingUser != null) {
                String pendingUserName = pendingUser.getName();
                Criteria cr = session.createCriteria(PendingUser.class);
                cr.add(Restrictions.eq("Name", pendingUserName));
                List getPendingUsers = cr.list();
                if (getPendingUsers != null && !getPendingUsers.isEmpty()) {
                    PendingUser pendingUser1 = (PendingUser) getPendingUsers.get(0);
                    session.refresh(pendingUser1);
                    return pendingUser1;
                } else {
                    return null;
                }
            }
            return null;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Retrun true if The code Exist 
    public boolean isCodeExist(String verificationCode) {
        if (verificationCode != null) {
            try {
                Criteria cr = session.createCriteria(PendingUser.class);
                cr.add(Restrictions.eq("verificationCode", verificationCode));
                List pendUser = cr.list();
                if (pendUser != null) {
                    return true;
                }
            } catch (Exception e) {
                System.err.print("Error in Getting PendingUser verification codes!!");
                //e.printStackTrace();
            }
        } else {
            System.err.print("verificationCode is null!!");
            return false;
        }
        return false;
    }

    public int isPendingUserExist(int id) {
        try {
            Object obj = this.session.get(PendingUser.class, id);
            if (obj != null) {
                return ErrorCodesConstants.SUCCESS;
            }
        } catch (HibernateException e) {
            System.err.print("Error in Updating User!!");
            this.session.getTransaction().rollback();
            //e.printStackTrace();
            return ErrorCodesConstants.DATABASE_PROBLEM;
        }
        return ErrorCodesConstants.INVALID_PARAMETER;
    }

    public String getVerificationCode(int pendingUserID) {
        try {
            String str = (String) session.createCriteria(PendingUser.class)
                    .setProjection(Projections.property("verificationCode"))
                    .add(Restrictions.eq("id", pendingUserID)).uniqueResult();
            return str;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PendingUser getPendingUser(int id) {
        try {
            PendingUser pendingUser = (PendingUser) session.get(PendingUser.class, id);
            session.refresh(pendingUser);
            return pendingUser;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePendingUser(PendingUser pendingUser) {
        try {
            if (pendingUser != null) {
                session.beginTransaction();
                session.delete(pendingUser);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
