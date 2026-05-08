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
    public int getMaxCapacity() {

        return this.maxCapacity;
    }
}
