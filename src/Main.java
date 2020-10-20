import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        ArrayList<Customer> customers = null;
        try {
            customers = createCustomers();
        } catch (Exception e) {
            System.out.println("Något gick snett vid inläsningen av kunderna. Kontrollera filen. Den måste heta customer.txt och ligga i /data");
            System.exit(0);
        }


        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print("Namn (förnamn och efternamn) eller personummer (ååmmddxxxx), tack: ");
        String input = scanner.nextLine();
        for (Customer customer : customers) {
            if (input.equalsIgnoreCase(customer.name) || input.equalsIgnoreCase(customer.socialSecurityNumber)) {
                if (customer.isValidMember()) {
                    System.out.println("Välkommen " + customer.name + ". Kör hårt!");
                    String workSession = customer.name + "\n" + customer.socialSecurityNumber + "\n" + LocalDate.now() + "\n\n";
                    Files.write(Paths.get("data/WorkoutSessions.txt"), workSession.getBytes(), StandardOpenOption.APPEND);
                } else {
                    System.out.println("Du har inte betalt din medlemsavgift för i år.");
                }
                System.exit(0);
            }
        }
        System.out.println("Ledsen. Men du finns inte registrerad som kund i vår register.");
        System.exit(0);
    }

    private static ArrayList<Customer> createCustomers() throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();
        int lineNumber = 0;
        Customer customer = null;
        List<String> lines = Files.readAllLines(Path.of("data/Customers.txt"));
        for (String line : lines) {
            if (line.isEmpty()) continue;

            lineNumber++;
            if (lineNumber % 2 == 1) {
                String[] socialSecurityNumberAndName = line.split(",");
                String socialSecurityNumber = socialSecurityNumberAndName[0].trim();
                String name = socialSecurityNumberAndName[1].trim();
                customer = new Customer(socialSecurityNumber, name);
            } else {
                customer.latestPayment = LocalDate.parse(line);
                customers.add(customer);
            }
        }
        return customers;
    }
}
