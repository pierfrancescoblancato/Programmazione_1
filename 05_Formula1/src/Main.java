
public class Main {
    public static void main(String[] args) {
        int laps = 10;
        Formula1 t1 = new Formula1("Leclerc","Ferrari1",laps);
        Formula1 t2 = new Formula1("Norris","McLaren",laps);
        Formula1 t3 = new Formula1("Verstappen","Redbull",laps);
        Formula1 t4 = new Formula1("Hamilton","Ferrari2",laps);

        Thread th1 = new Thread(t1);
        Thread th2 = new Thread(t1);
        Thread th3 = new Thread(t1);
        Thread th4 = new Thread(t1);

        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }
}