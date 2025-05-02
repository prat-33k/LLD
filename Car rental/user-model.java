// Class to represent a user in the system
public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private List<Reservation> reservationHistory;
    
    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.reservationHistory = new ArrayList<>();
    }
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<Reservation> getReservationHistory() {
        return Collections.unmodifiableList(reservationHistory);
    }
    
    public void addReservation(Reservation reservation) {
        this.reservationHistory.add(reservation);
    }
    
    @Override
    public String toString() {
        return String.format("User: %s (%s)", name, email);
    }
}
