
public class Main {
    public static void main(String[] args) {
        System.out.println("ciao mondo");
        SimpleThread t1 = new SimpleThread();
        t1.start();
        System.out.println("bye bye");
        }
    }
