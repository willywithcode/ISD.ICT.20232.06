package controller;

import entity.media.Media;

import java.sql.SQLException;
import java.util.List;

/**
 * This class controls the flow of events in homescreen
 * @SRP Violation of Single Responsibility Principle (SRP): The HomeController class extends from BaseController and implements a new function related to retrieving all Media from the database.
 */
public class HomeController extends BaseController {

    /**
     * this method gets all Media in DB and return back to home to display
     *
     * @return List[Media]
     * @throws SQLException
     */
    public List getAllMedia() throws SQLException {
        return new Media().getAllMedia();
    }

    public List searchMedia(String searchText) throws SQLException {
        return new Media().searchMedia(searchText);
    }

    public List handleFilter(String filterType) throws SQLException {
        return new Media().getMediaByType(filterType);
    }
}
