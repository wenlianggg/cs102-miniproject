public class Student {
    
    private String username;
    private String name;
    private int eDollars;

    Student(String username, String name, int eDollars) {
        this.username = username;
        this.name = name;
        this.eDollars = eDollars;
    }

    String getUsername() {
        return username;
    }

    String getName() {
        return name;
    }

    int getBalance() {
        return eDollars;
    }

    boolean sufficientBalanceFor(int amt) {
        return amt <= eDollars;
    }

    int deductFromBalance(int amt) {
        eDollars -= amt;
        return eDollars;
    }
}
