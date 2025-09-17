import java.util.Scanner;

public class task {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the first integer: ");
        int n1 = sc.nextInt();

        System.out.print("Enter the second integer: ");
        int n2 = sc.nextInt();

        System.out.print("Enter a floating-point number: ");
        float n3 = sc.nextFloat();

        System.out.print("Enter a single character: ");
        char ch = (sc.next().charAt(0));

        System.out.print("Enter a boolean value (true/false): ");
        boolean b = sc.nextBoolean();

        sc.nextLine();
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.println("Sum of " + n1 + " and " + n2 + " is: " + (n1 + n2));
        System.out.println("Difference between " + n1 + " and " + n2 + " is: " + (n1 - n2));
        System.out.println("Product of " + n1 + " and " + n2 + " is: " + (n1 * n2));
        System.out.println(
                "Quotient of " + n1 + " / " + n2 + " is: " + n1 / (float) n2 + " and remainder is: " + n1 % n2);

        System.out.println(n3 + " multiplied by 2 is: " + n3 * 2);
        System.out.println("Square of " + n3 + " is: " + n3 * n3);

        System.out.println("The next character after '" + ch + "' is: " + (char) (ch + 1));
        char p = (char) (ch - 1);
        System.out.println("The previous character before '" + ch + "' is: " + p);

        System.out.println("The opposite of " + b + " is: " + !b);
        System.out.println("Was the original value true? " + (b ? "Yes" : "No"));

        System.out.println("HELLO, " + name.toUpperCase() + "!");

    }
}
