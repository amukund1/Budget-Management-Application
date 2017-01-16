//Client code that allows user to input the budget, categories etc. through use of the objects
import java.util.*;
import java.io.*;

public class BudgetClient {
   public static final double PRIME_PUMP = 0; //primes the pump for variables with cumulative algorithms
   
   public static void main(String[] args) {
      Scanner nameIn = new Scanner(System.in);
         
      System.out.println("Welcome!");
      
      
      System.out.print("Please enter your name: ");
      String name = nameIn.nextLine();
      
      Scanner budgetIn = new Scanner(System.in);

      double budget = PRIME_PUMP;
      System.out.print("Please enter your budget. Do not include the dollar sign: ");
      try {
         budget = budgetIn.nextDouble();
         budget = enforceValidity(budget, budgetIn);
      } catch(InputMismatchException e) {
         budget = invalidate();
         budget = enforceValidity(budget, budgetIn);
      }
      
      Scanner dateIn = new Scanner(System.in);
      System.out.print("Please enter the start date for your budget. Any format is acceptable: ");
      String startDate = dateIn.nextLine();

      System.out.print("Please enter the end date for your budget. Any format is acceptable: ");
      String endDate = dateIn.nextLine();
       
      System.out.print("Please enter the money value at which you would like to be warned that your budget is running low. Do not enter the dollar sign: ");
      double low = PRIME_PUMP;
      Scanner lowIn = new Scanner(System.in);
      
      try {
         low = lowIn.nextDouble();
         low = enforceValidity(low, lowIn);
      } catch(InputMismatchException e) {
         low = invalidate();
         low = enforceValidity(low, lowIn);
      }
      
      System.out.println();

      Budget b = new Budget(name, budget, startDate, endDate);

      System.out.print("Would you like to create a budget for a domestic context or other context? Enter either 'domestic' or 'other', depending on what you want: ");
      Scanner budgetTypeIn = new Scanner(System.in);
      String budgetType = budgetTypeIn.nextLine();
      
      while(!(budgetType.equalsIgnoreCase("domestic") || budgetType.equalsIgnoreCase("other"))) {
         System.out.println("This is not a valid budget type. Your only options are 'domestic' or 'other'. Please try again.");
         System.out.print("Would you like to create a budget for a domestic context or other context? Enter either 'domestic' or 'other', depending on what you want: ");
         budgetType = budgetTypeIn.nextLine();
      }

      BudgetDocument doc = null; //prime the pump for budgetdoc/domestic budget(Polymorphism)
      
      if(budgetType.equalsIgnoreCase("domestic")) { 
         double income = PRIME_PUMP;
         Scanner incomeIn = new Scanner(System.in);
              
         //the loop checks to make sure that the income is greater than or equal than the budget
         do {
            System.out.print("Please enter the income which you have for this time period. Do not include the dollar sign: ");
            try {
               income = incomeIn.nextDouble();
               income = enforceValidity(income, incomeIn); 
            } catch(InputMismatchException e) {
               income = invalidate();
               income = enforceValidity(income, incomeIn);
            }
              
            if(incomeValidity(income, budget)) {
               doc = new DomesticBudgetDocument(b, income);
            } else {
               System.out.println("It appears that your income is less than your budget. This does not makes sense.");
            }  
         } while(!incomeValidity(income, budget)); 
      } else if(budgetType.equalsIgnoreCase("other")) {
         doc = new BudgetDocument(b);
      }
         
      System.out.println("Would you like to add or remove a category, see your current list of categories, or quit the program?");
      System.out.print("The following entries will do the respective actions: 'add', 'remove', 'list', 'quit': ");
      Scanner commandIn = new Scanner(System.in);
      String command = commandIn.nextLine();
        
      do {
         if(command.equalsIgnoreCase("quit")) {
            break;
         } else if(command.equalsIgnoreCase("list")) {
            spendingUpdate(doc);
         } else if(command.equalsIgnoreCase("remove") && (doc.getCategoryList()).isEmpty()) {
            System.out.println("You cannot remove a category if there are no categories in your list. Please try again.");
            continue;
         } else if(command.equalsIgnoreCase("add")) {
            Scanner categoryNameIn = new Scanner(System.in);
            System.out.print("Please enter the name of the category you would like to add: ");
            String categoryName = categoryNameIn.nextLine();
            
            System.out.print("Please enter the amount of money that you would like to invest into this category. Do not include the dollar sign: ");
            double money = PRIME_PUMP;
            Scanner categoryMoneyIn = new Scanner(System.in);
            
            //takes care of issue of exceptions that would end the program
            try {
               money = categoryMoneyIn.nextDouble();
               money = enforceValidity(money, categoryMoneyIn);
            } catch(InputMismatchException e) {
               money = invalidate();
               money = enforceValidity(money, categoryMoneyIn);
            }
            
            Category c = new Category(categoryName, money);
            
            doc.addCategory(c);
            spendingUpdate(doc);
            doc.warning(low);
         } else if(command.equalsIgnoreCase("remove")) {
            Scanner categoryOut = new Scanner(System.in);
            System.out.print("Please enter the name of the category that you would like to remove: ");
            String categoryName = categoryOut.nextLine();
            int categoryIndex = doc.getCategoryIndex(categoryName);  
            
            if(categoryIndex >= 0 && categoryIndex < (doc.getCategoryList()).size()) {
               doc.removeCategory(categoryIndex);
               spendingUpdate(doc);
               doc.warning(low);
            } else {
               System.out.println("This category does not exist in your current list. Please try again.");
            }
         } else {
            System.out.println("This is an invalid entry. Your only options are 'add', 'remove', 'list', and 'quit'. Please try again.");
         }
         System.out.print("Would you like to add or remove a category, see your current list of categories, or quit the program? ");
         System.out.print("The following entries will do the respective actions: 'add', 'remove', 'list', 'quit': ");
         command = commandIn.next();        
      } while(!command.equalsIgnoreCase("quit"));
      
      Category leftover = new Category("Leftover", doc.getBudgetRemaining());     
      doc.addCategory(leftover);
      spendingUpdate(doc);
      
      
      try { 
         doc.export();
      } catch (FileNotFoundException fnf) {
         System.out.print(fnf.toString());
      } catch (Exception e) {
         System.out.print(e.toString());
      }
   }
   
   //checks to make sure that any money value entered is positive and indeed a double value
   public static double enforceValidity(double value, Scanner console) {
      console.nextLine(); //reset the Scanner copy
      boolean validity = true;
      
      if(value <= 0) {
         validity = false;
      }
      
      while(validity == false) {
         System.out.print("Invalid entry. Please enter a positive value: ");
         
         try {
            value = console.nextDouble();
         } catch(InputMismatchException e) {
            value = invalidate();
            console.nextLine(); //http://stackoverflow.com/questions/28658256/try-catch-skipping-loops, deals with skipping loops
         }
         
         if(value <= 0) {
            validity = false;
         } else {
            validity = true;
         }
      }
      
      return value;
   }
   
   public static void spendingUpdate(BudgetDocument doc) {
      System.out.println("Here is your updated list of spending categories: " + doc.getCategoryList());
      System.out.println("This is the amount you have left in your budget: $" + doc.getBudgetRemaining());
   }
   
   //makes sure that income is greater than budget
   public static boolean incomeValidity(double income, double budget) {
      if(income >= budget) {
         return true;
      } else {
         return false;
      }
   }
   
   //returns -1 to invalidate any money values entered that are not doubles, as negative money values are also not valid
   public static double invalidate() {
      return -1;
   }

}