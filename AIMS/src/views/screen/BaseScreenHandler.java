package views.screen;

import java.io.IOException;
import java.util.Hashtable;

import controller.BaseController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.screen.home.HomeScreenHandler;

public abstract class BaseScreenHandler extends FXMLScreenHandler {

    protected final Stage stage;
    protected HomeScreenHandler homeScreenHandler;
    protected Hashtable<String, String> messages;
    private Scene scene;
    private BaseScreenHandler prev;
    private BaseController bController;

    private BaseScreenHandler(String screenPath) throws IOException {
        super(screenPath);
        this.stage = new Stage();
    }


    public BaseScreenHandler(Stage stage, String screenPath) throws IOException {
        super(screenPath);
        this.stage = stage;
    }

    /**
     * @return BaseScreenHandler
     */
    public BaseScreenHandler getPreviousScreen() {
        return this.prev;
    }

    /**
     * @param prev
     */
    public void setPreviousScreen(BaseScreenHandler prev) {
        this.prev = prev;
    }

    public void show() {
        if (this.scene == null) {
            this.scene = new Scene(this.content);
        }
        this.stage.setScene(this.scene);
        this.stage.show();
    }


    /**
     * @param string
     */
    public void setScreenTitle(String string) {
        this.stage.setTitle(string);
    }

    /**
     * @return BaseController
     */
    public BaseController getBController() {
        return this.bController;
    }

    /**
     * @param bController
     */
    public void setBController(BaseController bController) {
        this.bController = bController;
    }


    /**
     * @param HomeScreenHandler
     */
    public void setHomeScreenHandler(HomeScreenHandler HomeScreenHandler) {
        this.homeScreenHandler = HomeScreenHandler;
    }

}
