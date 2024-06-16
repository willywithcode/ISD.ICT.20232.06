package views.screen.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortHomeScreen {
    public static List<MediaHomeHandler> sortByAscendingPrice(List<MediaHomeHandler> homeItems) {
        List<MediaHomeHandler> sortedItems = new ArrayList<>(homeItems);
        Collections.sort(sortedItems, Comparator.comparingInt((MediaHomeHandler o) -> o.getMedia().getPrice()));
        return sortedItems;
    }

    public static List<MediaHomeHandler> sortByDescendingPrice(List<MediaHomeHandler> homeItems) {
        List<MediaHomeHandler> sortedItems = new ArrayList<>(homeItems);
        Collections.sort(sortedItems, Comparator.comparingInt((MediaHomeHandler o) -> o.getMedia().getPrice()).reversed());
        return sortedItems;
    }
}
