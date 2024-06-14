package views.screen.home;
//import controller.CRUDMediaController;
import controller.ManagerScreenController;
import controller.SignUpController;
import entity.user.User;
import controller.HomeController;
import controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import controller.LoginController;
import controller.ManagerScreenController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;

public class LoginScreenHandler extends BaseScreenHandler implements Initializable{

public static Logger LOGGER = Utils.getLogger(LoginScreenHandler.class.getName());
	
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
            User user = getBController().login(username.getText(), password.getText());
            if("admin".equals(user.getRole())) {
            	System.out.println("admin login");
            	ManagerScreenHandler managerScreen = new ManagerScreenHandler(this.stage, Configs.MANAGER_SCREEN_PATH);
            	managerScreen.setScreenTitle("User Manager");
                managerScreen.setBController(new ManagerScreenController());
                managerScreen.setHomeScreenHandler(homeScreenHandler);
                managerScreen.show();
            }else if("user".equals(user.getRole())) {
            	System.out.println("user login");
            	HomeScreenHandler homeScreen = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
            	homeScreen.setLoggedInUser(user);
            	homeScreen.setScreenTitle("Home Screen");
            	homeScreen.setBController(new HomeController());
            	homeScreen.setHomeScreenHandler(homeScreenHandler);
            	homeScreen.show();
            } else {
            	System.out.println("product manager login");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void backToHomeScreen(MouseEvent event) throws IOException, InterruptedException, SQLException {
        this.homeScreenHandler.show();
    }
    
    @FXML
    void signUp() throws IOException, InterruptedException, SQLException{
    	SignUpScreenHandler signupScreen = new SignUpScreenHandler(this.stage, Configs.SIGN_UP_SCREEN_PATH);
    	signupScreen.setHomeScreenHandler(homeScreenHandler);
    	signupScreen.setBController(new SignUpController());
    	signupScreen.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}

