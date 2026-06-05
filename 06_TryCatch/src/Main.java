public class Main {
    public static void main(String[] args) {
        try{
            int input = 0;
            double x = (double) 3 / input;

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            System.out.println("finally fine voglio stampare !!!");
        }
    }
}