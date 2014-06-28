/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drway.server.util;

/**
 *
 * @author Kareem Moustafa
 */
public class ValidationUtil {

    public boolean isEmailValid(String email) {
        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(emailreg);

    }

    public boolean isMobileValid(String mobile) {
        return mobile.length() == 11;
    }
}
