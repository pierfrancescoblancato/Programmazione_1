public class Cargo extends Plane{

    private float capacity;

    public Cargo(int id, String model, String producer,float capacity) {
        super(id,model,producer);
        this.capacity = capacity;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    @Override
    public float getWeight() {
        return this.capacity * 1000.0f;
    }

    @Override
    public String toString() {
        return super.toString() + " [Capacity: " + capacity + " tons]";
    }
}
