import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart cart;

    @Test
    void testShoppingCartWithSamplePrices() {
        ArrayList<PriceInfo> priceList = new ArrayList<>();
        priceList.add(new PriceInfo("A", 50, 3, 130));
        priceList.add(new PriceInfo("B", 30, 2, 45));
        priceList.add(new PriceInfo("C", 20));
        priceList.add(new PriceInfo("D", 15));

        cart = new ShoppingCart(priceList);
        assertEquals(0,cart.calculateCost());
        cart.addItemToBasket("A");
        assertEquals(50,cart.calculateCost());
        cart.addItemToBasket("A");
        assertEquals(100,cart.calculateCost());
        cart.addItemToBasket("A");
        assertEquals(130,cart.calculateCost());
        cart.addItemToBasket("B");
        assertEquals(160,cart.calculateCost());
        cart.addItemToBasket("C");
        assertEquals(180,cart.calculateCost());
        cart.addItemToBasket("B");
        assertEquals(195,cart.calculateCost());
        assertThrowsExactly(IllegalArgumentException.class,()->cart.addItemToBasket("X"));
        assertEquals(195,cart.calculateCost());
    }
}