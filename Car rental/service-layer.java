// UserService class to handle user-related operations
public class UserService {
    private Map<String, User> users;
    
    public UserService() {
        this.users = new HashMap<>();
    }
    
    // Register a new user
    public User registerUser(String name, String email, String phone) {
        String userId = "U" + System.currentTimeMillis() % 10000;
        User newUser = new User(userId, name, email, phone);
        users.put(userId, newUser);
        return newUser;
    }
    
    // Get a user by ID
    public User getUser(String userId) {
        return users.get(userId);
    }
    
    // Update user information
    public boolean updateUser(String userId, String name, String email, String phone) {
        User user = users.get(userId);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            return true;
        }
        return false;
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    // Add a reservation to user's history
    public void addReservationToUser(User user, Reservation reservation) {
        user.addReservation(reservation);
    }
}

// CarService class to handle car-related operations
public class CarService {
    private List<Car> cars;
    
    public CarService() {
        this.cars = new ArrayList<>();
    }
    
    // Add a car to the system
    public void addCar(Car car) {
        cars.add(car);
    }
    
    // Remove a car from the system
    public boolean removeCar(String carId) {
        return cars.removeIf(car -> car.getCarId().equals(carId));
    }
    
    // Get a car by ID
    public Car getCar(String carId) {
        return cars.stream()
                 .filter(car -> car.getCarId().equals(carId))
                 .findFirst()
                 .orElse(null);
    }
    
    // Search for available cars
    public List<Car> searchAvailableCars() {
        return cars.stream()
                 .filter(Car::isAvailable)
                 .collect(Collectors.toList());
    }
    
    // Search for available cars by type
    public List<Car> searchAvailableCars(String carType) {
        return cars.stream()
                 .filter(car -> car.isAvailable() && car.getCarType().equalsIgnoreCase(carType))
                 .collect(Collectors.toList());
    }
    
    // Update car availability
    public void updateCarAvailability(Car car, boolean isAvailable) {
        car.setAvailable(isAvailable);
    }
    
    // Get all cars
    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }
    
    // Create a new car factory method
    public Car createCar(String type, String carId, String make, String model, int year, double rentalPrice) {
        Car car;
        switch (type.toLowerCase()) {
            case "sedan":
                car = new Sedan(carId, make, model, year, rentalPrice);
                break;
            case "hatchback":
                car = new Hatchback(carId, make, model, year, rentalPrice);
                break;
            case "suv":
                car = new SUV(carId, make, model, year, rentalPrice);
                break;
            default:
                throw new IllegalArgumentException("Unknown car type: " + type);
        }
        addCar(car);
        return car;
    }
}

// ReservationService class to handle reservation-related operations
public class ReservationService {
    private Map<String, Reservation> reservations;
    private CarService carService;
    
    public ReservationService(CarService carService) {
        this.reservations = new HashMap<>();
        this.carService = carService;
    }
    
    // Create a new reservation
    public Reservation createReservation(User user, Car car, LocalDateTime pickupDate, LocalDateTime returnDate) {
        if (!car.isAvailable()) {
            throw new IllegalStateException("Car is not available for reservation");
        }
        
        Reservation reservation = new Reservation(user, car, pickupDate, returnDate);
        reservations.put(reservation.getReservationId(), reservation);
        
        // Mark car as unavailable
        carService.updateCarAvailability(car, false);
        
        return reservation;
    }
    
    // Confirm a reservation
    public void confirmReservation(Reservation reservation, Payment payment) {
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);
        reservation.setPayment(payment);
    }
    
    // Cancel a reservation
    public void cancelReservation(Reservation reservation) {
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        
        // Mark car as available again
        carService.updateCarAvailability(reservation.getCar(), true);
        
        // Handle payment refund if necessary
        Payment payment = reservation.getPayment();
        if (payment != null) {
            payment.refundPayment();
        }
    }
    
    // Complete a reservation
    public void completeReservation(Reservation reservation) {
        reservation.setStatus(Reservation.ReservationStatus.COMPLETED);
        
        // Mark car as available again
        carService.updateCarAvailability(reservation.getCar(), true);
    }
    
    // Get a reservation by ID
    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }
    
    // Get all reservations
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations.values());
    }
    
    // Get reservations by user
    public List<Reservation> getReservationsByUser(User user) {
        return reservations.values().stream()
                          .filter(reservation -> reservation.getUser().equals(user))
                          .collect(Collectors.toList());
    }
    
    // Get active reservations
    public List<Reservation> getActiveReservations() {
        return reservations.values().stream()
                          .filter(reservation -> reservation.getStatus() == Reservation.ReservationStatus.CONFIRMED)
                          .collect(Collectors.toList());
    }
}

// PaymentService class to handle payment-related operations
public class PaymentService {
    private Map<String, Payment> payments;
    
    public PaymentService() {
        this.payments = new HashMap<>();
    }
    
    // Process a payment
    public Payment processPayment(double amount, String paymentMethod) {
        Payment payment = new Payment(amount, paymentMethod);
        payment.processPayment();
        payments.put(payment.getPaymentId(), payment);
        return payment;
    }
    
    // Refund a payment
    public boolean refundPayment(Payment payment) {
        return payment.refundPayment();
    }
    
    // Get a payment by ID
    public Payment getPayment(String paymentId) {
        return payments.get(paymentId);
    }
    
    // Get all payments
    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments.values());
    }
}
