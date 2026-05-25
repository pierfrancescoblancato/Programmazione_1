public class Formula1 extends Car implements Runnable {

    private String namePilot;
    private int laps;


    public Formula1(String namePilot, String model, int laps) {
        super(model);
        this.namePilot = namePilot;
        this.laps = laps;
    }

    public String getNamePilot() {
        return namePilot;
    }

    public void setNamePilot(String namePilot) {
        this.namePilot = namePilot;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    @Override
    public void run() {
        for(int i = 0; i <= laps; i++){
            try {
                System.out.println(namePilot + " completed lap " + (i + 1) + "/" + laps);
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println(namePilot + " finished all " + laps + " laps!");
    }
}