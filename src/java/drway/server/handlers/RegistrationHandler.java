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
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Genuine
 */
public class RegistrationHandler {

    UserDAO userDAO = HibernateUtil.getUSER_DAO();
    PendingUserDAO pendingUserDAO = HibernateUtil.getPENDING_USER_DAO();
    PendingUser pendingUser;
    private static final String ALPHA_NUMERIC_STRING = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";

    public String EmailRegistration(String email, String name) {

        // checking if this email already exist as user or not 
        if (userDAO.getUserByEmail(email) != null) {
            System.out.println("Email already exist as active user");
            return ErrorCodesConstants.USER_EXISTS + "";
        } // checking if this email already exist as pending user or not 
        else if (pendingUserDAO.getPendingUserByEmail(email) != null) {
            System.out.println("Email already exist as pending user");
            return ErrorCodesConstants.USER_EXISTS_AS_PENDING + "";
        }

        String emailVerificationCode = getRandomAlphaNumeric(6);
        pendingUser = new PendingUser();
        pendingUser.setEmail(email);
        pendingUser.setName(name);
        pendingUser.setVerificationCode(emailVerificationCode);
        pendingUserDAO = HibernateUtil.getPENDING_USER_DAO();
        int ispendingUserUpdated = pendingUserDAO.updatePendingUser(pendingUser);
        //If the generated Verifcation code is found!!
        if (ispendingUserUpdated == 0) {
            try {
                String temp = sendCodeToEmail(pendingUser.getEmail(), emailVerificationCode);
                pendingUser.setVerificationCode(temp);
            } catch (MessagingException ex) {
                ex.printStackTrace();
                System.out.println("SENDING_VARIFICATION_CODE_FAILED");
                return ErrorCodesConstants.SENDING_VARIFICATION_CODE_FAILED + "";
            }
        }
        // Success
        String result = getRandomAlphaNumeric(3) + pendingUser.getId() + getRandomAlphaNumeric(4);
        return result;
    }

    public String socialRegistration(String email, String name) {

        if (userDAO.getUserByEmail(email) != null) {
            //User user = userDAO.getUserByEmail(email);
            System.out.println("Email already exist as active user");
//            return ErrorCodesConstants.USER_EXISTS + "";
            return getRandomAlphaNumeric(3) + userDAO.getUserByEmail(email).getId() + getRandomAlphaNumeric(4);
        } else {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            int res = userDAO.updateUser(user);
            if (res == 0) {
                return getRandomAlphaNumeric(3) + user.getId() + getRandomAlphaNumeric(4);
            } else {
                return ErrorCodesConstants.DATABASE_PROBLEM + "";
            }
        }
        //return 0;
    }

    public static String getRandomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public String sendCodeToEmail(String email, String emailVerificationCode) throws MessagingException {

        if (emailVerificationCode.isEmpty()) {
            emailVerificationCode = getRandomAlphaNumeric(6);
        }
        String to = email;
        String from = "noreply.drway@gmail.com";
//        String host = "localhost";
        Properties prop = System.getProperties();
        //prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.transport.protocol", "smtp");
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("noreply.drway@gmail.com", "eqidxklqqmttrldv"); //To change body of generated methods, choose Tools | Templates.
            }

        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Dr Way Verification Code");
        //The message should contain Verification Code and Client Url of enter verification code page
        message.setText("There is the verification code from Dr_way ");
        message.setText(emailVerificationCode);
        Transport.send(message);
        System.out.println("verCode in sendToemail: " + emailVerificationCode);
        return emailVerificationCode;
    }

//    public static void main(String[] args){
//        try {
//            //int test = new RegistrationHandler().EmailRegistration("eng.banoota@gmail.com","rana");
//            //System.out.println(test);
//            new RegistrationHandler().sendCodeToEmail("eng.banoota@gmail.com");
//        } catch (MessagingException ex) {
//            ex.printStackTrace();
//        }
//    }
}
