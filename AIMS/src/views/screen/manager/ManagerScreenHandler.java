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
	
	User currentUser;
	
	@FXML
    private TabPane tabPane;
	
	@FXML
	private Button changePassBtn;
	
	@FXML
	private AnchorPane changePasswordForm;
	
	@FXML
    private TextField newPasswordField, confirmPasswordField;
    
    private ManageUserScreenHandler manageUserScreenHandler;
    private ManageMediaScreenHandler manageMediaScreenHandler;
	
    public ManagerScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        // TODO Auto-generated constructor stub
        manageUserScreenHandler = new ManageUserScreenHandler(this.stage, Configs.MANAGE_USER_SCREEN);
        manageMediaScreenHandler = new ManageMediaScreenHandler(this.stage, "/views/fxml/manageMedia.fxml");
        
        changePasswordForm.setVisible(false);
        
        changePassBtn.setOnAction(event -> {
        	if(currentUser != null) {
        		changePasswordForm.setVisible(true);
        	}
        });
    }
    
    public ManagerScreenController getBController() {
        return (ManagerScreenController) super.getBController();
    }

    public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) throws IOException {
		this.currentUser = currentUser;
		setupTabs();
	}
	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
	
	private void setupTabs() throws IOException {
        if (currentUser == null) return;

        List<String> roles = currentUser.getRoles();
		if (roles.contains("admin")) {
			Tab manageUserTab = new Tab("Manage User");
		    manageUserTab.setContent(manageUserScreenHandler.getContent());
		    tabPane.getTabs().add(manageUserTab);
		}

		if (roles.contains("product manager")) {
			Tab manageMediaTab = new Tab("Manage Media");
		    manageMediaTab.setContent(manageMediaScreenHandler.getContent());
		    tabPane.getTabs().add(manageMediaTab);
		}
    }
	
	public void setSaveChangePassword() throws SQLException, Exception {
        if (currentUser != null) {
            int id = currentUser.getId();
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change password?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.YES) {
                String newPassword = newPasswordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                if (!newPassword.equals(confirmPassword)) {
                    showAlert(Alert.AlertType.ERROR, "Wrong confirm password", "The new password is not the same with the confirm password", "Please enter confirm password again.");
                } else {
                    getBController().changePassword(id, newPassword);
                    changePasswordForm.setVisible(false);
                }
            }
        }
    }
	
	private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

	public void logout(ActionEvent e) throws SQLException {
	    this.homeScreenHandler.show();
	}

}
