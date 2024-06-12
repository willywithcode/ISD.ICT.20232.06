package views.screen.mediaDetail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import controller.HomeController;
import controller.PlaceOrderController;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.home.HomeScreenHandler;

public class MediaScreenHandler extends BaseScreenHandler{
	private static Logger LOGGER = Utils.getLogger(CartScreenHandler.class.getName());
//    @FXML
//    private ImageView aimsImage;
    @FXML
    private Label pageTitle;
    @FXML
    private VBox vboxDetail;
    
	public MediaScreenHandler(Stage stage, String screenPath, Media media) throws IOException {
		super(stage, screenPath);
		
//		File file = new File("assets/images/Logo.png");
//        Image im = new Image(file.toURI().toString());
//        aimsImage.setImage(im);
//
        //on mouse clicked, we back to home
//        aimsImage.setOnMouseClicked(e -> {
//            homeScreenHandler.show();
//        });

        media.getDetailScreen(vboxDetail);
        
	}
	
	/**
     * @param prevScreen
     * @throws SQLException
     */
    public void requestToView(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        setScreenTitle("Media detail");
        show();
    }
    
    /**
     * @param event
     */
    @FXML
    private void handleBack(MouseEvent event){
        // Back to previous screen
    	this.getPreviousScreen().show();
    }
    
    /**
     * @return HomeController
     */
    public HomeController getBController() {
        return (HomeController) super.getBController();
    }
}
