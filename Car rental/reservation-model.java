import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

// Class to represent a car reservation
public class Reservation {
    private String reservationId;
    private User user;
    private Car car;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;
    private double totalCost;
    private ReservationStatus status;
    private Payment payment;
    
    public enum ReservationStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }
    
    public Reservation(User user, Car car, LocalDateTime pickupDate, LocalDateTime returnDate) {
        this.reservationId = UUID.randomUUID().toString().substring(0, 8);
        this.user = user;
        this.car = car;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.status = ReservationStatus.PENDING;
        
        // Calculate total cost based on rental duration and car price
        long days = ChronoUnit.DAYS.between(pickupDate, returnDate);
        if (days < 1) days = 1; // Minimum one day rental
        this.totalCost = car.getRentalPricePerDay() * days;
    }
    
    // Getters and setters
    public String getReservationId() {
        return reservationId;
    }
    
    public User getUser() {
        return user;
    }
    
    public Car getCar() {
        return car;
    }
    
    public LocalDateTime getPickupDate() {
        return pickupDate;
    }
    
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public ReservationStatus getStatus() {
        return status;
    }
    
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
    
    public Payment getPayment() {
        return payment;
    }
    
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    @Override
    public String toString() {
        return String.format("Reservation #%s - %s\n" +
                            "Car: %s\n" +
                            "Pickup: %s\n" +
                            "Return: %s\n" +
                            "Total Cost: $%.2f\n" +
                            "Status: %s",
                            reservationId, user.getName(),
                            car.toString(),
                            pickupDate.toString(),
                            returnDate.toString(),
                            totalCost,
                            status);
    }
}
