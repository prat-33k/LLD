// Abstract base class for all cars
public abstract class Car {
    private String carId;
    private String make;
    private String model;
    private int year;
    private double rentalPricePerDay;
    private boolean isAvailable;
    
    public Car(String carId, String make, String model, int year, double rentalPricePerDay) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.rentalPricePerDay = rentalPricePerDay;
        this.isAvailable = true;
    }
    
    // Getters and setters
    public String getCarId() {
        return carId;
    }
    
    public String getMake() {
        return make;
    }
    
    public String getModel() {
        return model;
    }
    
    public int getYear() {
        return year;
    }
    
    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }
    
    public void setRentalPricePerDay(double rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    public abstract String getCarType();
    
    @Override
    public String toString() {
        return String.format("%s: %d %s %s - $%.2f per day", 
                            getCarType(), year, make, model, rentalPricePerDay);
    }
}

// Concrete class for Sedan type
public class Sedan extends Car {
    public Sedan(String carId, String make, String model, int year, double rentalPricePerDay) {
        super(carId, make, model, year, rentalPricePerDay);
    }
    
    @Override
    public String getCarType() {
        return "Sedan";
    }
}

// Concrete class for Hatchback type
public class Hatchback extends Car {
    public Hatchback(String carId, String make, String model, int year, double rentalPricePerDay) {
        super(carId, make, model, year, rentalPricePerDay);
    }
    
    @Override
    public String getCarType() {
        return "Hatchback";
    }
}

// Concrete class for SUV type
public class SUV extends Car {
    public SUV(String carId, String make, String model, int year, double rentalPricePerDay) {
        super(carId, make, model, year, rentalPricePerDay);
    }
    
    @Override
    public String getCarType() {
        return "SUV";
    }
}
