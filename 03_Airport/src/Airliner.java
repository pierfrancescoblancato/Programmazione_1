public class Airliner extends Plane{
    private int maxSeats;

    public Airliner(int id, String model, String producer,int maxSeats) {
        super(id,model,producer);
        this.maxSeats = maxSeats;
    }

    public void setMaxCapacity(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Override
    public float getWeight() {
        return this.maxSeats * 70;
    }

    @Override
    public String toString() {
        return super.toString() + " [Posti: " + maxSeats + "]";
    }
}
