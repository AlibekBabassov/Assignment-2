import java.util.*;

public class MiniBankingMenu {
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        LinkedList<BankAccount> accounts = new LinkedList<>();
        Queue<BankAccount> accountRequests = new LinkedList<>();
        Queue<String> billQueue = new LinkedList<>();
        Stack<String> history = new Stack<>();

        while (true) {
            System.out.println("=== MAIN MENU ===");
            System.out.println("1 - Enter Bank");
            System.out.println("2 - Enter ATM");
            System.out.println("3 - Admin Area");
            System.out.println("4 - Exit");
            System.out.print("Select: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    bankMenu(scanner, accounts, accountRequests, billQueue, history);
                    break;
                case 2:
                    atmMenu(scanner, accounts, history);
                    break;
                case 3:
                    adminMenu(accounts, accountRequests, billQueue);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void bankMenu(Scanner scanner, LinkedList<BankAccount> accounts, Queue<BankAccount> accountRequests, Queue<String> billQueue, Stack<String> history) {
        while (true) {
            System.out.println("\n=== BANK MENU ===");
            System.out.println("1 - Submit account opening request");
            System.out.println("2 - Deposit money");
            System.out.println("3 - Withdraw money");
            System.out.println("4 - Pay bill");
            System.out.println("5 - Back");
            System.out.print("Select: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    int accountNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();

                    accountRequests.offer(new BankAccount(accountNumber, username, balance));
                    System.out.println("Account opening request added to queue");
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String depName = scanner.nextLine();

                    BankAccount depAcc = findAccount(accounts, depName);
                    if (depAcc != null) {
                        System.out.print("Deposit amount: ");
                        double depAmount = scanner.nextDouble();
                        scanner.nextLine();

                        depAcc.deposit(depAmount);
                        history.push("Deposit " + depAmount + " to " + depName);
                        System.out.println("New balance: " + depAcc.getBalance());
                    } else {
                        System.out.println("Account not found");
                    }
                    break;
                case 3:
                    System.out.print("Enter username: ");
                    String withName = scanner.nextLine();

                    BankAccount withAcc = findAccount(accounts, withName);
                    if (withAcc != null) {
                        System.out.print("Withdraw amount: ");
                        double withAmount = scanner.nextDouble();
                        scanner.nextLine();

                        if (withAcc.withdraw(withAmount)) {
                            history.push("Withdraw " + withAmount + " from " + withName);
                            System.out.println("New balance: " + withAcc.getBalance());
                        } else {
                            System.out.println("Insufficient balance");
                        }
                    } else {
                        System.out.println("Account not found");
                    }
                    break;
                case 4:
                    System.out.print("Enter bill name: ");
                    String bill = scanner.nextLine();
                    billQueue.offer(bill + " Bill");
                    System.out.println("Bill payment request added to queue");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void atmMenu(Scanner scanner, LinkedList<BankAccount> accounts, Stack<String> history) {
        while (true) {
            System.out.println("\n=== ATM MENU ===");
            System.out.println("1 - Balance enquiry");
            System.out.println("2 - Withdraw");
            System.out.println("3 - Back");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String name = scanner.nextLine();

                    BankAccount acc = findAccount(accounts, name);
                    if (acc != null) {
                        System.out.println("Balance: " + acc.getBalance());
                    } else {
                        System.out.println("Account not found");
                    }
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String atmName = scanner.nextLine();

                    BankAccount atmAcc = findAccount(accounts, atmName);
                    if (atmAcc != null) {
                        System.out.print("Withdraw amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();

                        if (atmAcc.withdraw(amount)) {
                            history.push("Withdraw " + amount + " from " + atmName + " via ATM");
                            System.out.println("New balance: " + atmAcc.getBalance());
                        } else {
                            System.out.println("Insufficient balance");
                        }
                    } else {
                        System.out.println("Account not found");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void adminMenu(LinkedList<BankAccount> accounts, Queue<BankAccount> accountRequests, Queue<String> billQueue) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1 - View account opening queue");
            System.out.println("2 - Process next account request");
            System.out.println("3 - View bill payment queue");
            System.out.println("4 - Process next bill payment");
            System.out.println("5 - Show approved accounts");
            System.out.println("6 - Back");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (accountRequests.isEmpty()) {
                        System.out.println("No pending account requests");
                    } else {
                        System.out.println("Pending account requests:");
                        for (BankAccount req : accountRequests) {
                            System.out.println(req.getUsername());
                        }
                    }
                    break;
                case 2:
                    if (!accountRequests.isEmpty()) {
                        BankAccount request = accountRequests.poll();
                        accounts.add(request);
                        System.out.println("Approved and moved to main LinkedList: " + request.getUsername());
                    } else {
                        System.out.println("No pending account requests");
                    }
                    break;
                case 3:
                    if (billQueue.isEmpty()) {
                        System.out.println("No pending bill payments");
                    } else {
                        System.out.println("Bill payment queue:");
                        for (String bill : billQueue) {
                            System.out.println(bill);
                        }
                    }
                    break;
                case 4:
                    if (!billQueue.isEmpty()) {
                        String bill = billQueue.poll();
                        System.out.println("Processing: " + bill);
                    } else {
                        System.out.println("No bills to process");
                    }
                    break;
                case 5:
                    if (accounts.isEmpty()) {
                        System.out.println("No approved accounts");
                    } else {
                        System.out.println("Approved accounts:");
                        for (BankAccount acc : accounts) {
                            acc.information();
                        }
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static BankAccount findAccount(LinkedList<BankAccount> accounts, String username) {
        for (BankAccount acc : accounts) {
            if (acc.getUsername().equalsIgnoreCase(username)) {
                return acc;
            }
        }
        return null;
    }
}
