package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author עמרי באום
 */
public class Terrain {
    private final float groundHeightAtX0;
    private final float TWO_THIRDS = 0.666F;
    private final String BLOCK_TAG = "ground";
    private final int NOISE_FACTOR = Block.SIZE * 7;
    private static final Color BASE_GROUND_COLOR =
            new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final int ONE = 1;
    private static final int NULL = 0;
    private final Vector2 WindowDimensions;
    private NoiseGenerator noiseGenerator;

    /**
     *
     * @param windowDimensions game dimensions
     * @param seed
     */
    public Terrain(Vector2 windowDimensions, int seed){
        this.groundHeightAtX0 = TWO_THIRDS * windowDimensions.y();
        this.WindowDimensions = windowDimensions;
        this.noiseGenerator =
                new NoiseGenerator(seed,(int)this.groundHeightAtX0);
    }

    /**
     *
     * @param x x coordinate range to get height at
     * @return the height at given coordinate
     */
    public float groundHeightAt(float x) {
        if(this.WindowDimensions.x() < x){
            return NULL;
        }
        double noise = noiseGenerator.noise(x,NOISE_FACTOR);
        return (float) (this.groundHeightAtX0 + noise);
    }

    /**
     *creates blocks on the range
     * @param minX left bound
     * @param maxX right bound
     * @return
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> res = new ArrayList<Block>();
        Vector2 block_dimensions =
                new Vector2(Block.SIZE, Block.SIZE);
        //round the range to at least the range but dividable number
        int new_min_range = (minX / Block.SIZE) * Block.SIZE;
        int new_max_range = (int) (Math.ceil(maxX / Block.SIZE) + ONE) * Block.SIZE;
        //number of total bricks in a row
        int NumBricksInRow = (new_max_range - new_min_range) / Block.SIZE;
        //create bricks and put in the list
        for (int i = 0; i < NumBricksInRow; i++) {
            int XcoordinateOfCol = new_min_range + (i * Block.SIZE);
            int YcoordinateOfCol =
                    (int) Math.floor(groundHeightAt(XcoordinateOfCol) / Block.SIZE) * Block.SIZE;
            for (int j = TERRAIN_DEPTH; j > 0; j--) {
                int Y_Block = YcoordinateOfCol + (Block.SIZE * j);
                Vector2 Block_coordinates =
                        new Vector2(XcoordinateOfCol, Y_Block);
                RectangleRenderable Block_image =
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                //create the Block
                Block block =
                        new Block(Block_coordinates, block_dimensions, Block_image);
                block.setTag(BLOCK_TAG);
                res.add(block);
            }
        }
        return res;
    }
}
//List<Block> res = new ArrayList<Block>();
//Vector2 block_dimensions =
//        new Vector2(Block.SIZE, Block.SIZE);
////round the range to at least the range but dividable number
//int new_min_range = (minX / Block.SIZE) * Block.SIZE;
//int new_max_range = (int) (Math.ceil(maxX / Block.SIZE) + 1) * Block.SIZE;
////number of total bricks in a row
//int NumBricksInRow = (new_max_range - new_min_range) / Block.SIZE;
////create bricks and put in the list
//        for (int i = 0; i < NumBricksInRow; i++) {
//int XcoordinateOfCol = new_min_range + (i * Block.SIZE);
////round the noise product to round number in Block.SIZE
//int YcoordinateOfCol =
//        (int) Math.floor(groundHeightAt(XcoordinateOfCol) / Block.SIZE) * Block.SIZE;
//            for (int j = 0; j < TERRAIN_DEPTH; j++) {
//int Y_Block = YcoordinateOfCol + (Block.SIZE * j);
//Vector2 Block_coordinates =
//        new Vector2(XcoordinateOfCol, Y_Block);
//RectangleRenderable Block_image =
//        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
////create the Block
//Block block =
//        new Block(Block_coordinates, block_dimensions, Block_image);
//                block.setTag(BLOCK_TAG);
//                res.add(block);
//            }
//                    }
//                    return res;
//    }