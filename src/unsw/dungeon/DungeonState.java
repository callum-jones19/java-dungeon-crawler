package unsw.dungeon;

public class DungeonState implements GameState{

    private Dungeon dungeon;
    
    private boolean running;

    public DungeonState(Dungeon d) {
        this.dungeon = d;
        this.running = true;
    }

    // public void run() {
    //     long prevTime = System.nanoTime();
    //     while (running) {
    //         long newTime = System.nanoTime();
    //         double deltaTime = (double)(newTime - prevTime)/1000000000;
    //         prevTime = newTime;

    //         dungeon.executeUpdates(deltaTime);
    //     }
    // }

    

    // NOTE
    // This is purely here for testing purposes. Will not remain after milestone
    // 2
    public void run(double timeToRun) {
        running = true;
        long prevTime = System.nanoTime();
        while (running) {
            long newTime = System.nanoTime();
            double deltaTime = (double)(newTime - prevTime)/1000000000;
            prevTime = newTime;
            
            if (timeToRun <= 0) {
                running = false;
            } else {
                timeToRun -= deltaTime;
            }

            dungeon.executeUpdates(deltaTime);

            if (dungeon.getPlayer() == null) {
                running = false;
                System.out.println("Player dead, game ended.");
            }
        }
    }

}