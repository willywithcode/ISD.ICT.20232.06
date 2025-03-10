package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.ViewCartException;
import controller.HomeController;
import controller.LoginController;
import controller.ViewCartController;
import controller.ViewOrderController;
import entity.cart.Cart;
import entity.media.Media;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.mediaDetail.MediaScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.viewOrder.ViewOrderScreenHandler;

public class HomeScreenHandler extends BaseScreenHandler implements Initializable {

	public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());

    @FXML
    private Label numMediaInCart;
    
    @FXML
    private Label pageTitle;

//    @FXML
//    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private HBox hboxMedia;

    @FXML
    private Button loginBtn;

    @FXML
    private Button orderBtn;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private SplitMenuButton splitMenuBtnSort;

    @FXML
    private Pagination pagination;

    private List homeItems;

    private List homeSearchItems;
    private List pageItems;

    public HomeScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }
    
    /**
     * @return Label
     */
    public Label getNumMediaCartLabel() {
        return this.numMediaInCart;
    }

    /**
     * @return HomeController
     */
    public HomeController getBController() {
        return (HomeController) super.getBController();
    }

    private void setNumOfPage(List listItem) {
        this.pagination.setPageCount((int) Math.ceil((double) listItem.size()/20));
    }

    private VBox createPage(int pageIndex) {
        this.pageItems = new ArrayList<>();
        for(int index = 20*pageIndex; index < 20*(pageIndex+1); index++) {
            if(index < homeSearchItems.size()) {
                pageItems.add(homeSearchItems.get(index));
            }
        }
        VBox vboxMedia = new VBox();
        addMediaHome(pageItems);
        return vboxMedia;
    }
    
    public void setup() {
    	numMediaInCart.setText(String.valueOf(Cart.getCart().getTotalMedia()));
    	try {
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList<>();
            for (Object object : medium) {
                Media media = (Media) object;
                MediaHomeHandler m1 = new MediaHomeHandler(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }

        } catch (SQLException | IOException e) {
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }
    	homeSearchItems = homeItems;
        setNumOfPage(homeSearchItems);
        addMediaHome(homeSearchItems);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
    }

    @Override
    public void show() {
    	setup();
    	super.show();
    }
    @FXML
    void viewOrder() throws IOException {
        System.out.println("View Order");
        ViewOrderController viewOrderController = new ViewOrderController();
        ViewOrderScreenHandler viewOrderScreenHandler = new ViewOrderScreenHandler(this.stage, Configs.VIEW_ORDER_SCREEN_PATH);
        viewOrderScreenHandler.setBController(viewOrderController);
        viewOrderScreenHandler.setHomeScreenHandler(this);
        viewOrderScreenHandler.show();
    }
    
    /**aq1
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBController(new HomeController());
        setup();
        pageTitle.setOnMouseClicked(e -> {
        	setup();
        });
        cartImage.setOnMouseClicked(e -> {
            CartScreenHandler cartScreen;
            try {
                LOGGER.info("User clicked to view cart");
                cartScreen = new CartScreenHandler(this.stage, Configs.CART_SCREEN_PATH);
                cartScreen.setPreviousScreen(this);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });
        loginBtn.setOnMouseClicked(e -> {
            loginButtonClicked();
        });
//        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);
        sortByAscendingPrice(0, "Price: Low to Hight", splitMenuBtnSort);
        sortByDescendingPrice(1, "Price: Hight to Low", splitMenuBtnSort);
        splitMenuBtnSearch.setOnMouseClicked(e -> {
            handleSearch();
        });
    }
    
    public void loginButtonClicked() {
    	LoginScreenHandler loginScreen;
        try {
            LOGGER.info("User click to login");
            loginScreen = new LoginScreenHandler(this.stage, Configs.LOGIN_SCREEN_PATH);
            loginScreen.setHomeScreenHandler(this);
            loginScreen.setBController(new LoginController());
            loginScreen.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    
    public void openMediaDetail(Media media) {
    	MediaScreenHandler mediaScreen;
        try {
            mediaScreen = new MediaScreenHandler(this.stage, Configs.MEDIA_DETAIL_PATH, media);
            mediaScreen.setPreviousScreen(this);
            mediaScreen.setHomeScreenHandler(this);
            mediaScreen.setBController(getBController());
            mediaScreen.requestToView(this);
        } catch (IOException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void setImage() {
        // fix image path caused by fxml
//        File file1 = new File(Configs.IMAGE_PATH + "/" + "Logo.png");
//        Image img1 = new Image(file1.toURI().toString());
//        aimsImage.setImage(img1);

        File file2 = new File(Configs.IMAGE_PATH + "/" + "cart.png");
        Image img2 = new Image(file2.toURI().toString());
        cartImage.setImage(img2);
    }

    /**
     * @param items
     */
    public void addMediaHome(List items){
        ArrayList mediaItems = (ArrayList)((ArrayList) items).clone();
        hboxMedia.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });

        while (!mediaItems.isEmpty()) {
        	int maxItemsPerCol = 5;
        	while (maxItemsPerCol > 0 && !mediaItems.isEmpty()) {
        		hboxMedia.getChildren().forEach(node -> {
                    VBox vBox = (VBox) node;
                    if (!mediaItems.isEmpty()) {
                        MediaHomeHandler media = (MediaHomeHandler) mediaItems.get(0);
                        vBox.getChildren().add(media.getContent());
                        mediaItems.remove(media);
                    }
                });
        		maxItemsPerCol--;
        	}
        }

    }

    /**
     * @param position
     * @param text
     * @param menuButton
     */
    private void addMenuItem(int position, String text, MenuButton menuButton) {
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            try {
                List medium = getBController().handleFilter(text);
                this.homeSearchItems = new ArrayList<>();
                for (Object object : medium) {
                    Media media = (Media) object;
                    MediaHomeHandler m1 = new MediaHomeHandler(Configs.HOME_MEDIA_PATH, media, this);
                    this.homeSearchItems.add(m1);
                }
            } catch (SQLException | IOException ex) {
                LOGGER.info("Errors occured: " + ex.getMessage());
                ex.printStackTrace();
            }
            setNumOfPage(homeSearchItems);
            addMediaHome(homeSearchItems);
        });
        menuButton.getItems().add(position, menuItem);
    }

    public void handleSearch() {
        String text = textFieldSearch.getText();
        if(!text.isBlank()) {
	        try {
	            List medium = getBController().searchMedia(text);
	            if(medium.size() == 0) {
	                PopupScreen.error("No products found");
	                addMediaHome(homeItems);
	                setNumOfPage(homeItems);
	            }  else {
	                this.homeSearchItems = new ArrayList<>();
	                for (Object object : medium) {
	                    Media media = (Media) object;
	                    MediaHomeHandler m1 = new MediaHomeHandler(Configs.HOME_MEDIA_PATH, media, this);
	                    this.homeSearchItems.add(m1);
	                }
	                addMediaHome(homeSearchItems);
	                setNumOfPage(homeSearchItems);
	            }
	        } catch (SQLException | IOException e) {
	            LOGGER.info("Errors occured: " + e.getMessage());
	            e.printStackTrace();
	        }
        }
        textFieldSearch.setText("");
    }

    private void sortByAscendingPrice(int position, String text, MenuButton menuButton) {
        List<MediaHomeHandler> sortedItems = new ArrayList<>(homeItems);
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            this.homeSearchItems = SortHomeScreen.sortByAscendingPrice(homeSearchItems);
            addMediaHome(homeSearchItems);
        });
        menuButton.getItems().add(position,menuItem );
    }

    private void sortByDescendingPrice(int position, String text, MenuButton menuButton) {
        List<MediaHomeHandler> sortedItems = new ArrayList<>(homeItems);
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            this.homeSearchItems = SortHomeScreen.sortByDescendingPrice(homeSearchItems);
            addMediaHome(homeSearchItems);
        });
        menuButton.getItems().add(position,menuItem );
    }
}
