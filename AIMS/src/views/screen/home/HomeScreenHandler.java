package views.screen.home;

import common.exception.ViewCartException;
import controller.LoginController;
import controller.HomeController;
import controller.ViewCartController;
import entity.cart.Cart;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import views.screen.mediaDetail.BookScreenHandler;
import views.screen.mediaDetail.CDScreenHandler;
import views.screen.mediaDetail.DVDScreenHandler;
import views.screen.mediaDetail.MediaScreenHandler;
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

        cartImage.setOnMouseClicked(e -> {
            CartScreenHandler cartScreen;
            try {
                LOGGER.info("User clicked to view cart");
                cartScreen = new CartScreenHandler(this.stage, Configs.CART_SCREEN_PATH);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });
        loginBtn.setOnMouseClicked(e -> {
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
        });
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
    /*public void openBookDetail(Book book) {
        BookScreenHandler bookScreen;
        try {
            LOGGER.info("User clicked to view book");
            bookScreen = new BookScreenHandler(this.stage, Configs.MEDIA_DETAIL_PATH, book);
            bookScreen.setHomeScreenHandler(this);
            //bookScreen.setBController(new ViewCartController());
            bookScreen.requestToViewBook(this);
        } catch (IOException | SQLException e1) {
            throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
        }
    }

    public void openDVDDetail(DVD dvd) {
        DVDScreenHandler dvdScreen;
        try {
            LOGGER.info("User clicked to view book");
            dvdScreen = new DVDScreenHandler(this.stage, Configs.MEDIA_DETAIL_PATH, dvd);
            dvdScreen.setHomeScreenHandler(this);
            //bookScreen.setBController(new ViewCartController());
            dvdScreen.requestToViewDVD(this);
        } catch (IOException | SQLException e1) {
            throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
        }
    }

    public void openCDDetail(CD cd) {
        CDScreenHandler cdScreen;
        try {
            LOGGER.info("User clicked to view book");
            cdScreen = new CDScreenHandler(this.stage, Configs.MEDIA_DETAIL_PATH, cd);
            cdScreen.setHomeScreenHandler(this);
            //bookScreen.setBController(new ViewCartController());
            cdScreen.requestToViewCD(this);
        } catch (IOException | SQLException e1) {
            throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
        }
    }*/
    
    public void openMediaDetail(Media media) {
    	MediaScreenHandler mediaScreen;
        try {
            mediaScreen = new MediaScreenHandler(this.stage, Configs.MEDIA_DETAIL_PATH, media);
            mediaScreen.setHomeScreenHandler(this);
            //bookScreen.setBController(new ViewCartController());
            mediaScreen.requestToView(this);
        } catch (IOException | SQLException e1) {
            e1.printStackTrace();
        }
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
