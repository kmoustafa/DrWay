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
import servicelayer.server.dal.daos.BlockedUserDAO;
import servicelayer.server.dal.pojos.BlockedUser;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "blockedUsers")
@SessionScoped
public class BlockedUserBean {
        private List<BlockedUser> blockedUsersList;
        private DataModel<BlockedUser> blockedUserModel ;
        private BlockedUserDAO blockedUserDAO;
        private BlockedUser blockedUser;
        
        public BlockedUserBean(){
            blockedUser = new BlockedUser();
            blockedUserDAO = HibernateUtil.getBlockedUserDAO();
            blockedUsersList = blockedUserDAO.getAllPendingUsers();
            blockedUserModel = new ListDataModel<BlockedUser>(blockedUsersList);
        }

    public List<BlockedUser> getBlockedUsersList() {
        return blockedUsersList;
    }

    public void setBlockedUsersList(List<BlockedUser> blockedUsersList) {
        this.blockedUsersList = blockedUsersList;
    }

    public DataModel<BlockedUser> getBlockedUserModel() {
        return blockedUserModel;
    }

    public void setBlockedUserModel(DataModel<BlockedUser> blockedUserModel) {
        this.blockedUserModel = blockedUserModel;
    }

    public BlockedUserDAO getBlockedUserDAO() {
        return blockedUserDAO;
    }

    public void setBlockedUserDAO(BlockedUserDAO blockedUserDAO) {
        this.blockedUserDAO = blockedUserDAO;
    }

    public BlockedUser getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(BlockedUser blockedUser) {
        this.blockedUser = blockedUser;
    }
        
    public void restore(){
        blockedUsersList = blockedUserDAO.getAllPendingUsers();
        blockedUserModel = new ListDataModel<BlockedUser>(blockedUsersList);
    }
}
