package unsw.dungeon;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Class to manage a dungeon instance being run by the JavaFX frontend.
 */
public class DungeonController implements EntryObserver {

    // JavaFX Data
    @FXML
    private StackPane gamePane;
    @FXML
    private GridPane inventoryGrid;
    @FXML 
    private GridPane goalsGrid;
    @FXML 
    private Label goalString;
    @FXML
    private Rectangle invBack;
    @FXML
    private VBox appPane;
    private Label potionTimer;

    private DungeonScreen screen;

    private HashMap<Entity, ImageView> entityImages;
    private int tileSize;

    // Input data
    KeyCode lastInput;
    private HashMap<String, String> keybindings = new HashMap<String, String>();

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

        this.potionTimer = null;
        this.screen = d;

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

        // Now make this observe the dungeon entrances.
        this.dungeon.linkEntrances(this);

        // Initialise JavaFX-related variables.
        this.tileSize = loader.getTileSize();
        this.lastInput = null;
        refreshKeys();
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
                
                //System.out.println("DeltaTime = " + deltaTime);
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
            screen.openDeathScreen();
            running = false;
        }
    }

    public void stopGameLoop() {
        gameLoop.stop();
        wasPaused = true;
    }

    public void startGameLoop() {
        loader.refreshTextures();
        gameLoop.play();
        initialRender();
    }

    public double getPaneWidth() {
        return this.gamePane.getWidth();
    }

    public double getPaneHeight() {
        return this.gamePane.getHeight();
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
        
        for (ZLayer z : ZLayer.values()) {
            GridPane g = getRenderLayer(z.getZIndex());
            g.getChildren().clear();
        }
        
        renderBG();
        // Set the stackPane to be as big as the gridpanes it holds - this allows
        // all other SceneBuilder elements to be placed where they should go.
        gamePane.setPrefSize(tileSize * dungeon.getWidth(), tileSize * dungeon.getHeight());
        for (ZLayer z : ZLayer.values()) {
            //System.out.println("Rendering layer " + z.toString());
            renderEntityLayer(z);
        }

        renderTextOverlay();
    }

    public void updateRender() {
        for (ZLayer z : ZLayer.values()) {
            if (! z.isStatic()) {
                GridPane g = getRenderLayer(z.getZIndex());
                g.getChildren().clear();
                renderEntityLayer(z);
            }
        }
        renderUI();
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
            g.setAlignment(Pos.CENTER);

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

            //System.out.println("Created layer for " + z.toString());
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

    private void renderUI() {
        renderInventory();
        renderGoals();
        renderGoalString();
        renderTextOverlay();
    }


    private void renderPotionTimer() {
        // Node n = appPane.lookup("potion_timer");
        // if (dungeon.playerIsInvincible()) {
        //     String text = "Potion Duration Left: " + dungeon.getPlayerInvincLeft();
        //     if(n == null) {
        //         Label timer = new Label(text);
        //         timer.setId("potion_timer");
        //         appPane.getChildren().add(timer);
        //     } else {
        //         Label timer = (Label) n;
        //         timer.setText(text);
        //     }
        // } else {
        //     if(n != null) {
        //         appPane.getChildren().remove(n);
        //     }
        // }
        if (p.isInvincible()) {
            double timeLeft = p.getInvincTimeLeft();
            DecimalFormat df = new DecimalFormat("#.##");
            String text = "Potion Duration Left: " + df.format(timeLeft);
            if (potionTimer == null) {
                // Need to instantiate it.
                potionTimer = new Label(text);
                potionTimer.setTextFill(Color.WHITE);
                potionTimer.setStyle("-fx-font-weight: bold;");
                potionTimer.setPadding(new Insets(10));
                potionTimer.setPrefWidth(300);
                potionTimer.setTextAlignment(TextAlignment.CENTER);
                DropShadow d = new DropShadow();
            
                potionTimer.setEffect(d);
                potionTimer.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
                appPane.getChildren().add(potionTimer);
            } else {
                // Need to update it
                potionTimer.setText(text);
            }
        } else {
            if (potionTimer != null) {
                // Remove display.
                appPane.getChildren().remove(potionTimer);
                potionTimer = null;
            }
        }
    }

    private void renderTextOverlay() {
        // Render this to the same layer as the items.
        GridPane targetLayer = getRenderLayer(ZLayer.ITEM.getZIndex());
        for (DungeonEntry e : dungeon.getEntries()) {
            int x = e.getX();
            int y = e.getY();
            String text = e.getTitleUpper();
            Label title = new Label(text);

            title.setFont(new Font("Calibri", 8));
            title.setStyle("-fx-font-weight: bold;");
            title.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
            title.setTextFill(Color.WHITE);
            title.setAlignment(Pos.BOTTOM_CENTER);
            title.setMinWidth(Region.USE_PREF_SIZE);

            targetLayer.add(title, x, y);
            GridPane.setHalignment(title, HPos.CENTER);
        }

        renderPotionTimer();
    }


    private void renderInventory() {
        inventoryGrid.getChildren().clear();
        inventoryGrid.setMaxHeight(invBack.getHeight());
        int counter = 0;
        for (Item i: p.getInventory()) {
            ImageView tex = entityImages.get((Entity) i);

            if (i instanceof Weapon) {
                // If a weapon, draw the number of uses left.
                Weapon w = (Weapon) i;

                Label uses = new Label("" + w.getUsesLeft());
                uses.setTextFill(Color.WHITE);
                uses.setStyle("-fx-font-weight: bold");
                
                inventoryGrid.add(uses, counter, 2);
                GridPane.setHalignment(uses, HPos.CENTER);
            }

            //Add the texture
            inventoryGrid.add(tex, counter, 1);

            GridPane.setValignment(tex, VPos.CENTER);
            GridPane.setHalignment(tex, HPos.CENTER);
            counter++;
        }
        inventoryGrid.setMaxHeight(invBack.getHeight());
    }

    private void renderGoals() {
        goalsGrid.getChildren().clear();
        int placementCounter = 0;

        if (dungeon.isComplete()) {
            screen.openCompletionScreen();
        }

        HashMap<GoalObserver, Integer> goalInfo = dungeon.getGoalInfo();
        if (goalInfo == null) return;

        for (GoalObserver g: goalInfo.keySet()) {
            ImageView goalImage;
            if (g instanceof TreasureGoal) {
                goalImage = new ImageView(loader.getTreasureImage());
            } else if (g instanceof ExitGoal) {
                goalImage = new ImageView(loader.getExitImage());
            } else if (g instanceof EnemyGoal) {
                goalImage = new ImageView(loader.getEnemyImage());
            } else {
                goalImage = new ImageView(loader.getSwitchImage());
            }

            if (g.isVoid()) {
                ColorAdjust dimmer = new ColorAdjust();
                dimmer.setSaturation(-0.2);
                dimmer.setBrightness(0.3);
                goalImage.setEffect(dimmer);
            }

            Label completionLabel;
            if (g instanceof ExitGoal) {
                completionLabel = new Label("Goal incomplete!");
            } else {
                completionLabel = new Label("" + (goalInfo.get(g) - g.getGoalEntitySize() + "/" + goalInfo.get(g)));
            }
            completionLabel.setTextFill(Color.WHITE);
            if (g.isComplete()) {
                completionLabel.setText("Goal Completed!");
            }

            if (g.isVoid()) {
                completionLabel.setText("Goal voided");
                completionLabel.setTextFill(Color.LIGHTGREY);
            }
            completionLabel.setStyle("-fx-font-weight: bold");

            goalsGrid.add(completionLabel, placementCounter, 1);
            GridPane.setHalignment(completionLabel, HPos.CENTER);

            goalsGrid.add(goalImage, placementCounter, 2);

            GridPane.setValignment(goalImage, VPos.CENTER);
            GridPane.setHalignment(goalImage, HPos.CENTER);
            placementCounter++;            

        }
    }

    private void renderGoalString() {
        String goalString = dungeon.getGoalString();
        if (goalString == null) {
            this.goalString.setText("No goals for this level");
        } else {
            this.goalString.setText(goalString + "!");
        }
        this.goalString.setStyle("-fx-font-weight: bold");
    }

    /**
     * Render the background of the dungeon.
     */
    private void renderBG() {
        Image ground = loader.getGroundImage();

        GridPane targetLayer = getRenderLayer(ZLayer.BACKGROUND.getZIndex());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                targetLayer.add(new ImageView(ground), x, y);
            }
        }

        appPane.setBackground(new Background(new BackgroundFill(new Color(0.384, 0.255, 0.306, 1), null, null)));
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
            // tmp
        } else {
            if (lastInput.toString().equals(keybindings.get("UP"))) {
                p.moveUp();
            } else if (lastInput.toString().equals(keybindings.get("DOWN"))) {
                p.moveDown();
            } else if (lastInput.toString().equals(keybindings.get("LEFT"))) {
                p.moveLeft();
            } else if (lastInput.toString().equals(keybindings.get("RIGHT"))) {
                p.moveRight();
            } else if (lastInput.toString().equals(keybindings.get("PAUSE"))) {
                screen.openPauseScreen();
            } else if (lastInput.toString().equals(keybindings.get("ATTACK"))) {
                p.attack();
            }
            
            lastInput = null;
        }
    }

    public void refreshKeys() {
        String keyMap = "";
        try {
            File keys = new File("keybindings.txt");
            Scanner fileReader = new Scanner(keys);
            while (fileReader.hasNextLine()) {
                keyMap += fileReader.nextLine();
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            keybindings.put("UP", "W");
            keybindings.put("DOWN", "S");
            keybindings.put("LEFT", "A");
            keybindings.put("RIGHT", "D");
            keybindings.put("ATTACK", "SPACE");
            keybindings.put("PAUSE", "ESC");
            return;
        }
        keyMap = keyMap.substring(1, keyMap.length() - 1);
        String[] pairs = keyMap.split(",");
        for (String pair: pairs) {
            pair = pair.strip();
            keybindings.put(pair.split("=")[0], pair.split("=")[1]);
        }

    }
    /**
     * Function is called whenever a dungeon entry tile is triggered.
     * @param sub
     */
    @Override
    public void updateEntry(EntrySubject sub) {
        String fileName = sub.getTargetFile();
        screen.openNewDungeon(fileName);
    }

}