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
import servicelayer.server.dal.daos.DailyWinnersDAO;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.pojos.BlockedUser;
import servicelayer.server.dal.pojos.DailyWinner;
import servicelayer.server.dal.pojos.User;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author eng.banoota
 */
@ManagedBean(name = "winners")
@SessionScoped
public class WinnersBean {
    
    private List<DailyWinner> dailyWinnersList;
    private DataModel<DailyWinner> dailyWinnersModel ;
    private DailyWinnersDAO dailyWinnersDAO;
    private UserDAO userDAO;
    private DailyWinner dailyWinner;
    private User user;
    
    public WinnersBean(){
        dailyWinner = new DailyWinner();
        user = new User();
        dailyWinnersDAO = HibernateUtil.getDAILY_WINNERS_DAO();
        userDAO = HibernateUtil.getUSER_DAO();
        dailyWinnersList = dailyWinnersDAO.getAllWinners();
        dailyWinnersModel = new ListDataModel<DailyWinner>(dailyWinnersList);
    }

    public List<DailyWinner> getDailyWinnersList() {
        return dailyWinnersList;
    }

    public void setDailyWinnersList(List<DailyWinner> dailyWinnersList) {
        this.dailyWinnersList = dailyWinnersList;
    }

    public DataModel<DailyWinner> getDailyWinnersModel() {
        return dailyWinnersModel;
    }

    public void setDailyWinnersModel(DataModel<DailyWinner> dailyWinnersModel) {
        this.dailyWinnersModel = dailyWinnersModel;
    }

    public DailyWinnersDAO getDailyWinnersDAO() {
        return dailyWinnersDAO;
    }

    public void setDailyWinnersDAO(DailyWinnersDAO dailyWinnersDAO) {
        this.dailyWinnersDAO = dailyWinnersDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public DailyWinner getDailyWinner() {
        return dailyWinner;
    }

    public void setDailyWinner(DailyWinner dailyWinner) {
        this.dailyWinner = dailyWinner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void restore(){
        dailyWinnersList = dailyWinnersDAO.getAllWinners();
        dailyWinnersModel = new ListDataModel<DailyWinner>(dailyWinnersList);
    }
    
}
