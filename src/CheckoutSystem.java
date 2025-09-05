import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

class PriceInfo {
    String id;
    int unitPrice; //normal price for a single item
    int discountQuantity; //number of items in the discounted batch
    int specialPrice; //total price for the discounted batch

    //Constructor for no discounts
    PriceInfo(String id, int unitPrice) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.discountQuantity = 0;
        this.specialPrice = 0;
    }
    //Constructor for discounted items
    PriceInfo(String id, int unitPrice, int discountQuantity, int specialPrice) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.discountQuantity = discountQuantity;
        this.specialPrice = specialPrice;
    }
}

class ShoppingCart {
    private final HashMap<String, Integer> basket;
    private final HashMap<String, PriceInfo> pricingRules;

    //Constructor, takes price rules as a PriceInfo list, creates an empty basket
    public ShoppingCart(ArrayList<PriceInfo> priceList) {
        pricingRules = new HashMap<>();
        basket = new HashMap<>();
        for (PriceInfo info : priceList) {
            pricingRules.put(info.id, info);
        }
    }

    public void addItemToBasket(String id) {
        if (pricingRules.containsKey(id)) {
            basket.put(id, basket.getOrDefault(id, 0) + 1);
        } else if (id.length() != 1) {
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
            if (p.discountQuantity > 1) {
                discountedBatch = item.getValue() / p.discountQuantity;
                total += discountedBatch * p.specialPrice;
            }
            total += (item.getValue() - discountedBatch * p.discountQuantity) * p.unitPrice;
        }
        return total;
    }
}

public class CheckoutSystem {
    static ArrayList<PriceInfo> priceListGenerator(Scanner sc) {
        ArrayList<PriceInfo> priceList = new ArrayList<>();
        System.out.println("Select 'A' to use the sample price rules,'B' to provide rules yourself,'Q' to quit:");
        loop:
        while (true) {
            String nextToken = sc.nextLine();
            nextToken = nextToken.toUpperCase(Locale.ROOT);
            switch (nextToken) {
                case "Q": {
                    break loop;
                }
                case "A": {
                    priceList.add(new PriceInfo("A", 50, 3, 130));
                    priceList.add(new PriceInfo("B", 30, 2, 45));
                    priceList.add(new PriceInfo("C", 20));
                    priceList.add(new PriceInfo("D", 15));
                    System.out.println("Using sample prices");
                    break loop;
                }
                case "B": {
                    while (true) {
                        System.out.println("Please enter next product id to continue or 'done' to finish:");
                        nextToken = sc.nextLine();
                        nextToken = nextToken.toUpperCase(Locale.ROOT);
                        if (nextToken.equals("DONE")) {
                            System.out.println("Manual pricing rules set");
                            break;
                        } else if (nextToken.length() == 1) {
                            String id = nextToken;
                            int unitPrice, discountQuantity, specialPrice = 0;
                            System.out.println("Unit Price:");
                            nextToken = sc.nextLine();
                            try {
                                unitPrice = Integer.parseInt(nextToken);
                            } catch (NumberFormatException e) {
                                System.out.println("Unit Price must be an integer");
                                continue;
                            }
                            System.out.println("Discount Quantity(number of items for a discount), '0' for no discount:");
                            nextToken = sc.nextLine();
                            try {
                                discountQuantity = Integer.parseInt(nextToken);
                            } catch (NumberFormatException e) {
                                System.out.println("Discount Quantity must be an integer");
                                continue;
                            }
                            if (discountQuantity > 1) {
                                System.out.println("Special Price(Discounted batch cost):");
                                nextToken = sc.nextLine();
                                try {
                                    specialPrice = Integer.parseInt(nextToken);
                                } catch (NumberFormatException e) {
                                    System.out.println("Special Price must be an integer");
                                    continue;
                                }
                            }
                            priceList.add(new PriceInfo(id, unitPrice, discountQuantity, specialPrice));
                        } else {
                            System.out.println("Product id must be an individual letter");
                        }
                    }
                    break loop;
                }
                default:
                    System.out.println("Invalid input. Please try again:");
            }
        }
        return priceList;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the checkout system");
        ArrayList<PriceInfo> priceList = priceListGenerator(sc);
        if (priceList.isEmpty()) {
            System.out.println("There are no items in the price list, closing application.");
        } else {
            ShoppingCart cart = new ShoppingCart(priceList);
            System.out.println("Please enter a single letter item ID to add it to cart or enter 'done' to finish shopping:");
            while (true) {
                String nextItem = sc.nextLine();
                nextItem = nextItem.toUpperCase(Locale.ROOT);
                if (nextItem.equals("DONE")) {
                    System.out.println("Final total is: " + cart.calculateCost());
                    System.out.println("Thank you for using checkout system");
                    break;
                }
                try {
                    cart.addItemToBasket(nextItem);
                    System.out.println("Current cost is: " + cart.calculateCost());
                    System.out.println("Please enter next item ID:");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}