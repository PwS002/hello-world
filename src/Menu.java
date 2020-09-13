import java.util.Scanner;

public class Menu {
    public static void Menu() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("This Is Practice Switch Case as A Menu");
            System.out.println("=======================================");
            System.out.println("Wanna Try ? ( Type Y / yes if want to try )");
            String decision = input.nextLine();
            while (decision.equalsIgnoreCase("Y") || decision.equalsIgnoreCase("yes")) {
                System.out.println("1.Even/Ods ");
                System.out.println("2.Left-Right Elbow Triangle ");
                System.out.println("3.Sided Elbow Triangle ");
                System.out.println("4.Right-Right Elbow Triangle ");
                System.out.println("5.Faktorial ");
                System.out.println("6.Calculate the Area of ​​a Circle");
                System.out.println("=======================================");
                System.out.println("Choose Number : ");
                int pil = input.nextInt();
                SwitchCase.switchCase(pil);
                System.out.println("Wanna Try Again ? (Y/YES) ");
                decision = input.next();
            }
            System.out.print("Thank you for trying");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
