import java.util.*;
//domestic-specific Budget Document; subclass
import java.io.*;

public class DomesticBudgetDocument extends BudgetDocument {
   private static final double PRIME_PUMP = 0; //initializes variables that are in cumulative algorithms
   private double income;
   

   public DomesticBudgetDocument(Budget budget, double income) {
      super(budget);
      this.income = income;
      
      //Pumping for Scanner objects
      Scanner aIn = null;
      Scanner bIn = null;
      Scanner cIn = null;
      Scanner dIn = null;
      
      //Pumping for costs of the categories
      double costA = PRIME_PUMP;
      double costB = PRIME_PUMP;
      double costC = PRIME_PUMP;
      double costD = PRIME_PUMP;
      
      //Pumping for default categories
      Category a = null;
      Category b = null;
      Category c = null;
      Category d = null;
      
      Scanner[] scanners = {aIn, bIn, cIn, dIn}; //separate scanners for each entry
      String[] defaultCategoryLabels = {"HousePayment", "Groceries", "Restaurants", "Clothes"};
      double[] costs = {costA, costB, costC, costD}; // costs of each category
      Category[] defaultCategories = {a, b, c, d};
      
      for(int i = 0; i < 4; i++) {
         scanners[i] = new Scanner(System.in);
         System.out.print("Please enter the amount of money you would like to invest into the '" + defaultCategoryLabels[i] + "' category. Do not include the dollar sign: ");
         try {
            costs[i] = scanners[i].nextDouble();
            costs[i] = super.enforceValidity(costs[i], scanners[i]);
         } catch(InputMismatchException e) {
            costs[i] = super.invalidate();
            costs[i] = super.enforceValidity(costs[i], scanners[i]);
         }
         defaultCategories[i] = new Category(defaultCategoryLabels[i], costs[i]);
         
         //forces loop to repeat the current iteration if it does not meet the standards of the addCategory method in the superclass
         if(costs[i] >= 0 && costs[i] <= super.getBudgetRemaining()) {
            addCategory(defaultCategories[i]);
         } else {
            System.out.println("This is not a proper value. It either exceeds your budget or is negative. Please try again.");
            i -= 1; //forces the loop to redo the current iteration
         }
      }
   }
   
   
   public double getIncome() {
      return income;
   }
   
   
   public double getIncomeRemaining() {
      return income - super.getBudgetRemaining();
   }
   
   //method to export to text file  
   public void export() throws FileNotFoundException {
      Scanner input = new Scanner(System.in);
      System.out.print("Please enter the notepad file which you would like to have your official budget document generated in: ");
      String fileName = input.nextLine();
      
      PrintStream output = new PrintStream(new File(fileName + ".txt"));
 
      output.print(super.getBudget());
      output.println();
      
      reportIncome(output);
      output.println();
      
      output.println("Categories in this budget: ");
      output.println("---------------------------");

      ArrayList<Category> categories = super.getCategoryList(); //brings the category list to this subclass
      
      //loop controls output of categories so that each category has its own line.
      for(int i = 0; i < (categories.size()); i++) {
         output.println(categories.get(i));
         output.println();
      }
   }
   
   public void reportIncome(PrintStream output) {
      output.print("Income: " + income);
      output.println();
   }
   
}