import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.time.DayOfWeek;

public class Main {
    public static void main(String[] args) {
        DataInitializer.seed();
        Scanner scanner = new Scanner(System.in);
        MemberService memberService = new MemberService();
        AdminService adminService = new AdminService();
        TrainerService trainerService = new TrainerService();
        RoomBookingService roomBookingService = new RoomBookingService();
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
                case 1: memberLogin(scanner, memberService,adminService, roomBookingService,trainerService);break;
                case 2: adminLogin(scanner, adminService, roomBookingService,trainerService);break;
                case 3: registerMemberMenu(scanner, memberService);break;
                case 4: trainerLogin(scanner, trainerService);break;
                case 0: {
                    System.out.println("Exiting system...");
                    return;
                }
            }
        }

    }

    private static void memberLogin(Scanner scanner, MemberService memberService,AdminService adminService, RoomBookingService roomBookingService, TrainerService trainerService) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Member member = memberService.findMemberByEmail(email);
        if (member == null) {
            System.out.println("No member found with that email.");
            return;
        }

        memberMenu(scanner, memberService, member, adminService, roomBookingService, trainerService);
    }

    private static void memberMenu(Scanner scanner,MemberService memberService, Member member, AdminService adminService, RoomBookingService roomBookingService, TrainerService trainerService) {
        while (true) {
            System.out.println("\n===== MEMBER MENU =====");
            System.out.println("1. Profile Management");
            System.out.println("2. Register for Group Class");
            System.out.println("3. Register for Personal Training Session");
            System.out.println("4. View Dashboard");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: profileManagementMenu(scanner,memberService, member);break;
                case 2: registerForGroupClass(scanner,memberService, member);break;
                case 3: createPTSession(scanner,memberService, adminService,trainerService,roomBookingService);break;
                case 4: viewDashboard(memberService, member);break;
                case 0:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void adminLogin(Scanner scanner, AdminService adminService,RoomBookingService roomBookingService,TrainerService trainerService) {
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();

        if(!adminService.isAdmin(email)){
            System.out.println("Invalid email");
            return;
        }

        adminMenu(scanner,roomBookingService,adminService,trainerService);
    }

    private static void adminMenu(Scanner scanner,RoomBookingService roomBookingService, AdminService adminService, TrainerService trainerService) {
        while (true) {
            System.out.println("\n-- ADMIN MENU --");
            System.out.println("1. Room Booking");
            System.out.println("2. Create Group Fitness Class");
            System.out.println("0. Return to Main Menu");
            System.out.println("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    RoomBooking(scanner,roomBookingService);break;
                case 2:
                    createGroupFitnessClass(scanner, adminService, trainerService,roomBookingService);break;
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
                case 2: upcomingSessions(scanner, trainerService, trainer);break;
                case 3: memberLookup(scanner, trainerService, trainer);break;
                case 0:
                    System.out.println("Returning to Main Menu");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
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

        System.out.print("Average Heart Rate during workout: ");
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
                            " | Average Heart Rate During Workout: " + healthMetric.getHeartRate()
            );

        }
    }

    public static void viewDashboard(MemberService memberService, Member member){
        System.out.println("\n--- MEMBER DASHBOARD ---");

        Member loadedMember = memberService.loadMemberForDashboard(member.getId());
        if(loadedMember.getHealthMetrics().isEmpty()) {
            System.out.println("No health metrics recorded yet.");
        }else{
            HealthMetric latest = loadedMember.getHealthMetrics()
                    .stream()
                    .max((a,b) -> a.getRecordedAt().compareTo(b.getRecordedAt()))
                    .get();

            System.out.println("\nLatest Health Metric:");
            System.out.println("Date: " + latest.getRecordedAt());
            System.out.println("Height: " + latest.getHeight());
            System.out.println("Weight: " + latest.getWeight());
            System.out.println("Average Heart Rate during Workout: " + latest.getHeartRate());
        }

        if(loadedMember.getFitnessGoals().isEmpty()) {
            System.out.println("No fitness goals recorded yet.");
        }else{
            System.out.println("Fitness Goals:");
            for (FitnessGoal goal : loadedMember.getFitnessGoals()) {
                System.out.println("- " + goal.getGoalType() + " | Target: " + goal.getTargetValue());
            }
        }

        long pastClassCount = loadedMember.getClassRegistrations()
                .stream()
                .filter(reg-> reg.getGroupClass().getClassTime().isBefore(LocalDateTime.now()))
                .count();
        System.out.println("\nPast Class Count: " + pastClassCount);

        List<PersonalTrainingSession> upcomingSessions = loadedMember.getPersonalTrainingSessions()
                .stream()
                .filter(pts-> pts.getSessionTime().isAfter(LocalDateTime.now()))
                .toList();

        if(upcomingSessions.isEmpty()) {
            System.out.println("\nNo upcoming training sessions recorded yet.");
        }else{
            System.out.println("\nUpcoming Training Sessions:");
            for(PersonalTrainingSession pts : upcomingSessions) {
                System.out.println("- " + pts.getSessionTime() + " with " + pts.getTrainer().getName());
            }
        }
    }

    public static void registerForGroupClass(Scanner scanner, MemberService memberService, Member member) {
        System.out.println("\n--- REGISTER FOR GROUP FITNESS CLASS ---");

        List<GroupFitnessClass> groupFitnessClasses = memberService.getAllGroupClasses();

        if(groupFitnessClasses.isEmpty()) {
            System.out.println("No group fitness classes currently available. ");
            return;
        }
        for(GroupFitnessClass c : groupFitnessClasses) {
            System.out.println(
                    "ID: " + c.getId() +
                            " | Class: " + c.getClassName() +
                            " | Time: " + c.getClassTime() +
                            " | Room: " + c.getRoom().getRoomNumber() +
                            " | Capacity: " + c.getRegistrations().size() + "/" + c.getCapacity()
            );
        }

        System.out.print("Enter Class ID to register for a class (or press 0 to cancel): ");
        int classId = Integer.parseInt(scanner.nextLine());

        if (classId == 0) {
            System.out.println("Registration cancelled.");
            return;
        }

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
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                System.out.print("Enter start datetime (yyyy-MM-dd HH:mm): ");
                LocalDateTime start = LocalDateTime.parse(scanner.nextLine(), dtf);

                System.out.print("Enter end datetime (yyyy-MM-dd HH:mm): ");
                LocalDateTime end = LocalDateTime.parse(scanner.nextLine(), dtf);

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

                trainerService.removeAvailability(currentSlots.get(index));
                System.out.println("Availability slot removed.");
                break;

            case 0:
                return;

            default:
                System.out.println("Invalid choice");
        }
    }

    private static void upcomingSessions(Scanner scanner, TrainerService trainerService, Trainer trainer){
        System.out.print("\n--- UPCOMING SESSIONS --- ");

        List<PersonalTrainingSession> ptSessions = trainerService.getUpcomingPtSessions(trainer);
        List<GroupFitnessClass> classes = trainerService.getUpcomingClasses(trainer);



        //pt sessions
        System.out.println("\n Personal Training Sessions:");
        if(ptSessions.isEmpty()){
            System.out.println("None Scheduled.");
        } else {
            for(PersonalTrainingSession ptSession : ptSessions){
                System.out.printf("  %s   |  Member: %s  |  Room: %s\n",
                        ptSession.getSessionTime(),
                        ptSession.getMember().getName(),
                        ptSession.getRoom().getRoomNumber());
            }
        }

        //group classes
        System.out.println("\n Group Classes:");
        if(classes.isEmpty()){
            System.out.println("None Scheduled.");
        } else {
            for(GroupFitnessClass c : classes){
                System.out.printf("  %s   |  Class: %s  |  Capacity: %s  |  Room: %s\n",
                        c.getClassTime(),
                        c.getClassName(),
                        c.getCapacity(),
                        c.getRoom());
            }
        }
    }

    private static void memberLookup(Scanner scanner, TrainerService trainerService, Trainer trainer){
        System.out.println("\n--- MEMBER LOOKUP --- ");
        System.out.print("Enter member name to search: ");
        String searchN =  scanner.nextLine().trim();

        List<Member> results = trainerService.searchMembers(trainer, searchN);
        //System.out.println("made it here");

        if(results.isEmpty()){
            System.out.println("No members found.");
        } else {
            System.out.println("\n --- MEMBERS ---");
            for (Member m : results){
                FitnessGoal g = trainerService.getCurrentGoal(m);
                HealthMetric hm = trainerService.getLastMetric(m);

                System.out.printf("Name: %s%n", m.getName());

                if (g != null) {
                    System.out.printf("Current Goal: %s — Target: %.2f, Achieved: %s, Created: %s%n",
                            g.getGoalType(),
                            g.getTargetValue(),
                            g.isAchieved() ? "Yes" : "No",
                            g.getCreatedAt()
                    );
                } else {
                    System.out.println("Current Goal: None");
                }

                if (hm != null) {
                    System.out.printf("Last Metric: Height: %.1f cm, Weight: %.1f lbs, Heart Rate: %d bpm, Recorded At: %s%n",
                            hm.getHeight(),
                            hm.getWeight(),
                            hm.getHeartRate(),
                            hm.getRecordedAt()
                    );
                } else {
                    System.out.println("Last Metric: None");
                }

                System.out.println("--------------------");
            }
        }
     }

    private static void RoomBooking(Scanner scanner, RoomBookingService roomBookingService){
        System.out.println("\n--- ROOM BOOKING ---");
        System.out.println("1. Assign room to Group Fitness Class");
        System.out.println("2. Assign room to Personal Training Session");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:{
                List<GroupFitnessClass> classes = roomBookingService.getAllGroupFitnessClasses();
                if(classes.isEmpty()){
                    System.out.println("No classes exist.");
                    return;
                }
                System.out.println("\nGROUP FITNESS CLASSES:");
                for (GroupFitnessClass c : classes) {
                    System.out.println("Class ID: " + c.getId() + " | " + c.getClassName() +
                            " | "  + c.getClassTime() +
                            " | Room: " + (c.getRoom() == null ? "None" : c.getRoom().getRoomNumber()));
                }

                System.out.print("Enter class ID to assign room: ");
                int classId = scanner.nextInt();
                scanner.nextLine();

                List<Room> rooms = roomBookingService.getAllRooms();
                System.out.println("\nAVAILABLE ROOMS:");
                for (Room r : rooms) {
                    System.out.println("Room ID: " + r.getId() +
                            " | Room Number: " + r.getRoomNumber() +
                            " | Capacity: " + r.getCapacity());
                }

                System.out.print("Enter Room ID to assign: ");
                int roomId = scanner.nextInt();
                scanner.nextLine();

                boolean success = roomBookingService.assignRoomToClass(classId, roomId);
                System.out.println(success ? "Room booked successfully!" : "Room already booked at that time.");
                break;
            }
            case 2:{
                List<PersonalTrainingSession> sessions = roomBookingService.getAllPersonalTrainingSessions();
                if(sessions.isEmpty()){
                    System.out.println("No Personal Training sessions exist.");
                    return;
                }
            System.out.println("\nPERSONAL TRAINING SESSIONS:");
                for (PersonalTrainingSession p : sessions) {
                    System.out.println("Class ID: " + p.getId() + " | " + p.getTrainer() +
                            " | " + p.getSessionTime() + " " +
                            " | Room: " + (p.getRoom() == null ? "None" : p.getRoom().getRoomNumber()));
                }

                System.out.print("Enter Personal Training Session ID to assign room: ");
                int sessionId = scanner.nextInt();
                scanner.nextLine();

                List<Room> rooms = roomBookingService.getAllRooms();
                System.out.println("\nAVAILABLE ROOMS:");
                for (Room r : rooms) {
                    System.out.println("Room ID: " + r.getId() +
                            " | Room Number: " + r.getRoomNumber() +
                            " | Capacity: " + r.getCapacity());
                }

                System.out.print("Enter Room ID to assign: ");
                int roomId = scanner.nextInt();
                scanner.nextLine();

                boolean success = roomBookingService.assignRoomToClass(sessionId, roomId);
                System.out.println(success ? "Room booked successfully!" : "Room already booked at that time.");
                break;
            }

        }
    }

    private static void createGroupFitnessClass(Scanner scanner, AdminService adminService,TrainerService trainerService,RoomBookingService roomBookingService){
        System.out.println("\n--- CREATE GROUP FITNESS CLASS ---");

        System.out.print("Class name: ");
        String name = scanner.nextLine();

        System.out.print("Capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Trainer email: ");
        String trainerEmail = scanner.nextLine();

        System.out.print("Room number: ");
        int roomNum = Integer.parseInt(scanner.nextLine());

        System.out.print("Date & time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime time = LocalDateTime.parse(scanner.nextLine());

        boolean success = adminService.createGroupClass(
                name, capacity, trainerEmail, roomNum, time
        );

        if (success) {
            System.out.println("Group class created");
        } else {
            System.out.println("Failed to create class (room conflict or invalid trainer)");
        }


    }

    private static void createPTSession(Scanner scanner,MemberService memberService, AdminService adminService,TrainerService trainerService,RoomBookingService roomBookingService){
        System.out.println("\n--- CREATE PERSONAL TRAINING SESSION ---");

        System.out.print("Member email: ");
        String memberEmail = scanner.nextLine();

        System.out.print("Trainer email: ");
        String trainerEmail = scanner.nextLine();

        System.out.print("Room number: ");
        int roomNum = Integer.parseInt(scanner.nextLine());

        System.out.print("Date & time (yyyy-MM-ddTHH:mm): ");
        LocalDateTime time = LocalDateTime.parse(scanner.nextLine());

        boolean success = memberService.createPersonalSession(
                memberEmail, trainerEmail, roomNum, time
        );

        if (success) {
            System.out.println("Personal Training Session created");
        } else {
            System.out.println("Failed to create session");
        }
    }


}
