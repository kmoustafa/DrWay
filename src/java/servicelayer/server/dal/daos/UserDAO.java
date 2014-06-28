/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import servicelayer.server.dal.pojos.BlockedUser;
import servicelayer.server.dal.pojos.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import servicelayer.server.util.ErrorCodesConstants;

/**
 *
 * @author Kareem Moustafa
 */
public class UserDAO {

    private final Session session;

    public UserDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

       public int updateUser(User user) {

        if (user != null) {
            try {
                this.session.getTransaction().begin();
                this.session.saveOrUpdate(user);
                this.session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                System.out.print("Error in Updating User!!");
                this.session.getTransaction().rollback();
                return 1;
            }
            return 0;
        }
        return -1;
    }

    public int blockUser(int id) {
        User user;
        try {
            this.session.getTransaction().begin();
            user = (User) this.session.get(User.class, id);
            session.refresh(user);
            BlockedUser blockedUser = new BlockedUser();
            blockedUser.setEmail(user.getEmail());
            blockedUser.setMobile(user.getMobile());
            blockedUser.setName(user.getName());
            blockedUser.setTokenType(String.valueOf(user.getTokenType()));
            blockedUser.setUserId(user.getId());
            blockedUser.setUserToken(user.getUserToken());
            this.session.delete(user);
            this.session.persist(blockedUser);
            this.session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            return ErrorCodesConstants.DATABASE_PROBLEM;
        }
        return ErrorCodesConstants.SUCCESS;
    }

    public User getUserByEmail(String email) {
        User result;
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.eq("email", email));
        List getUsers = cr.list();
        if (getUsers.size() > 0) {
            result = (User) getUsers.get(0);
            session.refresh(result);
            return result;
        }
        return null;
    }

    public User getUserById(int id) {
        User user = new User();
        try {
            user = (User) this.session.get(User.class, id);
            session.refresh(user);
        } catch (Exception e) {
            System.out.println("Exeee");
            e.printStackTrace();
            User user1 = new User();
            user1.setId(-1);
            return user1;
        }
        return user;
    }

    public void persist(User user) {
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    public int getUserIDByName(String name) {
        try {
            return (Integer) session.createCriteria(User.class).setProjection(Projections.id()).add(Restrictions.eq("name", name)).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return -1;
        }

    }

    // return 0 if user found
    // return -1 if user not found
    // return 1 if Database error
    public int isUserExist(int id) {
        try {
            Object obj = this.session.get(User.class, id);
            if (obj != null) {
                System.out.println("FOUND!!!!!!!" + ((User) obj).getName());
                return ErrorCodesConstants.SUCCESS;
            }
        } catch (Exception e) {
            System.err.print("Error in Updating User!!");
            this.session.getTransaction().rollback();
            e.printStackTrace();
            return 1;
        }
        return -1;
    }
    
    public List<User> getAllUsers (){
        try{
            List<User> users = session.createCriteria(User.class).list();
            if(users!=null && !users.isEmpty()){
                for(User user : users){
                    session.refresh(user);
                }
            }
            return users;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public List<String> getAllUserNames(){
        return session.createCriteria(User.class).setProjection(Projections.property("name")).list();
    }
    
}
