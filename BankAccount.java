//Task3

public class BankAccount {
    private int accountNumber;
    private String username;
    private double balance;

    public BankAccount(int accountNumber, String username, double balance){
        this. accountNumber = accountNumber;
        this. username = username;
        this.balance = balance;
    }

    public String getUsername(){
        return username;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public void information(){
        System.out.println(username + " - Balance: " + balance);
    }
}
