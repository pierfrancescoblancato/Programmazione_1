import java.text.SimpleDateFormat;
import java.util.Date;

public class Stopover {

    private Vehicle vehicle;
    private long timeIn;
    private long timeOut;
    private int position;
    private int priceForMillis;


    public Stopover(Vehicle vehicle, int position, int priceForMillis) {
        this.vehicle = vehicle;
        this.position = position;
        this.timeIn = System.currentTimeMillis();
        this.timeOut = 0;
        this.priceForMillis = priceForMillis;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getTimeIn() {
        return timeIn;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public int getPosition() {
        return position;
    }

    public double getpriceForMillis() {
        return priceForMillis;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public void setPriceForMillis(int priceForMillis) {
        this.priceForMillis = priceForMillis;
    }


    public double getTotalPrice(){
        if(this.timeIn == 0 || this.timeOut == 0) return 0.0;
        return (this.timeOut - this.timeIn) * this.priceForMillis;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String inStr = sdf.format(new Date(timeIn));

        String outStr;
        if (timeOut == 0) {
            outStr = "Stopover now";
        } else {
            outStr = sdf.format(new Date(timeOut));
        }

        return "Stopover{" +
                "plate: " + vehicle.getPlate() +
                ", in: " + inStr +
                ", out: " + outStr +
                ", position: " + position +
                ", totalPrice: " + getTotalPrice() +
                ", price: " + getpriceForMillis() +
                '}';
    }
}
