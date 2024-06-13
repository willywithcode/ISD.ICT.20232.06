package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

public class LoginScreenHandler extends BaseScreenHandler{
	
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

        } catch (Exception ex) {
//            PopupScreen.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
