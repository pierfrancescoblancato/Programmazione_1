public class Airliner extends Plane{
    private int maxSeats;

    public Airliner(int id, String model, String producer,int maxSeats) {
        super(id,model,producer);
        this.maxSeats = maxSeats;
    }

    public void setMaxCapacity(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Override
    public int getMaxCapacity() {
        return this.maxSeats;
    }
}
