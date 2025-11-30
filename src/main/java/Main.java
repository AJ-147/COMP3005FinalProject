import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.DayOfWeek;

public class Main {
    public static void main(String[] args) {
        DataInitializer.seed();
        Scanner scanner = new Scanner(System.in);
        MemberService memberService = new MemberService();
        AdminService adminService = new AdminService();
        TrainerService trainerService = new TrainerService();
        while (true) {
            System.out.println("\n---- Welcome to the Fitness Center System----");
            System.out.println("1. Member Login");
            System.out.println("2. Administrative Staff Login");
            System.out.println("3. Register New Member");
            System.out.println("4. Trainer Login");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: memberLogin(scanner, memberService);break;
                case 2: adminLogin(scanner, adminService);break;
                case 3: registerMemberMenu(scanner, memberService);break;
                case 4: trainerLogin(scanner, trainerService);break;
                case 0: {
                    System.out.println("Exiting system...");
                    return;
                }
            }
        }

    }

    private static void memberLogin(Scanner scanner, MemberService memberService) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Member member = memberService.findMemberByEmail(email);
        if (member == null) {
            System.out.println("No member found with that email.");
            return;
        }

        memberMenu(scanner, memberService, member);
    }

    private static void memberMenu(Scanner scanner,MemberService memberService, Member member) {
        while (true) {
            System.out.println("\n===== MEMBER MENU =====");
            System.out.println("1. Profile Management");
            System.out.println("2. Register for Group Class");
            System.out.println("3. View Dashboard");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: profileManagementMenu(scanner,memberService, member);break;
                case 2: registerForGroupClass(scanner,memberService, member);break;
                case 3://viewDashboard(member);
                    break;
                case 0:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void adminLogin(Scanner scanner, AdminService adminService){
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();

        if(!adminService.isAdmin(email)){
            System.out.println("Invalid email");
            return;
        }

        adminMenu(scanner);
    }

    private static void adminMenu(Scanner scanner){
        while (true) {
            System.out.println("\n-- ADMIN MENU --");
            System.out.println("1.Room Booking");
            System.out.println("0. Return to Main Menu");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    roomBooking(scanner);break;
                case 0:
                    System.out.println("Returning to Main Menu");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void trainerLogin(Scanner scanner, TrainerService trainerService){
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();

        Trainer trainer =  trainerService.findTrainerByEmail(email);
        if(trainer == null){
            System.out.println("No trainer found with that email");
            return;
        }
        trainerMenu(scanner, trainerService, trainer);
    }

    private static void trainerMenu(Scanner scanner, TrainerService trainerService, Trainer trainer){
        while (true) {
            System.out.println("\n-- TRAINER MENU --");
            System.out.println("1. Availability");
            System.out.println("2. View Upcoming Sessions");
            System.out.println("3. View Member Profiles");
            System.out.println("0. Return to Main Menu");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: availability(scanner, trainerService, trainer);break;
                case 2: //upcomingSessions(scanner, trainerService, trainer);break;
                case 3: //viewProfiles(scanner, trainerService, trainer);break;
                case 0:
                    System.out.println("Returning to Main Menu");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }


    private static void roomBooking(Scanner scanner) {
        System.out.println("\n--- ROOM BOOKING (admin feature placeholder) ---");
        System.out.println("This feature will assign rooms & prevent double-booking.");
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

    public static void profileManagementMenu(Scanner scanner, MemberService memberService, Member member) {
        System.out.println("\n--- PROFILE MANAGEMENT ---");


        while (true) {
            System.out.println("\n-- What would you like to update? --");
            System.out.println("1. Update Personal Details");
            System.out.println("2. Add Fitness Goal");
            System.out.println("3. Add Health Metric");
            System.out.println("4. View Health History");
            System.out.println("0. Return to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1: updatePersonalDetails(scanner, memberService, member);break;
                case 2: addFitnessGoal(scanner, memberService, member);break;
                case 3: addHealthMetric(scanner, memberService, member);break;
                case 4: viewHealthHistory(scanner, memberService, member);break;
                case 0: {return;}
                default:
                    System.out.println("Invalid choice.");

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

    public static void viewHealthHistory(Scanner scanner, MemberService memberService, Member member) {
        System.out.println("\n--- HEALTH HISTORY ---");

        Member loadedMember = memberService.loadMemberWithHealthHistory(member.getId());


        if (loadedMember.getHealthMetrics().isEmpty()) {
            System.out.println("No health metrics recorded yet.");
            return;
        }

        for (HealthMetric healthMetric : loadedMember.getHealthMetrics()) {
            System.out.println(
                    "Date: " + healthMetric.getRecordedAt() +
                            " | Height: " + healthMetric.getHeight() +
                            " | Weight: " + healthMetric.getWeight() +
                            " | Heart Rate: " + healthMetric.getHeartRate()
            );

        }
    }

    public static void dashboardMenu(Scanner scanner, MemberService memberService) {
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        //memberService.showDashboard(memberId);
    }

    public static void registerForGroupClass(Scanner scanner, MemberService memberService, Member member) {
        System.out.println("\n--- REGISTER FOR GROUP FITNESS CLASS ---");

        System.out.print("Enter Class ID to register for: ");
        int classId = Integer.parseInt(scanner.nextLine());

        boolean success = memberService.registerForClass(member.getEmail(), classId);

        if (success) {
            System.out.println("Successfully registered!");
        } else {
            System.out.println("Registration failed. Class may be full or unavailable.");
        }
    }

    public static void availability(Scanner scanner, TrainerService trainerService, Trainer trainer) {
        System.out.println(" \n  --- AVAILABILITY MANAGEMENT--- ");

        System.out.println("1. View Availability");
        System.out.println("2. Add One-Time Availability");
        System.out.println("3. Add Recurring Availability");
        System.out.println("4. Remove Availability");
        System.out.println("0. Return to Trainer Menu");
        System.out.println("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:  //view current trainer availability
                List<TrainerAvailability> slots = trainerService.getAvailability(trainer);
                if(slots.isEmpty()){
                    System.out.println("No availability found.");
                } else {
                    System.out.println("Your availability slots: ");
                    for(int i = 0; i < slots.size(); i++){
                        TrainerAvailability slot = slots.get(i);

                        if (slot.isRecurring()) {
                            System.out.printf(
                                    "%d. RECURRING — %s: %s to %s%n",
                                    i + 1,
                                    slot.getDayOfWeek(),
                                    slot.getStartTime(),
                                    slot.getEndTime()
                            );
                        } else {
                            System.out.printf(
                                    "%d. ONE-TIME — %s to %s%n",
                                    i + 1,
                                    slot.getStartDateTime(),
                                    slot.getEndDateTime()
                            );
                        }
                    }
                }
                break;

            case 2: //add one-time availability
                System.out.print("Enter start datetime (yyyy-MM-dd): ");
                LocalDateTime start = LocalDateTime.parse(scanner.nextLine());

                System.out.print("Enter end datetime (yyyy-MM-dd): ");
                LocalDateTime end = LocalDateTime.parse(scanner.nextLine());

                boolean addedOneTime = trainerService.addOneTimeAvailability(trainer, start, end);

                if (addedOneTime)
                    System.out.println("One-time slot successfully added.");
                else
                    System.out.println("ERROR: Slot overlaps with existing availability.");

                break;

            case 3: //reoccurring avail
                System.out.print("Enter day of week (MONDAY, TUESDAY, etc): ");
                DayOfWeek day = DayOfWeek.valueOf(scanner.nextLine().toUpperCase());

                System.out.print("Start time (HH:mm): ");
                LocalTime rStart = LocalTime.parse(scanner.nextLine());

                System.out.print("End time (HH:mm): ");
                LocalTime rEnd = LocalTime.parse(scanner.nextLine());

                boolean addedRecurring =
                        trainerService.addRecurringAvailability(trainer, day, rStart, rEnd);

                if (addedRecurring)
                    System.out.println("Recurring availability added.");
                else
                    System.out.println("ERROR: Overlaps existing availability.");

                break;

            case 4:  //remove availability
                List<TrainerAvailability> currentSlots = trainerService.getAvailability(trainer);

                if(currentSlots.isEmpty()){
                    System.out.println("No availability found.");
                    break;
                }

                System.out.println("\n Select slot to remove");
                for(int i = 0; i < currentSlots.size(); i++){
                    TrainerAvailability slot = currentSlots.get(i);

                    if (slot.isRecurring()) {
                        System.out.printf(
                                "%d. RECURRING — %s: %s to %s%n",
                                i + 1,
                                slot.getDayOfWeek(),
                                slot.getStartTime(),
                                slot.getEndTime()
                        );
                    } else {
                        System.out.printf(
                                "%d. ONE-TIME — %s to %s%n",
                                i + 1,
                                slot.getStartDateTime(),
                                slot.getEndDateTime()
                        );
                    }
                }

                System.out.print("Enter number to remove: ");
                int index = scanner.nextInt() - 1;
                scanner.nextLine();

                if (index < 0 || index >= currentSlots.size()) {
                    System.out.println("Invalid selection.");
                    break;
                }

                trainerService.removeAvailability(trainer, currentSlots.get(index));
                System.out.println("Availability slot removed.");
                break;

            case 0:
                return;

            default:
                System.out.println("Invalid choice");
        }
    }




}
