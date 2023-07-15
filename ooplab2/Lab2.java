import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lab2 implements Runnable {
    private static int nextId = 1;
    private static int pens;
    private static int bottles;
    private static int fountainpens;
    private static int fountaininks;
    private static int books;
    private static Lock penLock;
    private static Lock bottleLock;
    private static Lock fountainpenLock;
    private static Lock fountaininkLock;
    

    private int id;

    public Lab2() {
        id = nextId++;
    }

    @Override
    public void run(){
        while(true){
            try{
                penLock.lock();
                if(pens>0){
                    pens--;
                    System.out.println("Scribe "+ id +" takes a pen");
                    penLock.unlock();
                }
                else{
                    penLock.unlock();
                    Thread.sleep(1000);
                    continue;
                }

                bottleLock.lock();
                if(bottles>0){
                    bottles--;
                    System.out.println("Scribe "+ id +" takes a bottle");
                    bottleLock.unlock();
                }
                else{
                    bottleLock.unlock();
                    penLock.lock();
                    pens++;
                    System.out.println("Scribe "+ id +" puts the pen back");
                    penLock.unlock();
                    Thread.sleep(1000);
                    continue;
                }

                

                

                System.out.println("Scribe "+id+" writes a record");

                penLock.lock();
                bottleLock.lock();
                
                pens++;
                bottles++;
                
                System.out.println("Scribe "+id+" puts the pen back");
                System.out.println("Scribe "+id+" puts the bottle back");
                

                penLock.unlock();
                bottleLock.unlock();          

                Thread.sleep((int)(Math.random() * 10000));

                fountainpenLock.lock();
                if(fountainpens>0){
                    fountainpens--;
                    System.out.println("Scribe "+ id +" takes a fountain pen");
                    fountainpenLock.unlock();
                }
                else{
                    fountainpenLock.unlock();
                    Thread.sleep(1000);
                    continue;
                }

                fountaininkLock.lock();
                if(fountaininks>0){
                    fountaininks--;
                    System.out.println("Scribe "+ id +" takes a fountain ink");
                    fountaininkLock.unlock();
                }
                else{
                    fountaininkLock.unlock();
                    fountainpenLock.lock();
                    fountainpens++;
                    System.out.println("Scribe "+ id +" puts the fountain pen back");
                    penLocfountainpenLock.unlock();
                    Thread.sleep(1000);
                    continue;
                }

                System.out.println("Scribe "+id+" writes a record");

                fountainpenLock.lock();
                fountaininkLock.lock();
                
                fountainpens++;
                fountaininks++;
                
                System.out.println("Scribe "+id+" puts the fountain pen back");
                System.out.println("Scribe "+id+" puts the fountain ink back");
                

                fountainpenLock.unlock();
                fountaininkLock.unlock();          

                Thread.sleep((int)(Math.random() * 10000));

            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        if (args.length < 4){
            System.out.println("Usage: java Lab2 <number_of_scribes> <number_of_pens> <number_of_bottles> <number_of_books>");
            System.exit(1);
        }
        
        int numOfScribes = Integer.parseInt(args[0]);
        pens = Integer.parseInt(args[1]);
        bottles = Integer.parseInt(args[2]);
        fountainpens = Integer.parseInt(args[3]);
        fountaininks = Integer.parseInt(args[3]);

        penLock = new ReentrantLock();
        bottleLock = new ReentrantLock();
        fountainpenLock = new ReentrantLock();
        fountaininkLock = new ReentrantLock();

        for(int i = 0; i < numOfScribes; i++){
            Thread thread = new Thread(new Lab2());
            thread.start();
        }
    }
}
