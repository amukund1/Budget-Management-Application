//object that stores the budget, categories etc.
import java.util.*;
import java.io.*;

//actual document in which budget is outlined
public class BudgetDocument {
   private Budget budget; //budgetObject
   private double budgetRemaining; //budget left after spending
   private ArrayList<Category> categories = new ArrayList<Category>(); //list of the categories in the budget
   private double lowBudget; //value which the user chooses when the budget is low
   
   //constructor takes in a Budget object and the value at which the user should be warned
   public BudgetDocument(Budget budget) {
      this.budgetRemaining = budget.getBudget();
      this.budget = budget;
   }
   
   //returns the budget object
   public Budget getBudget() {
      return budget;
   }
   
   //returns the budget remaining
   public double getBudgetRemaining() {
      return budgetRemaining;
   }
   
   //checks to make sure that any money value entered is positive
   public double enforceValidity(double value, Scanner console) {
      console.nextLine();
      boolean validity = true;
      
      if(value < 0) {
         validity = false;
      }
      
      while(validity == false) {         
         System.out.print("Invalid entry. Please enter a non-negative value: ");
         
         try {
            value = console.nextDouble();
         } catch(InputMismatchException e) {
            value = invalidate();
            console.nextLine();
         }
         
         if(value < 0) {
            validity = false;
         } else {
            validity = true;
         }
      }
      
      return value;
   }
   
   public double invalidate() {
      return -1;
   }   
   
   //adds a category to the current array list of categories and updates budget
   public void addCategory(Category c) {
      //tests to make sure that the value of the category is not negative and does not exceed the budget
      if(c.getMoney() > 0 && c.getMoney() <= budgetRemaining) {
         categories.add(c);
         budgetRemaining -= c.getMoney();
      } else if(c.getMoney() > budgetRemaining) {
         String invalid = "This category is invalid; It will exceed your budget.";
         System.out.println(invalid);
      } else if(c.getMoney() < 0) {
         System.out.println("This category is invalid; you cannot assign a negative money value to it.");
      }
   }
   
   //removes a category at index i from the current array list of categories and updates budget
   public void removeCategory(int i) {
      Category c = categories.get(i);
      budgetRemaining += c.getMoney();
      categories.remove(i);
   }
   
   //returns 'categories' ArrayList
   public ArrayList<Category> getCategoryList() {
      return categories;
   }
   
   //returns the index of the category to be removed to make the removeCategory(int i) method work
   public int getCategoryIndex(String categoryName) {
      String[] categoryNames = new String[(getCategoryList()).size()];
      ArrayList<Category> categoryList = getCategoryList();
      int nameIndex = -1; //this prime-pumping is appropriate because "negative" indices indicate that the name is not in the list
      
      for(int i = 0; i < categoryNames.length; i++) {
         categoryNames[i] = (categoryList.get(i)).getName();
         if(categoryName.equals(categoryNames[i])) {
            nameIndex = i;
         }
      }
      
      return nameIndex;        
   }
   
   //warn when budget runs "low" at the user standard
   public void warning(double lowBudget) {
      if(budgetRemaining <= lowBudget) {
         System.out.println("You are close to exceeding your budget.");
      } else {
         System.out.println();
      }
   }
  
   //method to export to text file  
   public void export() throws FileNotFoundException {
      Scanner input = new Scanner(System.in);
      System.out.print("Please enter the notepad file which you would like to have your official budget document generated in: ");
      String fileName = input.nextLine();
      
      PrintStream output = new PrintStream(new File(fileName + ".txt"));
 
      output.print(budget); //prints budget overview to text file via toString() method
      output.println();
      output.println();
      
      output.println("Categories in this budget: ");
      output.println("---------------------------");
      output.println();

      
      //loop controls output of categories so that each category has its own line.
      for(int i = 0; i < categories.size(); i++) {
         output.println(categories.get(i));
         output.println();
      }
   }
            
}