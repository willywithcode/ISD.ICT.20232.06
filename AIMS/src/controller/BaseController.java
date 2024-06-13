package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

import java.util.List;


/**
 * This class is the base controller for our AIMS project.
 *
 */
public class BaseController {

    /**
     * The method checks whether the Media in Cart, if it were in, we will return
     * the CartMedia else return null.
     *
     * @param media media object
     * @return CartMedia or null
     */
    public CartMedia checkMediaInCart(Media media) {
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart.
     *
     * @return List[CartMedia]
     */
    public List getListCartMedia() {
        return Cart.getCart().getListMedia();
    }
}
