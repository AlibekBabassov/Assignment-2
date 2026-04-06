import java.util.*;
public class Main{
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<BankAccount> accounts = new LinkedList<>();
        Stack<String> transactions = new Stack<>();
        while (true){
            System.out.println("Menu");
            System.out.println("1. Add a new account");
            System.out.println("2. Display all accounts");
            System.out.println("3. Search account by username");
            System.out.println("4. Deposit money");
            System.out.println("5. Withdraw money");
            System.out.println("6. Pay Bill");
            System.out.println("7. Display last transaction (peek)");
            System.out.println("8. Undo last transaction (pop)");
            System.out.println("9. Exit");
            System.out.print("Select: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    int accountNum = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter username: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();
                    accounts.add(new BankAccount(accountNum, name, balance));
                    System.out.println("Account added successfully");
                    break;
                case 2:
                    System.out.println("Accounts List:");
                    int i = 1;
                    for (BankAccount account : accounts) {
                        System.out.print(i + ". ");
                        account.information();
                        i++;
                    }
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    String searchName = scanner.nextLine();
                    boolean found = false;
                    for (BankAccount account : accounts) {
                        if (account.getUsername().equals(searchName)) {
                            account.information();
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Account not found");
                    }
                    break;
                case 4:
                    System.out.print("Enter username: ");
                    String depositName = scanner.nextLine();
                    boolean foundDeposit = false;
                    for (BankAccount account : accounts) {
                        if (account.getUsername().equals(depositName)) {
                            System.out.print("Deposit: ");
                            double amount = scanner.nextDouble();
                            account.deposit(amount);
                            transactions.push("Deposit " + amount + " to " + depositName);
                            System.out.println("New balance: " + account.getBalance());
                            foundDeposit = true;
                            break;
                        }
                    }
                    if (!foundDeposit) {
                        System.out.println("Account not found");
                    }
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Enter username: ");
                    String withdrawName = scanner.nextLine();
                    boolean foundWithdraw = false;
                    for (BankAccount account : accounts) {
                        if (account.getUsername().equals(withdrawName)) {
                            System.out.print("Withdraw: ");
                            double amount = scanner.nextDouble();
                            if (account.withdraw(amount)) {
                                transactions.push("Withdraw " + amount + " from " + withdrawName);
                                System.out.println("New balance: " + account.getBalance());
                            } else {
                                System.out.println("Insufficient balance!");
                            }
                            foundWithdraw = true;
                            break;
                        }
                    }
                    if (!foundWithdraw) {
                        System.out.println("Account not found");
                    }
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.print("Enter username: ");
                    String billName = scanner.nextLine();
                    for (BankAccount account : accounts) {
                        if (account.getUsername().equals(billName)) {
                            System.out.print("Enter bill amount: ");
                            double amount = scanner.nextDouble();
                            if (account.withdraw(amount)) {
                                transactions.push("Bill payment " + amount + " from " + billName);
                                System.out.println("Bill paid. New balance: " + account.getBalance());
                            } else {
                                System.out.println("Insufficient balance!");
                            }
                            break;
                        }
                    }
                    scanner.nextLine();
                    break;
                case 7:
                    if (!transactions.isEmpty()) {
                        System.out.println("Last transaction: " + transactions.peek());
                    } else {
                        System.out.println("No transactions yet");
                    }
                    break;
                case 8:
                    if (!transactions.isEmpty()) {
                        String removed = transactions.pop();
                        if(removed.charAt(0) == 'W') {
                            System.out.println("Undo → Withdraw removed");
                        }else if(removed.charAt(0) == 'D') {
                            System.out.println("Undo → Deposit removed");
                        }else {
                            System.out.println("Undo → Bill payment removed");
                        }
                    } else {
                        System.out.println("Nothing to undo");
                    }
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
