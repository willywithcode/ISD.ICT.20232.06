package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.HomeController;
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
import views.screen.popup.PopupScreen;



/*
 * Violate SOLID:
 * SRP: The class has multiple responsibilities. It handles the initialization of the home screen, 
 * handles user interactions, performs search, filter and sort operations, and manages the display of media items. 
 * 
 */
public class HomeScreenHandler extends BaseScreenHandler implements Initializable {

    public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());

    @FXML
    private Label numMediaInCart;

    @FXML
    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private VBox vboxMedia1;

    @FXML
    private VBox vboxMedia2;

    @FXML
    private VBox vboxMedia3;

    @FXML
    private HBox hboxMedia;

    @FXML
    private Button loginBtn;

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

    @Override
    public void show() {
        numMediaInCart.setText(String.valueOf(Cart.getCart().getListMedia().size()) + " media");
        super.show();
    }

    private void setNumOfPage(List listItem) {
        this.pagination.setPageCount((int) Math.ceil((double) listItem.size()/12));
    }

    private VBox createPage(int pageIndex) {
        this.pageItems = new ArrayList<>();
        for(int index = 12*pageIndex; index < 12*(pageIndex+1); index++) {
            if(index < homeSearchItems.size()) {
                pageItems.add(homeSearchItems.get(index));
            }
        }
        VBox vboxMedia = new VBox();
        System.out.println("page: " + pageIndex + " - " + homeSearchItems.size());
        addMediaHome(pageItems);
        return vboxMedia;
    }

    /**
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBController(new HomeController());
        try {
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList<>();
            for (Object object : medium) {
                Media media = (Media) object;
                MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }

        } catch (SQLException | IOException e) {
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }
        homeSearchItems = homeItems;
        setNumOfPage(homeSearchItems);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });

        aimsImage.setOnMouseClicked(e -> {
            addMediaHome(this.homeItems);
        });

        cartImage.setOnMouseClicked(e -> {});
        
        loginBtn.setOnMouseClicked(e -> {});
        
        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);
        sortByAscendingPrice(0, "Price: Low to Hight", splitMenuBtnSort);
        sortByDescendingPrice(1, "Price: Hight to Low", splitMenuBtnSort);
        splitMenuBtnSearch.setOnMouseClicked(e -> {
            handleSearch();
        });
    }

    public void setImage() {
        // fix image path caused by fxml
        File file1 = new File(Configs.IMAGE_PATH + "/" + "Logo.png");
        Image img1 = new Image(file1.toURI().toString());
        aimsImage.setImage(img1);

        File file2 = new File(Configs.IMAGE_PATH + "/" + "cart.png");
        Image img2 = new Image(file2.toURI().toString());
        cartImage.setImage(img2);
    }

    /**
     * @param items
     */
    public void addMediaHome(List items) {
        ArrayList mediaItems = (ArrayList) ((ArrayList) items).clone();
        hboxMedia.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });
        while (!mediaItems.isEmpty()) {
            hboxMedia.getChildren().forEach(node -> {
                int vid = hboxMedia.getChildren().indexOf(node);
                VBox vBox = (VBox) node;
                while (vBox.getChildren().size() < 3 && !mediaItems.isEmpty()) {
                    MediaHandler media = (MediaHandler) mediaItems.get(0);
                    vBox.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            });
            return;
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
                    MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
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

    @FXML
    public void handleSearch (ActionEvent event) {
        handleSearch();
    }

    public void handleSearch() {
        String text = textFieldSearch.getText();
        try {
            List medium = getBController().searchMedia(text);
            if(medium.size() == 0) {
                PopupScreen.error("Không tìm thấy sản phẩm");
                addMediaHome(homeItems);
                setNumOfPage(homeItems);
            }  else {
                this.homeSearchItems = new ArrayList<>();
                for (Object object : medium) {
                    Media media = (Media) object;
                    MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
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

    private void sortByAscendingPrice(int position, String text, MenuButton menuButton) {
        List<MediaHandler> sortedItems = new ArrayList<>(homeItems);
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            this.homeSearchItems = SortHandler.sortByAscendingPrice(homeSearchItems);
            addMediaHome(homeSearchItems);
        });
        menuButton.getItems().add(position,menuItem );
    }

    private void sortByDescendingPrice(int position, String text, MenuButton menuButton) {
        List<MediaHandler> sortedItems = new ArrayList<>(homeItems);
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            this.homeSearchItems = SortHandler.sortByDescendingPrice(homeSearchItems);
            addMediaHome(homeSearchItems);
        });
        menuButton.getItems().add(position,menuItem );
    }
}
