import java.util.*;

public class SwitchCase {
    public static void menu() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("This Is Practice Switch Case as A Menu");
            System.out.println("=======================================");
            System.out.println("Wanna Try ? ( Type Y / yes if wannt to try )");
            String decission = input.nextLine();
            while (decission.equalsIgnoreCase("Y") || decission.equalsIgnoreCase("yes")) {
                System.out.println("1.Even/Ods ");
                System.out.println("2.Left-Right Elbow Triangle ");
                System.out.println("3.Sided Elbow Triangle ");
                System.out.println("4.Right-Right Elbow Triangle ");
                System.out.println("5.Faktorial ");
                System.out.println("6.Calculate the Area of ​​a Circle");
                System.out.println("=======================================");
                System.out.println("Pilih Nomer : ");
                int pil = input.nextInt();
                switch (pil) {
                    case 1:
                        System.out.println("Nothing To Do");
                        break;
                    case 2:
                        System.out.println("Nothing To Do");
                        break;
                    case 3:
                        System.out.println("Nothing To Do");
                        break;
                    case 4:
                        System.out.println("Nothing To Do");
                        break;
                    case 5:
                        System.out.println("Nothing To Do");
                        break;
                    case 6:
                        System.out.println("Nothing To Do");
                        break;
                    default:
                        System.out.println("Menu Not Found");
                        break;
                }
                System.out.println("Wanna Try Again ? (Y/YES) ");
                decission = input.next();
            }
            System.out.print("Thank you for trying");
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}