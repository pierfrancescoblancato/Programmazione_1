public abstract class Plane {
    private int id;
    private String model;
    private String producer;

    public Plane(int id, String model, String producer) {
        this.id = id;
        this.model = model;
        this.producer = producer;
    }

    public abstract int getMaxCapacity();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
}
