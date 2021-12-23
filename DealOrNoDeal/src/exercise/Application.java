package exercise;
import java.util.*;

public class Application {
    public static Case personalCase;
    public static ArrayList<Case> cases = new ArrayList<>();
    public static ArrayList<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 5000, 10000,
            25000, 50000, 75000,100000, 200000, 300000, 400000, 500000, 750000, 1000000));
    public static int turns = 0;

    public static void main(String[] args) {
        startGame();
    }

    public static void shuffleValues() {
        Collections.shuffle(values);
    }

    public static void play(int remove, int countdown) {
        turns++;
        if (remove != 1 && remove != 2) {
            for (int i = remove; i > 0; i--)
                openYourCase();
            bankOffer(--remove, 0);
        } else if (remove == 2) {
            openYourCase();
            openYourCase();
            bankOffer(1, 3);
        } else {
            turns++;
            if (countdown > 0){
                openYourCase();
                bankOffer(1, --countdown);
            } else {
                openYourCase();
                lastRound();
            }
        }
    }

    public static void bankOffer(int remove, int newCountdown) {
        int sum = 0;
        int left = cases.size() + 1;
        for (int i = 0; i < cases.size(); i++) {
            sum += cases.get(i).getValue();
        }

        sum += personalCase.getValue();

        int offer = ((sum / left) * turns) / 10;
        printRemainingCases();
        System.out.println("The bank offers you " + offer + "$.");
        System.out.println("Do you accept the offer?");
        System.out.println("Please press Y/y for YES and N/n for NO:");
        if (dealOrNoDeal()) {
            System.out.println("Congratulations, you won: " + offer + "$");
        } else {
            play(remove, newCountdown);
        }
    }

    public static void openYourCase() {
        System.out.print("Pick a suitcase to eliminate from the game: ");
        Scanner scanner = new Scanner(System.in);
        try {
            int input = scanner.nextInt();
            if (input < 1 || input > 26) {
                System.out.println("Please select an actual case number.");
                openYourCase();
            } else if (cases.stream().anyMatch(n -> n.getNumber() == input)) {
                System.out.println("Case " + input + " was eliminated.");
                Case toBeRemoved = cases.stream().filter(s -> s.getNumber() == input).findFirst().get();
                System.out.println("It contains: " + toBeRemoved.getValue() + "$.");
                cases.remove(toBeRemoved);
            } else {
                System.out.println("You have already eliminated that case earlier.");
                openYourCase();
            }
        } catch (InputMismatchException ex){
            System.out.println("\u001B[31m" + "Please enter a number!" + "\u001B[0m");
            openYourCase();
        }

    }

    public static void printRemainingCases() {
        cases.stream().forEach(s -> System.out.print("[" + s.getNumber() + "] "));
        System.out.println();
    }

    public static void lastRound() {
        int sum = 0;
        for (int i = 0; i < cases.size(); i++) {
            sum += cases.get(i).getValue();
        }
        sum += personalCase.getValue();
        int offer = ((int) (sum / 2.0));
        System.out.println("The bank offers you " + offer + "$. ");
        System.out.println("Do you accept this offer?");
        System.out.println("Please press Y/y for YES and N/n for NO: ");
        if (!dealOrNoDeal()) {
            System.out.println("The suitcase with your price was number " + personalCase.getNumber());
            System.out.print("This suitcase is the last one remaining: ");
            printRemainingCases();
            System.out.println("Now we give you a chance to switch to this suitcase.");
            System.out.println("Do you want to switch suitcases?");
            System.out.println("Please press Y/y for YES and N/n for NO:");
        } else {
            System.out.println("Congratulations, you won " + offer + "$");
        }
        if (!dealOrNoDeal()) {
            System.out.println("You chose your personal case.");
            System.out.println("Congratulations, you won " + personalCase.getValue() + "$");
        } else {
            System.out.println("You chose the last case on the field: ");
            System.out.println("Congratulations, you won " + cases.get(0).getValue() + "$");
        }
    }
    public static boolean dealOrNoDeal() {
        Scanner scanner = new Scanner(System.in);
            String deal = scanner.nextLine();
            if (deal.equalsIgnoreCase("Y")) {
                return true;
            } else if (deal.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Please press Y/y for YES and N/n for NO:");
                return dealOrNoDeal();
            }
    }

    public static void setCases() {
        System.out.println("Please press D/d for debugging session or press any other key to start the game: ");
        Scanner scanner = new Scanner(System.in);
        if(!scanner.nextLine().equalsIgnoreCase("D")) {
            shuffleValues();
        }
        for (int i = 1; i < values.size()+1; i++) {
            cases.add(i-1, new Case(i, values.get(i-1)));
        }
        selectPersonalCase();
    }

    public static void selectPersonalCase() {
        printRemainingCases();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose your suitcase 1-26 with your price: ");
        try {
            int input = scanner.nextInt();
            if (!(input >= 1 && input <= 26)) {
                System.out.println("This is not a number between 1-26!");
                selectPersonalCase();
            } else {
                System.out.println("You chose number " + input);
                personalCase = cases.stream().filter(s -> s.getNumber() == input).findFirst().get();
                cases.remove(personalCase);
                printRemainingCases();
            }
        } catch (InputMismatchException ex) {
            System.out.println("\u001B[31m" + "Please enter a number!" + "\u001B[0m");
            selectPersonalCase();
        }
    }

    public static void startGame() {
        setCases();
        play(6, 0);
    }

}
