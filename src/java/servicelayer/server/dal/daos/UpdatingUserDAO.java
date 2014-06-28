/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import servicelayer.server.dal.pojos.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Kareem Moustafa
 */
public class UpdatingUserDAO {

    private final Session session;

    public UpdatingUserDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    // return 0 if done!
    // return -1 if user = null
    // return 1 if Database error
    public int updateUser(User user) {

        if (user != null) {
            try {
                this.session.getTransaction().begin();
                this.session.merge(user);
                this.session.getTransaction().commit();
            } catch (Exception e) {
                System.err.print("Error in Updating User!!");
                this.session.getTransaction().rollback();
                e.printStackTrace();
                return 1;
            }
        } else {
            return -1;
        }
        return 0;
    }

    // return 0 if user found
    // return -1 if user not found
    // return 1 if Database error
    public int isUserExist(int id) {
        try {
            Object obj = this.session.get(User.class, id);
            if (obj != null) {
                System.out.println("FOUND!!!!!!!" + ((User) obj).getName());
                return 0;
            }
        } catch (Exception e) {
            System.err.print("Error in Updating User!!");
            e.printStackTrace();
            return 1;
        }
        return -1;
    }

}
