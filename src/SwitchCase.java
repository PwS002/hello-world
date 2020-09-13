import java.util.*;

public class SwitchCase {
    static Scanner input = new Scanner(System.in);

    public static void switchCase(int pil) {
        switch (pil) {
            case 1:
                System.out.print("Input Variable : ");
                int valInput = input.nextInt();
                EvenOdd.EvenOdd(valInput);
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
    }
}