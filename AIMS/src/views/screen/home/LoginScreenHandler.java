package views.screen.home;
<<<<<<< HEAD:AIMS/src/views/screen/home/LoginScreenHandler.java
=======
//import controller.CRUDMediaController;
import controller.ManagerScreenController;
import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
//import views.screen.cart.CartScreenHandler;
//import views.screen.manager.CRUDMediaScreenHandler;
import views.screen.popup.PopupScreen;
>>>>>>> 5b5c76c46a4906a53fb320a4286b17a93bfe3aa9:AIMS/src/main/java/views/screen/home/LoginScreenHandler.java

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

<<<<<<< HEAD:AIMS/src/views/screen/home/LoginScreenHandler.java
import controller.LoginController;
import controller.ManagerScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;

public class LoginScreenHandler extends BaseScreenHandler{
	
	public static Logger LOGGER = Utils.getLogger(LoginScreenHandler.class.getName());
=======

public class LoginScreenHandler extends BaseScreenHandler{

public static Logger LOGGER = Utils.getLogger(LoginScreenHandler.class.getName());
>>>>>>> 5b5c76c46a4906a53fb320a4286b17a93bfe3aa9:AIMS/src/main/java/views/screen/home/LoginScreenHandler.java
	
	@FXML
    private TextField username;

    @FXML
    private PasswordField password;

	public LoginScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
	}
	
	public LoginController getBController() {
        return (LoginController) super.getBController();
    }

	@FXML
    void login() throws IOException, InterruptedException, SQLException {
        System.out.println(password.getText());
        try {
            String role = getBController().login(username.getText(), password.getText());
            if("admin".equals(role)) {
            	System.out.println("admin login");
            	ManagerScreenHandler managerScreen = new ManagerScreenHandler(this.stage, Configs.MANAGER_SCREEN_PATH);
            	managerScreen.setScreenTitle("User Manager");
                managerScreen.setBController(new ManagerScreenController());
                managerScreen.setHomeScreenHandler(homeScreenHandler);
                managerScreen.show();
            }else {
            	System.out.println("user login");
            }

<<<<<<< HEAD:AIMS/src/views/screen/home/LoginScreenHandler.java
=======
//            PopupScreen.success("Login Successfully!");
//            if (role == 1) {
//                ManagerUserScreenHandler managerUserScreen = new ManagerUserScreenHandler(this.stage, Configs.MANAGER_USER_SCREEN_PATH);
//                managerUserScreen.setScreenTitle("User Manager");
//                managerUserScreen.setBController(new MangagerUserScreenController());
//                managerUserScreen.setHomeScreenHandler(homeScreenHandler);
//                managerUserScreen.show();
//            } else {
//                if (role == 0) {
//                    CRUDMediaScreenHandler crudMediaScreen = new CRUDMediaScreenHandler(this.stage, Configs.MANAGER_SCREEN_PATH);
//                    crudMediaScreen.setScreenTitle("Manager");
//                    crudMediaScreen.setBController(new CRUDMediaController());
//                    crudMediaScreen.setHomeScreenHandler(homeScreenHandler);
//                    crudMediaScreen.show();
//                }
//            }

>>>>>>> 5b5c76c46a4906a53fb320a4286b17a93bfe3aa9:AIMS/src/main/java/views/screen/home/LoginScreenHandler.java
        } catch (Exception ex) {
//            PopupScreen.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
<<<<<<< HEAD:AIMS/src/views/screen/home/LoginScreenHandler.java
}
=======

    @FXML
    void backToHomeScreen(MouseEvent event) throws IOException, InterruptedException, SQLException {
        this.homeScreenHandler.show();
    }
}
>>>>>>> 5b5c76c46a4906a53fb320a4286b17a93bfe3aa9:AIMS/src/main/java/views/screen/home/LoginScreenHandler.java
