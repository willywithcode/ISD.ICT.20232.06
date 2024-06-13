package controller;

import common.exception.FailLoginException;
import common.exception.FailLoginDueToBannedException;
import entity.db.AIMSDB;
import entity.user.User;
import javafx.scene.control.Alert;
import utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Logger;

public class LoginController extends BaseController {

    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    public    int login(String email, String password) throws Exception {
//        LOGGER.info(email);
//        LOGGER.info(Utils.md5(password));
        int role;
        try {
        	User user = authenticateUser(email, Utils.md5(password));
            role = user.getRole();
            boolean isBan = user.getBan();
            if (isBan) throw new FailLoginDueToBannedException();
            // Log user details
            if (Objects.isNull(user)) throw new FailLoginException();
//            SessionInformation.mainUser = user;
//            SessionInformation.expiredTime = LocalDateTime.now().plusHours(24);
        } catch (SQLException ex) {
            throw new FailLoginException();
        }
        return role;
    }
    
    private User authenticateUser(String email, String encryptedPassword) throws SQLException {
        return new User().authenticate(email, encryptedPassword);
    }

 

//    public void logout() {
//        SessionInformation.mainUser = null;
//        SessionInformation.expiredTime = null;
//    }

}
