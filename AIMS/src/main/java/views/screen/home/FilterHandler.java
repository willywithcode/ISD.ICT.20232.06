package views.screen.home;

import java.util.ArrayList;
import java.util.List;

public class FilterHandler {
    public static List<MediaHandler> handleFilter(List<MediaHandler> homeItems, String type) {

        List<MediaHandler> filteredItems = new ArrayList<>();
        homeItems.forEach(mediaHandler -> {
            if (type.toLowerCase().equalsIgnoreCase(mediaHandler.getMedia().getType().toLowerCase())) {
                filteredItems.add(mediaHandler);
            }
        });
        return filteredItems;
    }
}
