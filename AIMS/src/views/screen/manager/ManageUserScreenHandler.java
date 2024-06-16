package views.screen.manager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.ManagerScreenController;
import entity.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Utils;
import utils.Utils.CHECK;
import views.screen.BaseScreenHandler;

public class ManageUserScreenHandler extends BaseScreenHandler implements Initializable{
	
	@FXML
    private Button userBtn;
    @FXML
    private AnchorPane userForm;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, Integer> userIDCol;
    @FXML
    private TableColumn<User, String> userUsernameCol, userAddressCol, userPhoneNumberCol, userEmailCol, userRoleCol;
    @FXML
    private TextField userAddressField, userEmailField, userPhoneNumberField, userNameField;
    @FXML
    private Label userLabelForm;
    @FXML
    private Button saveCreateUserBtn, saveUpdateUserBtn, banUserBtn, createUserBtn, updateUserBtn, deleteUserBtn, changeUserPasswordBtn;
    @FXML	
    private AnchorPane subUserForm;
    @FXML
    private  ChoiceBox<String> roleChoice;
    @FXML
    private AnchorPane changePasswordForm;
    @FXML
    private Button saveChangePassword;
    @FXML
    private TextField newPasswordField, confirmPasswordField;

	public ManageUserScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
		userForm.setVisible(true);
	}
	
	public ManagerScreenController getBController() {
        return (ManagerScreenController) super.getBController();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setBController(new ManagerScreenController());
        try {
            showAllUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }	
	}
	
	public void showAllUser() throws SQLException{
	    List<User> listUser = getBController().getAllUser();
	    System.out.println(new PropertyValueFactory<User, String>("province"));
	    roleChoice.getItems().setAll("Admin", "Product Manager");
	    userIDCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
	    userUsernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
	    userEmailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
	    userAddressCol.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
	    userPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
	    userRoleCol.setCellValueFactory(new PropertyValueFactory<User, String>("role"));;
	    
	    userTableView.getItems().setAll(listUser);
	
	    subUserForm.setVisible(false);
	    changePasswordForm.setVisible(false);
	
	    userTableView.setRowFactory(tv -> new TableRow<User>() {
	        @Override
	        public void updateItem(User item, boolean empty) {
	            super.updateItem(item, empty) ;
	            String AdminStyle = "-fx-font-weight: bold";
	            String BanUserStyle = "-fx-background-color: red";
	            String style = "";
	            if (item == null) {
	                setStyle("");
	            } else if (item.getBan()) {
	                style = BanUserStyle;
	            } else {
	                style = "";
	            };
	
	            if (item == null) {
	                setStyle("");
	            } else if ("admin".equals(item.getRole())) {
	                style = style +  ";" +AdminStyle;
	            }
	
	            setStyle(style);
	        }
	    });
	}
	
	public void setCreateUserBtn() {
        userLabelForm.setText("Create User");
        subUserForm.setVisible(true);
        saveUpdateUserBtn.setVisible(false);
        saveCreateUserBtn.setVisible(true);
        changePasswordForm.setVisible(false);

        userNameField.setText("");
        userAddressField.setText("");
        userPhoneNumberField.setText("");
        userEmailField.setText("");
    }

    public void setUpdateUserBtn() {
        changePasswordForm.setVisible(false);
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        System.out.println(selectedUser);
        if (selectedUser != null) {        	
            userLabelForm.setText("Edit User");
            subUserForm.setVisible(true);
            saveUpdateUserBtn.setVisible(true);
            saveCreateUserBtn.setVisible(false);

            userNameField.setText(selectedUser.getUsername());
            userAddressField.setText(selectedUser.getAddress());
            userPhoneNumberField.setText(selectedUser.getPhone());
            userEmailField.setText(selectedUser.getEmail());
            String role = selectedUser.getRole().toLowerCase();
            roleChoice.setValue(role.substring(0, 1).toUpperCase() + role.substring(1));
        } else {
            subUserForm.setVisible(false);
        }
    }

    public void setBanUserBtn() throws SQLException {
        subUserForm.setVisible(false);
        changePasswordForm.setVisible(false);
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to ban / unban this user?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.YES) {
                getBController().banUser(selectedUser.getId(), selectedUser.getBan());
                showAllUser();
            }
        }
    }

    public void setDeleteUserBtn() throws SQLException {
        subUserForm.setVisible(false);
        changePasswordForm.setVisible(false);
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.YES) {
                getBController().deleteUser(selectedUser.getId());
                showAllUser();
            }
                    }
    }

    public void setChangeUserPasswordBtn() throws SQLException {
        changePasswordForm.setVisible(true);
        subUserForm.setVisible(false);
    }

    public void setSaveCreateUserBtn() throws SQLException {
        String username = userNameField.getText();
        String address = userAddressField.getText();
        String phone = userPhoneNumberField.getText();
        String email = userEmailField.getText();
        String role_str = roleChoice.getSelectionModel().getSelectedItem().toString().toLowerCase();
        String default_password = "123123";
        int numberOfUser = getBController().getAllUser().size();
        int id = numberOfUser + 1;

        CHECK check_phone = Utils.checkPhoneNumber(phone);
        CHECK check_email = Utils.checkEmail(email);

        if (check_phone == CHECK.WRONG_PHONENUMBER || check_email == CHECK.WRONG_EMAIL) {
            showAlert(Alert.AlertType.WARNING, "Fail to create new user", "Enter information again please", "Enter information again please");
        } else {
        	if(Utils.usernameExists(username)) {
        		showAlert(Alert.AlertType.WARNING, "Fail to create new user", "Username already exists", "Please choose a different username");
        	}else {
        		getBController().createUser(id, username, email, address, phone, role_str, default_password);
        		showAllUser();	
        	}
        }
    }

    public void setSaveUpdateUserBtn() throws SQLException{
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId();
            String name = userNameField.getText();
            String address = userAddressField.getText();
            String phone = userPhoneNumberField.getText();
            String email = userEmailField.getText();
            String role_str = roleChoice.getSelectionModel().getSelectedItem().toString().toLowerCase();
            
            CHECK check_phone = Utils.checkPhoneNumber(phone);
            CHECK check_email = Utils.checkEmail(email);

            if (check_phone == CHECK.WRONG_PHONENUMBER || check_email == CHECK.WRONG_EMAIL) {
                showAlert(Alert.AlertType.WARNING, "Fail to change user information", "Enter again please", "Enter again please");
            } else {
                getBController().updateUser(id, name, email, address, phone, role_str);
                showAllUser();
            }
        } else {
            subUserForm.setVisible(false);
        }
    }

    public void setSaveChangePassword() throws SQLException, Exception{
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        int id = selectedUser.getId();
        if (selectedUser != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change password?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.YES) {
                String newPassword = newPasswordField.getText();
                String confirmPassword =  confirmPasswordField.getText();
                    if (newPassword .compareTo(confirmPassword)  != 0) {
                    showAlert(Alert.AlertType.ERROR, "Wrong confirm password " + newPassword + confirmPassword, "The new password is not the same with the confirm password",
                            "Please enter confirm password again.");
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
}
