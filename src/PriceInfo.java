class PriceInfo {
    private final String id;
    private final int unitPrice; //normal price for a single item
    private final int discountQuantity; //number of items in the discounted batch
    private final int specialPrice; //total price for the discounted batch

    public String getId() {
        return id;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getDiscountQuantity() {
        return discountQuantity;
    }

    public int getSpecialPrice() {
        return specialPrice;
    }

    //Constructor for no discounts
    public PriceInfo(String id, int unitPrice) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.discountQuantity = 0;
        this.specialPrice = 0;
    }

    //Constructor for discounted items
    public PriceInfo(String id, int unitPrice, int discountQuantity, int specialPrice) {
        this.id = id;
        this.unitPrice = unitPrice;
        this.discountQuantity = discountQuantity;
        this.specialPrice = specialPrice;
    }
}
