package views.screen.home;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import controller.LoginController;
import controller.SignUpController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import utils.Utils.CHECK;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;
import entity.db.ProvincesDB;
import entity.user.User;
import utils.Utils.CHECK;;

public class SignUpScreenHandler extends BaseScreenHandler implements Initializable{
	
	@FXML
	private TextField username, password, email, phone, address;
	
	@FXML
	private Button createBtn, backBtn;
	
	@FXML
	private ChoiceBox provinces, districts, wards;
	
	@FXML
	private ImageView logo;

	public SignUpScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}
	
	public SignUpController getBController() {
        return (SignUpController) super.getBController();
    }
	
	private enum CHECK {
        WRONG_ADDRESS,
        WRONG_PHONENUMBER,
        RIGHT_PHONENUMBER,
        WRONG_NAME,
        WRONG_EMAIL,
        RIGHT_EMAIL,
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		// TODO Auto-generated method stub
        List<String> provincesList = Utils.getProvincesList();
        ObservableList<String> provinces_ = FXCollections.observableArrayList(provincesList);
        provinces.setItems(provinces_); 
        provinces.setOnAction(event -> updateDistricts((String) provinces.getValue()));  
	}
	
	private void updateDistricts(String selectedProvince){
//		System.out.println(selectedProvince);
		if (selectedProvince != null) {
            List<String> districtsList = Utils.getDistrictsList(selectedProvince);
            ObservableList<String> districts_ = FXCollections.observableArrayList(districtsList);
            districts.setItems(districts_);
            districts.setValue(districts_.get(0));
            districts.setOnAction(event -> updateWards((String) districts.getValue()));
        }
	}
	
	private void updateWards(String selectedDistrict) {
		if (selectedDistrict != null) {
            List<String> wardsList = Utils.getWardsList(selectedDistrict);
            ObservableList<String> wards_ = FXCollections.observableArrayList(wardsList);
            wards.setItems(wards_);
            wards.setValue(wards_.get(0));
        }
	}
	
	@FXML
	void clickCreateBtn(MouseEvent event) throws IOException, InterruptedException, SQLException {
		String usernameText = username.getText().trim();
	    String passwordText = password.getText().trim();
	    String emailText = email.getText().trim();
	    String phoneText = phone.getText().trim();
	    String addressText = address.getText().trim();
	    String provinceText = (String) provinces.getValue();
	    String districtText = (String) districts.getValue();
	    String wardText = (String) wards.getValue();
	    
	    if (usernameText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty() ||
	            addressText.isEmpty() || provinceText == null || districtText == null || wardText == null) {
	        // If any required field is empty, show an error message
	        PopupScreen.error("Please fill in all fields.");
	        return;
	    }else {
	    	int numberOfUser = getBController().getAllUser().size();
	        int id = numberOfUser + 1;
	        
	        utils.Utils.CHECK check_phone = Utils.checkPhoneNumber(phoneText);
            utils.Utils.CHECK check_email = Utils.checkEmail(emailText);
            
            if (check_phone == utils.Utils.CHECK.WRONG_PHONENUMBER || check_email == utils.Utils.CHECK.WRONG_EMAIL) {
            	PopupScreen.error("Please refill the email or phone number");
            } else {
            	if(Utils.usernameExists(usernameText)) {
            		PopupScreen.error("This username has already existed");
            	}else {
            		getBController().createUser(id, usernameText, emailText, addressText, phoneText, "user", passwordText, provinceText, districtText, wardText);
                    User user = new User();
                    user.setId(id);
                    user.setUsername(usernameText);
                    user.setAddress(addressText);
                    user.setPhone(phoneText);
                    user.setRole("user");
                    user.setPassword(passwordText);
                    user.setProvince(provinceText);
                    user.setDistrict(districtText);
                    user.setWard(wardText);
                    LoginScreenHandler loginScreen;
                    loginScreen = new LoginScreenHandler(this.stage, Configs.LOGIN_SCREEN_PATH);
                    loginScreen.setHomeScreenHandler(this.homeScreenHandler);
                    loginScreen.setBController(new LoginController());
                    loginScreen.show();
            	}
            }
	    }
	}
	
	@FXML
    void backToHomeScreen(MouseEvent event) throws IOException, InterruptedException, SQLException {
		this.homeScreenHandler.show();
    }
}
