package controller;

import entity.media.Media;

import java.sql.SQLException;
import java.util.List;

/**
 * This class controls the flow of events in homescreen
 *
 */
/**
 * No SOLID violation:
 * SRP: Responsibilities are related to the functionality of the home screen, and the class doesn't have any unrelated responsibilities
 * OCP: Can modify or extend the behavior of BaseController without directly modifying its code
 * LSP: Instances of HomeController can be used in place of instances of BaseController without affecting the correctness of the program
 * ISP: No interface
 * DIP: Depends on Media class rather than concrete implementations, allow change in Media class without affecting the HomeController
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
