public class Task6 {
    public static void main() {
        BankAccount[] accounts = new BankAccount[3];
        accounts[0] = new BankAccount(1, "Ali", 150000);
        accounts[1] = new BankAccount(2, "Sara", 250000);
        accounts[2] = new BankAccount(3, "Alibek", 160000);

        for (int i = 0; i < accounts.length; i++) {
            System.out.println((i + 1) + ". " + accounts[i].getUsername() +
                    " – Balance: " + accounts[i].getBalance());
        }
    }
}
