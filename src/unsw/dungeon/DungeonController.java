package unsw.dungeon;

import java.io.File;
import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

/**
 * Class to manage a dungeon instance being run by the JavaFX frontend.
 */
public class DungeonController {

    // JavaFX Data
    @FXML
    private StackPane gamePane;

    private DungeonScreen screen;

    private HashMap<Entity, ImageView> entityImages;
    private int tileSize;

    // Input data
    KeyCode lastInput;

    // Dungeon data
    private DungeonControllerLoader loader;

    private Dungeon dungeon;
    private Player p;

    //Gameloop
    private Timeline gameLoop;
    private boolean running;
    private EventHandler<ActionEvent> ev;
    private boolean wasPaused;

    public DungeonController(DungeonControllerLoader l, DungeonScreen d) {

        screen = d;

        // Create the game loop.
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        running = true;
        ev = createGameLoop();
        KeyFrame kf = new KeyFrame(Duration.seconds(0.017), ev);
        gameLoop.getKeyFrames().addAll(kf);

        // Initialise the dungeon 
        this.loader = l;

        this.dungeon = loader.load();
        this.entityImages = loader.loadDungeonImages();
        this.p = dungeon.getPlayer();

        // Initialise JavaFX-related variables.
        this.tileSize = loader.getTileSize();
        this.lastInput = null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    //                      Initialise Functions
    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Initialise all visual elements. Once this is done, begin the game loop.
     */
    @FXML
    public void initialize() {
        createGridPaneLayers();
        initialRender();

        gameLoop.play();
    }

    private EventHandler<ActionEvent> createGameLoop() {
        EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() {
            long prevTime = System.nanoTime();
            
            @Override
            public void handle(ActionEvent event) {
                // Run until the bool flag is set to false.
                if (!running) {
                    gameLoop.stop();
                }
                if (wasPaused) {
                    // If coming back from a pause state, we need to update with
                    // a new delta time, otherwise it will think the frame took
                    // as long as we paused for.
                    prevTime = System.nanoTime();
                    wasPaused = false;
                }
                long newTime = System.nanoTime();
                double deltaTime = (double)(newTime - prevTime)/1000000000;
                prevTime = newTime;
                
                System.out.println("DeltaTime = " + deltaTime);
                update(deltaTime);
            }
        };

        return ev;
    }

    public void update(double deltaTime) {
        dungeon.executeUpdates(deltaTime);
        processInput();
        updateRender();
        if(dungeon.getPlayer() == null) {
            running = false;
        }
    }

    public void stopGameLoop() {
        gameLoop.stop();
        wasPaused = true;
    }

    public void startGameLoop() {
        gameLoop.play();
    }

    ////////////////////////////////////////////////////////////////////////////
    //
    //                      Rendering Functions
    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * The initial render of the state. This initial render will
     * attach this JavaFX StackPane to a parent which it will draw onto.
     * @param parent
     */
    public void initialRender() {
        renderBG();
        for (ZLayer z : ZLayer.values()) {
            //System.out.println("Rendering layer " + z.toString());
            renderEntityLayer(z);
        }
    }

    public void updateRender() {
        for (ZLayer z : ZLayer.values()) {
            if (! z.isStatic()) {
                GridPane g = getRenderLayer(z.getZIndex());
                g.getChildren().clear();
                renderEntityLayer(z);
            }
        }
    }

    private GridPane getRenderLayer(int zIndex) {
        Node n = gamePane.getChildren().get(zIndex);
        GridPane g = null;
        if (n instanceof GridPane) {
            g = (GridPane) n;
        }
        return g;
    }

    private void createGridPaneLayers() {
        for (ZLayer z : ZLayer.values()) {
            GridPane g = new GridPane();

            // Now force the grid constraints.
            int numRows = dungeon.getWidth();
            int numCols = dungeon.getHeight();
            for (int i = 0; i < numRows; i++) {
                //
                ColumnConstraints colConst = new ColumnConstraints(tileSize);
                g.getColumnConstraints().add(colConst);
            }
            for (int i = 0; i < numCols; i++) {
                RowConstraints rowConst = new RowConstraints(tileSize);
                g.getRowConstraints().add(rowConst);
            }

            System.out.println("Created layer for " + z.toString());
            gamePane.getChildren().add(z.getZIndex(), g);
        }
    }

    /**
     * Render a specific layer of dungeon entities.
     * @param layer
     */
    private void renderEntityLayer(ZLayer layer) {
        for (Entity e : dungeon.getEntities(layer)) {
                GridPane targetLayer = getRenderLayer(layer.getZIndex());
                ImageView entTexture = entityImages.get(e);
                targetLayer.getChildren().add(entTexture);
                GridPane.setColumnIndex(entTexture, e.getX());
                GridPane.setRowIndex(entTexture, e.getY());
                //targetLayer.add(entTexture, e.getX(), e.getY(), 1, 1);
                // System.out.println("Rendered entity " + e.toString() + " at " + e.getX() + "," + e.getY() + ". Render coords are: " + GridPane.getColumnIndex(entTexture) + "," + GridPane.getRowIndex(entTexture));
                //System.out.println("Layer " + layer.toString() + " has gridPane with dimensions " + targetLayer.getColumnCount() + "," + targetLayer.getRowCount());
            }
    }

    /**
     * Render the background of the dungeon.
     */
    private void renderBG() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        GridPane targetLayer = getRenderLayer(ZLayer.BACKGROUND.getZIndex());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                targetLayer.add(new ImageView(ground), x, y);
            }
        }
    }

    
    ////////////////////////////////////////////////////////////////////////////
    //
    //                          FXML Functions
    //
    ////////////////////////////////////////////////////////////////////////////    
    
    

    /**
     * This event will feed the last key into a buffer so the game loop
     * doesn't run concurrently with the inputs.
     * @param event
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        KeyCode kc = event.getCode();
        lastInput = kc;
    }

    
    private void processInput() {
        if (lastInput == null) {
            // FIXME doesnt work the other way around??? No idea what i was doing
            // but its 1:30AM so fix it later.
        } else {
            switch(lastInput) {
                case UP:
                    p.moveUp();
                    break;
                case DOWN:
                    p.moveDown();
                    break;
                case LEFT:
                    p.moveLeft();
                    break;
                case RIGHT:
                    p.moveRight();
                    break;
                case SPACE:
                    p.attack();
                    break;
                case ESCAPE:
                    screen.openPauseScreen();
                    break;
                default:
                    break;
            }
            lastInput = null;
        }
    }

}