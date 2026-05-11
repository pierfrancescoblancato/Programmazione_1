public class Cargo extends Plane{

    private int maxCapacity;

    public Cargo(int id, String model, String producer,int maxCapacity) {
        super(id,model,producer);
        this.maxCapacity = maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public float getWeight() {
        return (float) (this.maxCapacity * 1.1);
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "maxCapacity=" + maxCapacity +
                '}';
    }
}
