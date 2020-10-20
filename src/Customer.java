import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Customer {
    String socialSecurityNumber;
    String name;
    LocalDate latestPayment;

    // Konstruktor
    public Customer(String socialSecurityNumber, String name) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
    }
    public boolean isValidMember(){
        LocalDate dateNow = LocalDate.now();
        LocalDate dateOneYearAgo = dateNow.minusYears(1);
        return latestPayment.isAfter(dateOneYearAgo);
    }
}
