package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;

import java.util.*;

/**
 * game manager class
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLELENGTH = 30;
    /**
     * randimizer
     */
    public static final Random rand = new Random();
    private static final int SEED = PepseGameManager.rand.nextInt(50);
    private static final int START = 0;
    private static final int WINDOW_FACTOR = 2;


     /**
     *
     * @param windowTitle window name
     * @param windowDimensions window dimensions
     */
    public PepseGameManager(){
        super();
    }

    /**
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame
            (ImageReader imageReader,
             SoundReader soundReader,
             UserInputListener inputListener,
             WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        Terrain terrain = new Terrain(windowDimensions, SEED);
        CreateSky(windowDimensions);
        CreateBlocks(windowDimensions, terrain);
        CreateNight(windowDimensions);
        CreateSun(windowDimensions);
        CreateTrees(windowDimensions, terrain);
        //check max of two blocks
        float y_rate = Math.max(terrain.groundHeightAt(windowDimensions.x() / WINDOW_FACTOR),
                terrain.groundHeightAt(windowDimensions.x() + Block.SIZE/ WINDOW_FACTOR));
        float x_rate = windowDimensions.x() / WINDOW_FACTOR;
        Create_Avatar(new Vector2(x_rate,y_rate), inputListener, imageReader);
        CreateEnergyBar();
    }

    /**
     * creates the sky
     */
    private void CreateSky(Vector2 windowDimensions){
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    /**
     * creates the blocks
     */
    private void CreateBlocks(Vector2 windowDimensions, Terrain terrain){
        List<Block> lst = terrain.createInRange(START, (int)windowDimensions.x());
        for (Block block : lst){
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    /**
     * creates night malben
     */
    private void CreateNight(Vector2 windowDimensions){
        GameObject night = Night.create(windowDimensions, CYCLELENGTH);
        gameObjects().addGameObject(night);
    }

    /**
     * creates sun gameobject
     */
    private void CreateSun(Vector2 windowDimensions){
        GameObject sun = Sun.create(windowDimensions, CYCLELENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject sun_halo = SunHalo.create(sun);
        gameObjects().addGameObject(sun_halo, Layer.BACKGROUND);
    }

    /**
     * creates avatar
     */
    private Avatar Create_Avatar(Vector2 pos,
            UserInputListener inputListenerser,
                               ImageReader imagereader){
        Avatar avatar = Avatar.getInstance(pos,inputListenerser,imagereader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
        return avatar;
    }

    /**
     * cerates the energybar
     * @param avatar to get energy from
     */
    private void CreateEnergyBar(){
        EnergyBar bar =
                new EnergyBar(Vector2.ZERO, Vector2.ONES.mult(Block.SIZE));
        gameObjects().addGameObject(bar);
    }

    /**
     * adds trees and fruits
     * @param windowDimensions to get the range
     */

    private void CreateTrees(Vector2 windowDimensions, Terrain terrain) {
        HashSet<Tree> trees =
                Flora.createInRange(START, (int) windowDimensions.x(), terrain::groundHeightAt);
        for (Tree tree : trees) {
            for (Map.Entry<GameObject,Integer> entry : tree.getSet().entrySet())  {
                gameObjects().addGameObject(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * standart main function
     * @param args
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
