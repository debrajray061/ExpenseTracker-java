import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ExpenseTracker 
{
    private static List<Expense> expenses = new ArrayList<>();
    private static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) 
    {
        loadExpensesFromFile();
        Scanner sc = new Scanner(System.in);
        int choice;

        do 
        {
            System.out.println("\n--- Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Show Total");
            System.out.println("4. Save & Exit");
            System.out.println("5. Filter by Category");
            System.out.println("6. Monthly Summary");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) 
            {
                case 1:
                    addExpense(sc);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    showTotal();
                    break;
                case 4:
                    saveExpensesToFile();
                    break;
                case 5:
                    filterByCategory(sc);
                    break;
                case 6:
                    showMonthlySummary();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } 
        while (choice != 4);
    }

    private static void addExpense(Scanner sc) 
    {
        System.out.print("Enter amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter category (e.g., Food, Travel): ");
        String category = sc.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        Expense e = new Expense(amount, category, date);
        expenses.add(e);
        System.out.println("Expense added.");
    }

    private static void viewExpenses() 
    {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\nDate       | Category | Amount");
        System.out.println("----------------------------------");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    private static void showTotal() 
    {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        System.out.println("Total Expenses: ₹" + total);
    }

    private static void filterByCategory(Scanner sc) 
    {
        System.out.print("Enter category to filter: ");
        String category = sc.nextLine();
        boolean found = false;

        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) 
            {
                System.out.println(e);
                found = true;
            }
        }

        if (!found)
            System.out.println("No expenses found for category: " + category);
    }

    private static void showMonthlySummary() 
    {
        Map<YearMonth, Double> monthlyTotals = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Expense e : expenses) 
        {
            LocalDate date = LocalDate.parse(e.getDate(), formatter);
            YearMonth ym = YearMonth.from(date);
            monthlyTotals.put(ym, monthlyTotals.getOrDefault(ym, 0.0) + e.getAmount());
        }

        System.out.println("\n--- Monthly Summary ---");
        for (Map.Entry<YearMonth, Double> entry : monthlyTotals.entrySet()) 
        {
            System.out.println(entry.getKey() + ": ₹" + entry.getValue());
        }
    }

    private static void saveExpensesToFile() 
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) 
        {
            for (Expense e : expenses) 
            {
                bw.write(e.getAmount() + "," + e.getCategory() + "," + e.getDate());
                bw.newLine();
            }
            System.out.println("Expenses saved to file.");
        } catch (IOException ex) {
            System.out.println("Error saving file: " + ex.getMessage());
        }
    }

    private static void loadExpensesFromFile() 
    {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(",");
                double amount = Double.parseDouble(parts[0]);
                String category = parts[1];
                String date = parts[2];
                expenses.add(new Expense(amount, category, date));
            }
        } 
        catch (IOException ex) 
        {
            System.out.println("Error loading file: " + ex.getMessage());
        }
    }
}
