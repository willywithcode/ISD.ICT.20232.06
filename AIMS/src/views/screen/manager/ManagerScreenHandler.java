package views.screen.manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
//import com.sun.media.jfxmedia.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entity.user.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import views.screen.BaseScreenHandler;
import controller.ManagerScreenController;

public class ManagerScreenHandler extends BaseScreenHandler implements Initializable {
	
	@FXML
    private TabPane tabPane;

    @FXML
    private Tab manageUserTab, manageMediaTab;
    
    private ManageUserScreenHandler manageUserScreenHandler;
    private ManageMediaScreenHandler manageMediaScreenHandler;
	
    public ManagerScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        // TODO Auto-generated constructor stub
    }
    public ManagerScreenController getBController() {
        return (ManagerScreenController) super.getBController();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	try {
    		manageUserScreenHandler = new ManageUserScreenHandler(this.stage, Configs.MANAGE_USER_SCREEN);
            manageMediaScreenHandler = new ManageMediaScreenHandler(this.stage, "/views/fxml/manageMedia.fxml");

            manageUserTab.setContent(manageUserScreenHandler.getContent());
            manageMediaTab.setContent(manageMediaScreenHandler.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void logout(ActionEvent e) throws SQLException {
	    this.homeScreenHandler.show();
	}

}
