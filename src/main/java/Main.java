import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MemberService memberService = new MemberService();
        while (true) {
            System.out.println("\n---- MEMBER MENU ----");
            System.out.println("1. Register New Member");
            System.out.println("2. Profile Management");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); //flush newline

            if (choice == 1) {
                registerMemberMenu(scanner, memberService);
            } else if(choice==2){
                profileManagementMenu(scanner, memberService);
            }else if (choice == 0) {
                break;
            }
        }

        System.out.println("Exiting system...");
    }

    public static void registerMemberMenu(Scanner scanner, MemberService memberService) {
        System.out.println("\n--- USER REGISTRATION ---");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dobInput = scanner.nextLine();
        LocalDate dob = LocalDate.parse(dobInput);

        memberService.registerMember(name, email, dob);
    }

    public static void profileManagementMenu(Scanner scanner, MemberService memberService) {
        System.out.println("\n--- PROFILE MANAGEMENT ---");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Member member = memberService.findMemberByEmail(email);

        if (member == null) {
            System.out.println("No member found with that email.");
            return;
        }

        while (true) {
            System.out.println("\n-- What would you like to update? --");
            System.out.println("1. Update Personal Details");
            System.out.println("2. Add Fitness Goal");
            System.out.println("3. Add Health Metric");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                updatePersonalDetails(scanner, memberService, member);
            }
            else if (choice == 2) {
                addFitnessGoal(scanner, memberService, member);
            }
            else if (choice == 3) {
                addHealthMetric(scanner, memberService, member);
            }
            else if (choice == 0) {
                return;
            }
        }
    }

    private static void updatePersonalDetails(Scanner scanner, MemberService memberService, Member member) {
        System.out.print("New name: ");
        String newName = scanner.nextLine();

        System.out.print("New email: ");
        String newEmail = scanner.nextLine();

        System.out.print("New DOB (YYYY-MM-DD): ");
        LocalDate newDob = LocalDate.parse(scanner.nextLine());

        memberService.updatePersonalDetails(member, newName, newEmail, newDob);
    }

    private static void addFitnessGoal(Scanner scanner, MemberService memberService, Member member) {

        System.out.print("Describe your goal (e.g., 'Reach 180 lbs'): ");
        String goalDesc = scanner.nextLine();

        System.out.print("Target value (number): ");
        double target = scanner.nextDouble();
        scanner.nextLine();

        memberService.addFitnessGoal(member, goalDesc, target);
    }

    private static void addHealthMetric(Scanner scanner, MemberService memberService, Member member) {

        System.out.print("Height: ");
        double height = scanner.nextDouble();

        System.out.print("Weight: ");
        double weight = scanner.nextDouble();

        System.out.print("Heart Rate: ");
        int heartRate = scanner.nextInt();
        scanner.nextLine();

        memberService.addHealthMetric(member, height, weight, heartRate);
    }


}
