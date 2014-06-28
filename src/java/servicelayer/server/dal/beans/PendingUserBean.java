/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servicelayer.server.dal.beans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import servicelayer.server.dal.daos.PendingUserDAO;
import servicelayer.server.dal.pojos.PendingUser;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "pendingUsers")
@SessionScoped
public class PendingUserBean {
        private List<PendingUser> pendingUsersList;
        private DataModel<PendingUser> pendingUserModel ;
        private PendingUserDAO pendingUserDAO;
        private PendingUser pendingUser;
        
        public PendingUserBean(){
            pendingUser = new PendingUser();
            pendingUserDAO = HibernateUtil.getPENDING_USER_DAO();
            pendingUsersList = pendingUserDAO.getAllPendingUsers();
            pendingUserModel = new ListDataModel<PendingUser>(pendingUsersList);
        }

        public List<PendingUser> getPendingUsersList() {
            return pendingUsersList;
        }

        public void setPendingUsersList(List<PendingUser> pendingUsersList) {
            this.pendingUsersList = pendingUsersList;
        }

        public DataModel<PendingUser> getPendingUserModel() {
            return pendingUserModel;
        }

        public void setPendingUserModel(DataModel<PendingUser> pendingUserModel) {
            this.pendingUserModel = pendingUserModel;
        }

        public PendingUserDAO getPendingUserDAO() {
            return pendingUserDAO;
        }

        public void setPendingUserDAO(PendingUserDAO pendingUserDAO) {
            this.pendingUserDAO = pendingUserDAO;
        }

        public PendingUser getPendingUser() {
            return pendingUser;
        }

        public void setPendingUser(PendingUser pendingUser) {
            this.pendingUser = pendingUser;
        }

        public void restore(){
            pendingUsersList = pendingUserDAO.getAllPendingUsers();
            pendingUserModel = new ListDataModel<PendingUser>(pendingUsersList);
        }
}
