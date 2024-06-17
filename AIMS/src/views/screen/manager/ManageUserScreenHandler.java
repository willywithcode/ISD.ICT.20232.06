package views.screen.manager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

public class ManageUserScreenHandler extends BaseScreenHandler implements Initializable {

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
    private ListView<String> roleListView;
    @FXML
    private AnchorPane changePasswordForm;
    @FXML
    private Button saveChangePassword;
    @FXML
    private TextField newPasswordField, confirmPasswordField;

    private ObservableList<String> roles;
    private ObservableList<String> selectedRoles;

    public ManageUserScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        userForm.setVisible(true);
    }

    public ManagerScreenController getBController() {
        return (ManagerScreenController) super.getBController();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBController(new ManagerScreenController());
        try {
            roles = FXCollections.observableArrayList("admin", "product manager");
            selectedRoles = FXCollections.observableArrayList();
            roleListView.setItems(roles);
            roleListView.setCellFactory(param -> new ListCell<String>() {
                private final CheckBox checkBox = new CheckBox();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        checkBox.setText(item);
                        checkBox.setSelected(selectedRoles.contains(item));
                        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                            if (isNowSelected) {
                                selectedRoles.add(item);
                            } else {
                                selectedRoles.remove(item);
                            }
                        });
                        setGraphic(checkBox);
                    } else {
                        setGraphic(null);
                    }
                }
            });
            showAllUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAllUser() throws SQLException {
        List<User> listUser = getBController().getAllUser();
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        userAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        userPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userRoleCol.setCellValueFactory(new PropertyValueFactory<>("roles")); // Update to reflect roles

        userTableView.getItems().setAll(listUser);

        subUserForm.setVisible(false);
        changePasswordForm.setVisible(false);

        userTableView.setRowFactory(tv -> new TableRow<User>() {
            @Override
            public void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                String adminStyle = "-fx-font-weight: bold";
                String banUserStyle = "-fx-background-color: red";
                String style = "";
                if (item == null) {
                    setStyle("");
                } else if (item.getBan()) {
                    style = banUserStyle;
                } else {
                    style = "";
                }
                if (item != null && item.getRoles().contains("admin")) {
                    style += ";" + adminStyle;
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
        selectedRoles.clear();
    }

    public void setUpdateUserBtn() {
        changePasswordForm.setVisible(false);
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userLabelForm.setText("Edit User");
            subUserForm.setVisible(true);
            saveUpdateUserBtn.setVisible(true);
            saveCreateUserBtn.setVisible(false);

            userNameField.setText(selectedUser.getUsername());
            userAddressField.setText(selectedUser.getAddress());
            userPhoneNumberField.setText(selectedUser.getPhone());
            userEmailField.setText(selectedUser.getEmail());
            selectedRoles.setAll(selectedUser.getRoles());
            
         // Update the checkboxes in the roleListView
            roleListView.setCellFactory(param -> new ListCell<String>() {
                private final CheckBox checkBox = new CheckBox();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        checkBox.setText(item);
                        checkBox.setSelected(selectedRoles.contains(item));
                        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                            if (isNowSelected) {
                                selectedRoles.add(item);
                            } else {
                                selectedRoles.remove(item);
                            }
                        });
                        setGraphic(checkBox);
                    } else {
                        setGraphic(null);
                    }
                }
            });
        } else {
            subUserForm.setVisible(false);
        }
    }

    public void setBanUserBtn() throws SQLException {
        subUserForm.setVisible(false);
        changePasswordForm.setVisible(false);
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to ban / unban this user?", ButtonType.YES, ButtonType.NO);
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
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
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
        List<String> roles = new ArrayList<>(selectedRoles);
        String defaultPassword = "123123";
        int numberOfUser = getBController().getAllUser().size();
        int id = numberOfUser + 1;

        CHECK checkPhone = Utils.checkPhoneNumber(phone);
        CHECK checkEmail = Utils.checkEmail(email);

        if (checkPhone == CHECK.WRONG_PHONENUMBER || checkEmail == CHECK.WRONG_EMAIL) {
            showAlert(Alert.AlertType.WARNING, "Fail to create new user", "Enter information again please", "Enter information again please");
        } else {
            if (Utils.usernameExists(username)) {
                showAlert(Alert.AlertType.WARNING, "Fail to create new user", "Username already exists", "Please choose a different username");
            } else {
                getBController().createUser(id, username, email, address, phone, roles, defaultPassword);
                showAllUser();
            }
        }
    }

    public void setSaveUpdateUserBtn() throws SQLException {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId();
            String name = userNameField.getText();
            String address = userAddressField.getText();
            String phone = userPhoneNumberField.getText();
            String email = userEmailField.getText();
            List<String> roles = new ArrayList<>(selectedRoles);

            CHECK checkPhone = Utils.checkPhoneNumber(phone);
            CHECK checkEmail = Utils.checkEmail(email);

            if (checkPhone == CHECK.WRONG_PHONENUMBER || checkEmail == CHECK.WRONG_EMAIL) {
                showAlert(Alert.AlertType.WARNING, "Fail to change user information", "Enter again please", "Enter again please");
            } else {
                getBController().updateUser(id, name, email, address, phone, roles);
                showAllUser();
            }
        } else {
            subUserForm.setVisible(false);
        }
    }

    public void setSaveChangePassword() throws SQLException, Exception {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int id = selectedUser.getId();
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
}
