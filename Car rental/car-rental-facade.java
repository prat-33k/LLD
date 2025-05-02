import java.time.LocalDateTime;
import java.util.List;

// Facade class to coordinate all services
public class CarRentalFacade {
    private UserService userService;
    private CarService carService;
    private ReservationService reservationService;
    private PaymentService paymentService;
    
    public CarRentalFacade() {
        this.userService = new UserService();
        this.carService = new CarService();
        this.reservationService = new ReservationService(carService);
        this.paymentService = new PaymentService();
    }
    
    // User operations
    public User registerUser(String name, String email, String phone) {
        return userService.registerUser(name, email, phone);
    }
    
    public User getUser(String userId) {
        return userService.getUser(userId);
    }
    
    // Car operations
    public void addCar(Car car) {
        carService.addCar(car);
    }
    
    public Car createCar(String type, String carId, String make, String model, int year, double rentalPrice) {
        return carService.createCar(type, carId, make, model, year, rentalPrice);
    }
    
    public List<Car> searchAvailableCars() {
        return carService.searchAvailableCars();
    }
    
    public List<Car> searchAvailableCars(String carType) {
        return carService.searchAvailableCars(carType);
    }
    
    // Reservation operations
    public Reservation createReservation(User user, Car car, LocalDateTime pickupDate, LocalDateTime returnDate) {
        Reservation reservation = reservationService.createReservation(user, car, pickupDate, returnDate);
        userService.addReservationToUser(user, reservation);
        return reservation;
    }
    
    public void confirmReservation(Reservation reservation, Payment payment) {
        reservationService.confirmReservation(reservation, payment);
    }
    
    public void cancelReservation(Reservation reservation) {
        reservationService.cancelReservation(reservation);
    }
    
    public Reservation getReservation(String reservationId) {
        return reservationService.getReservation(reservationId);
    }
    
    // Payment operations
    public Payment processPayment(Reservation reservation, String paymentMethod) {
        Payment payment = paymentService.processPayment(reservation.getTotalCost(), paymentMethod);
        if (payment.getStatus() == Payment.PaymentStatus.COMPLETED) {
            confirmReservation(reservation, payment);
        }
        return payment;
    }
    
    // Business workflow: complete reservation process
    public boolean completeReservationProcess(User user, Car car, LocalDateTime pickupDate, 
                                             LocalDateTime returnDate, String paymentMethod) {
        try {
            // Create reservation
            Reservation reservation = createReservation(user, car, pickupDate, returnDate);
            
            // Process payment
            Payment payment = processPayment(reservation, paymentMethod);
            
            // Check payment status
            return payment.getStatus() == Payment.PaymentStatus.COMPLETED;
        } catch (Exception e) {
            System.err.println("Error completing reservation process: " + e.getMessage());
            return false;
        }
    }
}
