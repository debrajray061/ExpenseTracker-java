
public class Expense 
{
    private double amount;
    private String category;
    private String date; // Format: YYYY-MM-DD

    public Expense(double amount, String category, String date) 
    {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public double getAmount() 
    {
        return amount;
    }

    public String getCategory() 
    {
        return category;
    }

    public String getDate() 
    {
        return date;
    }

    @Override
    public String toString() 
    {
        return date + " | " + category + " | ₹" + amount;
    }
}
