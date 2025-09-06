import java.util.ArrayList;
import java.util.HashMap;

class ShoppingCart {
    private final HashMap<String, Integer> basket;
    private final HashMap<String, PriceInfo> pricingRules;

    //Constructor, takes price rules as a PriceInfo list, creates an empty basket
    public ShoppingCart(ArrayList<PriceInfo> priceList) {
        pricingRules = new HashMap<>();
        basket = new HashMap<>();
        for (PriceInfo info : priceList) {
            pricingRules.put(info.getId(), info);
        }
    }

    public void addItemToBasket(String id) {
        if (pricingRules.containsKey(id)) {
            basket.put(id, basket.getOrDefault(id, 0) + 1);
        } else if (id.length() != 1 || !Character.isLetter(id.charAt(0))) {
            throw new IllegalArgumentException("Item ID's are individual letters of the alphabet (A, B, C, and so on). Please try again:");
        } else {
            throw new IllegalArgumentException("Invalid item ID. Please try again:");
        }
    }

    public int calculateCost() {
        int total = 0;
        for (var item : basket.entrySet()) {
            PriceInfo p = pricingRules.get(item.getKey());
            int discountedBatch = 0;
            if (p.getDiscountQuantity() > 1) {
                discountedBatch = item.getValue() / p.getDiscountQuantity();
                total += discountedBatch * p.getSpecialPrice();
            }
            total += (item.getValue() - discountedBatch * p.getDiscountQuantity()) * p.getUnitPrice();
        }
        return total;
    }
}
