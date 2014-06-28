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
import org.hibernate.criterion.Restrictions;
import servicelayer.server.dal.pojos.Admin;

/**
 *
 * @author eng.banoota
 */
public class AdminDAO {

    private final Session session;

    public AdminDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    public String adminLogin(String userName, String password) {
        System.out.println("UserName recieved : " + userName);
        System.out.println("password recieved : " + password);
        Criteria cr = session.createCriteria(Admin.class);
        cr.add(Restrictions.eq("username", userName));
        cr.add(Restrictions.eq("password", password));
        List res = cr.list();
        if (res.size() > 0) {
            System.out.println("List not empty");
            Admin admin = (Admin) res.get(0);
            if (admin != null) {
                //System.out.println(admin.getPrivilege());
                return "mainpoints.xhtml";
            } else {
                System.out.println("Admin is null");
                return "login_failed.xhtml";
            }
        } else {
            System.out.println("List is empty");
            return "login_failed.xhtml";
        }

    }
    
    public Admin getAdminByUserName(String username){
        Criteria criteria = this.session.createCriteria(Admin.class);
        criteria.add(Restrictions.eq("username", username));
        Admin admin =(Admin) criteria.uniqueResult();
        try{
            session.refresh(admin);
            return admin;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//
//        AdminDAO adao = HibernateUtil.getADMIN_DAO();
//        String res = adao.adminLogin("rana", "123");
//        System.out.println(res);
//    }
}
