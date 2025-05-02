import java.time.LocalDateTime;
import java.util.UUID;

// Class to represent a payment in the system
public class Payment {
    private String paymentId;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    
    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REFUNDED
    }
    
    public Payment(double amount, String paymentMethod) {
        this.paymentId = UUID.randomUUID().toString().substring(0, 8);
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING;
    }
    
    // Process the payment
    public boolean processPayment() {
        // In a real application, this would integrate with a payment gateway
        // For demonstration, we'll just simulate a successful payment
        this.status = PaymentStatus.COMPLETED;
        this.paymentDate = LocalDateTime.now();
        System.out.println("Payment processed successfully: $" + amount);
        return true;
    }
    
    // Refund the payment
    public boolean refundPayment() {
        if (this.status == PaymentStatus.COMPLETED) {
            this.status = PaymentStatus.REFUNDED;
            System.out.println("Payment refunded: $" + amount);
            return true;
        }
        return false;
    }
    
    // Getters and setters
    public String getPaymentId() {
        return paymentId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
    
    public PaymentStatus getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
        return String.format("Payment #%s - $%.2f via %s - Status: %s", 
                            paymentId, amount, paymentMethod, status);
    }
}
