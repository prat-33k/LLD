import java.time.LocalDateTime;
import java.util.List;

// Main application entry point using the service-based architecture
public class CarRentalApplication {
    public static void main(String[] args) {
        // Initialize the system facade
        CarRentalFacade rentalSystem = new CarRentalFacade();
        
        // Add some cars to the system
        rentalSystem.createCar("sedan", "S001", "Toyota", "Camry", 2023, 50.0);
        rentalSystem.createCar("sedan", "S002", "Honda", "Accord", 2022, 45.0);
        rentalSystem.createCar("hatchback", "H001", "Volkswagen", "Golf", 2023, 40.0);
        rentalSystem.createCar("hatchback", "H002", "Mazda", "3", 2022, 38.0);
        rentalSystem.createCar("suv", "SUV001", "Toyota", "RAV4", 2023, 60.0);
        
        // Create a user
        User user = rentalSystem.registerUser("John Doe", "john@example.com", "1234567890");
        
        // User searches for available cars
        System.out.println("Available cars:");
        List<Car> availableCars = rentalSystem.searchAvailableCars();
        for (Car car : availableCars) {
            System.out.println(car);
        }
        
        // User searches for a specific car type
        System.out.println("\nAvailable Sedans:");
        List<Car> availableSedans = rentalSystem.searchAvailableCars("sedan");
        for (Car car : availableSedans) {
            System.out.println(car);
        }
        
        // Select the first available sedan
        Car selectedCar = availableSedans.get(0);
        
        // User reserves a car
        LocalDateTime pickupDate = LocalDateTime.now().plusDays(1);
        LocalDateTime returnDate = LocalDateTime.now().plusDays(3);
        
        // Complete the reservation process
        boolean success = rentalSystem.completeReservationProcess(
            user, selectedCar, pickupDate, returnDate, "CREDIT_CARD");
        
        if (success) {
            System.out.println("\nReservation completed successfully!");
            
            // Get the user's reservations
            List<Reservation> userReservations = user.getReservationHistory();
            System.out.println("\nUser's reservations:");
            for (Reservation reservation : userReservations) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("\nReservation failed. Please try again.");
        }
    }
}
