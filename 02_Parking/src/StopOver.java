import java.text.SimpleDateFormat;
import java.util.Date;

public class StopOver {

    private Vehicle vehicle;
    private long timeIn;
    private long timeOut;
    private int position;
    private int priceForMillis;


    public StopOver(Vehicle vehicle, int position, int priceForMillis) {
        this.vehicle = vehicle;
        this.position = position;
        this.timeIn = System.currentTimeMillis();
        this.timeOut = 0;
        this.priceForMillis = priceForMillis;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public long getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(long timeIn) {
        this.timeIn = timeIn;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPriceForMillis() {
        return priceForMillis;
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
        String type = (vehicle instanceof Van) ? "VAN" : "CAR";

        String inStr = sdf.format(new Date(timeIn));

        String outStr;
        if (timeOut == 0) {
            outStr = "Stopover now";
        } else {
            outStr = sdf.format(new Date(timeOut));
        }

        return "Stopover{" +
                "plate: " + vehicle.getPlate() + ";" +
                "type: " + type.toLowerCase() + ";" +
                ", in: " + inStr + ";" +
                ", out: " + outStr + ";" +
                ", position: " + position + ";" +
                ", totalPrice: " + getTotalPrice() + ";" +
                ", price: " + getPriceForMillis() +
                '}';
    }
}
