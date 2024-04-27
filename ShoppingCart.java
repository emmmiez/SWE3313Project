import java.util.ArrayList;
import java.util.ListIterator;

class Item {
    private int quantity;
    private String itemName;
    private double price;

    //constructor
    Item(int quantity, String itemName, double price)
    {
        this.quantity = quantity;
        this.itemName = itemName;
        this.price = price;
    }
    //price getter
    public double getPrice()
    {
        return  price;
    }
    //item name getter
     public String getItemName()
     {
         return itemName;
     }
     //quantity getter
     public int getQuantity()
     {
         return quantity;
     }
     //quantity setter
     public int setQuantity(int newQuantity){
        quantity = newQuantity;
        return newQuantity;
     }

     //toString
     public String toString()
     {
        String s = itemName + ": ";
        s += "$" + price + "\n";
        return s;
     }
}



class ShoppingCart {

    //need to implement database still

    ArrayList<Item> items;
    double totalCost;
    ShoppingCart(){
    items = new ArrayList<>();
    }

    //add to cart
    public void addToCart(Item item) throws IllegalArgumentException {
        if(item == null){
            throw new IllegalArgumentException("Item cannot be null. ");
        }
        items.add(item);
        //use database here instead???
    }

    //remove from cart
    public void removeFromCart(Item i) throws IllegalArgumentException {
        if(i == null) {
            throw new IllegalArgumentException("Item cannot be null. ");
        }
            ListIterator<Item> iterator = items.listIterator();
            while(iterator.hasNext()){
                Item item1 = iterator.next();
                if(item1.getItemName().equalsIgnoreCase(i.getItemName())){
                    items.remove(i);
                    //use database here instead?
                    return;
                }
            }
            throw new IllegalArgumentException("Item has not been found in cart.");
        }

    //show cart
    public void showCart(){
        ListIterator<Item> iterator1 = items.listIterator();
        while(iterator1.hasNext()){
            Item item2 = iterator1.next();
            System.out.println(item2);
        }
    }

    //update quantity
    public void updateQuantity(String itemName, int newQuantity) throws IllegalArgumentException {
        if(newQuantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0. ");
        }
        for (Item item : items){
            if(item.getItemName().equalsIgnoreCase(itemName)){
                item.setQuantity(newQuantity);
                //use database here instead??
                return;
            }
        }
        throw new IllegalArgumentException("Item has not been found in cart. ");
    }

    //get total
    public double getFinalCost(){
        ListIterator<Item> iterator2 = items.listIterator();
        totalCost = 0;
        while(iterator2.hasNext()){
            Item item3 = iterator2.next();
            totalCost = (item3.getQuantity() * item3.getPrice()) + totalCost;
        }
        return totalCost;
    }
 }
