/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.pojos.User;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "users")
@ViewScoped
public class UserBean {
    
    private List<User> usersList;
    private DataModel<User> userModel ;
    private UserDAO userDAO;
    private User user;
    
    public UserBean(){
        user = new User();
        userDAO = HibernateUtil.getUSER_DAO();
        usersList = userDAO.getAllUsers();
        userModel = new ListDataModel<User>(usersList);
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public DataModel<User> getUserModel() {
        return userModel;
    }

    public void setUserModel(DataModel<User> userModel) {
        this.userModel = userModel;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    public void restore(){
        usersList = userDAO.getAllUsers();
        userModel = new ListDataModel<User>(usersList);
        System.out.println("Calling restore method");
    }
}
