import java.util.*;
//Task 5
public class Main{
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<BankAccount> accounts = new LinkedList<>();
        Stack<String> transactions = new Stack<>();
        Queue<String> billQueue = new LinkedList<>();
        Queue<BankAccount> accountRequests = new LinkedList<>();
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
            System.out.println("9. Add bill payment request");
            System.out.println("10. Process next bill payment");
            System.out.println("11. Display queue");
            System.out.println("12. Add account request to queue");
            System.out.println("13. Process request");
            System.out.println("14. Display pending requests");
            System.out.println("15. Exit");
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
                    System.out.print("Enter bill name: ");
                    String bill = scanner.nextLine();
                    billQueue.offer(bill);
                    System.out.println("Added: " + bill + " Bill");
                    break;
                case 10:
                    if (!billQueue.isEmpty()) {
                        String nextBill = billQueue.poll();
                        System.out.println("Processing: " + nextBill + " Bill");
                    } else {
                        System.out.println("Queue is empty");
                    }
                    break;
                case 11:
                    if (billQueue.isEmpty()) {
                        System.out.println("Queue is empty");
                    } else {
                        System.out.print("Remaining:");
                        for (String b : billQueue) {
                            System.out.println(b + " Bill");
                        }
                    }
                    break;
                case 12:
                    System.out.print("Enter account number: ");
                    int accNum = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter username: ");
                    String Name = scanner.nextLine();
                    System.out.print("Enter balance: ");
                    double Balance = scanner.nextDouble();
                    scanner.nextLine();
                    accountRequests.offer(new BankAccount(accNum, Name, Balance));
                    System.out.println("Request added");
                    break;
                case 13:
                    if (!accountRequests.isEmpty()) {
                        BankAccount acc = accountRequests.poll();
                        accounts.add(acc);
                        System.out.println("Account approved: " + acc.getUsername());
                    } else {
                        System.out.println("No pending requests");
                    }
                    break;
                case 14:
                    if (accountRequests.isEmpty()) {
                        System.out.println("No pending requests");
                    } else {
                        System.out.println("Pending requests:");
                        for (BankAccount acc : accountRequests) {
                            System.out.println(acc.getUsername());
                        }
                    }
                    break;
                case 15:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
