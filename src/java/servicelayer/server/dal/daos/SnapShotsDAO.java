/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicelayer.server.dal.daos;

import java.util.List;
import org.hibernate.Criteria;
import servicelayer.server.dal.pojos.Snapshot;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Kareem Moustafa
 */
public class SnapShotsDAO {

    private final Session session;

    public SnapShotsDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    // return 0 if blocked
    // return -1 block fail
    // return 1 if Database error
    public int updateSnapShot(Snapshot snapShot) {
        if (snapShot != null) {
            try {
                this.session.getTransaction().begin();
                //this.session.beginTransaction();
                this.session.saveOrUpdate(snapShot);
                this.session.getTransaction().commit();
            } catch (Exception e) {
                System.err.print("Error in Updating PendingUser!!1");
                this.session.getTransaction().rollback();
                e.printStackTrace();
                return -1;
            }
        } else {
            return 0;
        }
        return 1;
    }

    public List getTimeStamps() {
        try{
            Criteria cr = session.createCriteria(Snapshot.class);
            return cr.list();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        SnapShotsDAO snapShotsDAO = HibernateUtil.getSNAP_SHOTS_DAO();
//        System.out.println(snapShotsDAO.getTimeStamps());
//    }

}
