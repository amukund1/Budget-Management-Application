//object that is overview of budget; serves as header
public class Budget {
   private String name; //field for Budget Name
   private double budget; //field for Budget Value
   private String timePeriod; //field for time period
   
   //constructor for Budget Object, which is initialized by the name, budget, and its start/end dates
   public Budget(String name, double budget, String startDate, String endDate) { 
      this.name = name;
      this.budget = budget;
      this.timePeriod = startDate + " to " + endDate;
   }
   
   //returns budget value
   public double getBudget() {
      return budget;
   }
   
   
   //Header for official text file. Includes name, budget value and time period
   public String toString() {
      String title = "Budget for: " + name;
      String budgetStatement = "Budget: $" + budget;
      String timePeriodStatement = "Time Period: " + timePeriod;
      
      String n = System.getProperty("line.separator");
      
      return title + n + budgetStatement + n + timePeriodStatement;
   }
   
}

