public class SimpleThread extends Thread {

    @Override
    public void run(){
        System.out.println("sono nel thread separato");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("sono passati 1000 millisecondi");

    }
}