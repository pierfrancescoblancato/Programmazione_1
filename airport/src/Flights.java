import java.time.LocalDateTime;

public class Flights {
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Plane plane;

    public Flights(LocalDateTime departureTime, LocalDateTime arrivalTime, Plane plane) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.plane = plane;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }
}
