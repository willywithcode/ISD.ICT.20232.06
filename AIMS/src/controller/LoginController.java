package controller;

import common.exception.FailLoginException;
import common.exception.FailLoginDueToBannedException;
import entity.db.AIMSDB;
import entity.user.User;
import javafx.scene.control.Alert;
import utils.Utils;
import views.screen.popup.PopupScreen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * This class is responsible for handling the login process
 * It will authenticate the user and return the user object if the user is authenticated
 * Function cohesion is high because it only handles the login process. Communication cohesion is high because it only communicates with the User entity
 */
public class LoginController extends BaseController {

//    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    public User login(String username, String password) throws Exception {
        List<String> role;
        try {
        	User user = authenticateUser(username, password);
        	if (Objects.isNull(user)) {
        		PopupScreen.error("Wrong password or username. Please try again!!");
        		throw new FailLoginException();
        	}
        	role = user.getRoles();
        	boolean isBan = user.getBan();
        	if (isBan) {
        		PopupScreen.error("This account is banned. Contact with admin for more information");
        		throw new FailLoginDueToBannedException();
        	}
        	
        	return user;
        }catch (SQLException ex) {
            throw new FailLoginException();
        }
    }
    
    private User authenticateUser(String username, String password) throws SQLException {
        return new User().authenticate(username, password);
    }

 

//    public void logout() {
//        SessionInformation.mainUser = null;
//        SessionInformation.expiredTime = null;
//    }

}