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

    public String login(String username, String password) throws Exception {
        String role;
        try {
        	User user = authenticateUser(username, password);
        	role = user.getRole();
        	boolean isBan = user.getBan();
        	if (isBan) throw new FailLoginDueToBannedException();
            if (Objects.isNull(user)) throw new FailLoginException();
        }catch (SQLException ex) {
            throw new FailLoginException();
        }
        return role;
    }
    
    private User authenticateUser(String username, String encryptedPassword) throws SQLException {
        return new User().authenticate(username, encryptedPassword);
    }

 

//    public void logout() {
//        SessionInformation.mainUser = null;
//        SessionInformation.expiredTime = null;
//    }

}