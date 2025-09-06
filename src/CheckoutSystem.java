import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

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
                        } else if (nextToken.length() == 1 && Character.isLetter(nextToken.charAt(0))) {
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
                            System.out.println("Product id must be a single letter");
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