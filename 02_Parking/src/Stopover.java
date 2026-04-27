import java.time.Duration;
import java.time.LocalDateTime;

public class Stopover {

    private Car car;
    private LocalDateTime timestampIn;
    private LocalDateTime timestampOut;
    private int position;
    private double price;

    public Stopover(Car car, int position) {
        this.car = car;
        this.position = position;
        this.timestampIn = LocalDateTime.now();
        this.timestampOut = null;
        this.price = 0.2;
    }

    public Car getCar() {
        return car;
    }

    public LocalDateTime getTimestampIn() {
        return timestampIn;
    }

    public LocalDateTime getTimestampOut() {
        return timestampOut;
    }

    public int getPosition() {
        return position;
    }

    public double getPrice() {
        return price;
    }

    public void setTimestampOut(LocalDateTime timestampOut) {
        this.timestampOut = timestampOut;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice(){
        if(this.timestampIn == null || this.timestampOut == null) return 0.0;
        Duration duration = Duration.between(this.timestampIn, this.timestampOut);
        double totalMs = duration.toNanos() / 1_000_000.0;
        return totalMs * this.price;
    }

    @Override
    public String toString() {
        return "Stopover{" +
                "car=" + car.getPlate() +
                ", in=" + timestampIn +
                ", out=" + timestampOut +
                ", position=" + position +
                ", price=" + getTotalPrice() +
                '}';
    }
}
