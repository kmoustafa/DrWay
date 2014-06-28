/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.handlers;

import servicelayer.server.dal.daos.PendingUserDAO;
import servicelayer.server.dal.daos.UserDAO;
import servicelayer.server.dal.pojos.PendingUser;
import servicelayer.server.dal.pojos.User;
import servicelayer.server.util.ErrorCodesConstants;
import servicelayer.server.util.HibernateUtil;

/**
 *
 * @author Genuine
 */
public class VerificationHandler {

    PendingUser pendingUser;
    User user = new User();
    UserDAO userDAO = HibernateUtil.getUSER_DAO();
    PendingUserDAO pendingUserDAO = HibernateUtil.getPENDING_USER_DAO();

    public String VerifyUser(String pendingUserId, String verificationCode) {

        if (pendingUserId != null && verificationCode != null) {
            String temp = pendingUserDAO.getVerificationCode(Integer.parseInt(pendingUserId));
            System.out.println("temp: " + temp);
            if (!verificationCode.equals(temp)) {
                return ErrorCodesConstants.INVALID_VARIFICATION_CODE + "";
            } else {
                pendingUser = pendingUserDAO.getPendingUser(Integer.parseInt(pendingUserId));
                pendingUserDAO.deletePendingUser(pendingUser);
                user.setFuelLookUp(pendingUser.getFuelLookUp());
                user.setModeLookUp(pendingUser.getModeLookUp());
                user.setName(pendingUser.getName());
                user.setEmail(pendingUser.getEmail());
                user.setMobile(pendingUser.getMobile());
                user.setUserToken(pendingUser.getUserToken());
                user.setTokenType(pendingUser.getTokenType());
                userDAO.updateUser(user);
                return RegistrationHandler.getRandomAlphaNumeric(3) + user.getId() + RegistrationHandler.getRandomAlphaNumeric(4);
            }
        }
        return ErrorCodesConstants.INVALID_PARAMETER + "";
    }

//    public static void main(String[] args){ 
//        int test = new VerificationHandler().VerifyUser("14", "qAyyGe");
//        System.out.println(test);
//    }
}
