package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.function.Function;

/***
 * @omri baum and ayaelet hashahar valevy
 * creates the trees and fruits on given range
 */
public class Flora {
    private static final int PROBABILITY = 10;
    private static final int TERRAIN_DEPTH = 20;
    private static final double ONE_RANGE = 1;
    private static final int COIN_FLIP = 9;

    /**
     *
     * @param minX left edge of the range
     * @param maxX right end of range
     * @param func calaulates the ground height at given x
     * @return hasjmap of <location, Tree parts list>
     */
    public static HashMap<Vector2, Tree> createInRange(
            int minX, int maxX, Function<Integer, Float> func) {
        HashMap<Vector2, Tree> res =
                new HashMap<>();
        Random rand = new Random();

        int new_min_range = (minX / Block.SIZE) * Block.SIZE;
        int new_max_range = (int) (Math.ceil(maxX / Block.SIZE) + ONE_RANGE) * Block.SIZE;

        //number of total columns
        int NumBricksInRow = (new_max_range - new_min_range) / Block.SIZE;
        for (int i = 0; i < NumBricksInRow; i++) {
            int XcoordinateOfCol = new_min_range + (i * Block.SIZE);
            if (rand.nextInt(PROBABILITY) == COIN_FLIP) {
                //unsolved!
                int rawy =
                        (int) Math.floor(func.apply(XcoordinateOfCol) / Block.SIZE) * Block.SIZE;
                Vector2 location = new Vector2(XcoordinateOfCol,
                        rawy);
                Tree tree =
                        new Tree(location, Vector2.ZERO, null);
                res.put(location, tree);
                }
            }
            return res;
    }
}

