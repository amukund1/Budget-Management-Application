//represents a category for spending
public class Category {
   private String name; //name of category
   private double money; //amount invested in category
   
   //constructor takes in name of category and amount invested in category
   public Category(String name, double money) {
      this.name = name;
      this.money = money;
   }
   
   //accessor for category's name
   public String getName() {
      return name;
   }
   
   //accessor for the money invested in a category
   public double getMoney() {
      return money;
   }
   
   public String toString() {
      return "Name: " + getName() + ", Money: $" + getMoney();
   }
}