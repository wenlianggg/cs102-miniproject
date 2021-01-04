import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        int choice = 0;

        while (choice != 7) {
            App brosApp = new App();
            brosApp.printMenu();
            choice = brosApp.askChoice();
            brosApp.evalChoice(choice);
        }
    }

    public void printMenu() {
        System.out.println("== BROS :: Main Menu ==");
        System.out.println();
        System.out.println("1. List all students");
        System.out.println("2. List all facilities");
        System.out.println("3. List all bookings");
        System.out.println("4. List all bookings by a student");
        System.out.println("5. Add a student");
        System.out.println("6. Book a facility");
        System.out.println("7. Quit Application");
    }

    public int askChoice() {
        int input = 0;
        System.out.print("Enter your choice > ");

        Scanner sc = new Scanner(System.in);
        input = sc.nextInt();
        sc.close();

        return input;
    }

    public void evalChoice(int choice) {
        switch(choice) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            default:
                return;
        }
    }
}
