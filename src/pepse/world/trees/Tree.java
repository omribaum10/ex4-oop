package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Tree extends GameObject implements AvatarObserver{
    private static final Color TREECOLOR = new Color(100, 50, 20);
    private static final Color LEAF_COLOR = new Color(50,200,30);
    private static final Color FRUIT_COLOR = new Color(80,20,70);
    private static final int PROBABILITYHIGH = 8;
    private static final int PROBABILITYLOW = 5;
    private static final int HALFOFLEAFSQURE = 2;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int FULLLEAFSQUARESIZE = 4;
    private ArrayList<GameObject> lst;
    private Vector2 topleft;
    private int StemHeight;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Tree(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.lst = new ArrayList<>();
        topleft = topLeftCorner;
        RandomHeightStem();
        Randomleaves();
    }

    /**
     *
     */
    private void RandomHeightStem(){
        Random rand = new Random();
        int height = rand.nextInt(PROBABILITYHIGH - PROBABILITYLOW + ONE) + PROBABILITYLOW;
        this.StemHeight = height;
        Vector2 stemsize =
                new Vector2(Block.SIZE, Block.SIZE);
        RectangleRenderable stem_image =
                new RectangleRenderable(ColorSupplier.approximateColor(TREECOLOR));
        for (int i = 0; i < height; i++) {
            Vector2 location =
                    new Vector2(topleft.x(), topleft.y() - (i * Block.SIZE));
            lst.add(new Block(location,stemsize,stem_image));
        }

    }

    /**
     *
     */
    private void Randomleaves(){
        //leaf size equals to block size!
        Random rand = new Random();
        RectangleRenderable leaf_image =
                new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR));
        OvalRenderable fruit_image =
                new OvalRenderable(ColorSupplier.approximateColor(FRUIT_COLOR));
        Vector2 treetop = new Vector2(topleft.x(), topleft.y() - ((StemHeight) * Block.SIZE));
        float XmincoForSqure = treetop.x() - (Block.SIZE * HALFOFLEAFSQURE);
        float XmaxcoForSqure = treetop.x() + (Block.SIZE * HALFOFLEAFSQURE);
        float YTopcoForSqure = treetop.y() - (Block.SIZE * HALFOFLEAFSQURE);
        float YBottomcoForSqure = treetop.y() + (Block.SIZE * HALFOFLEAFSQURE);
        int new_min_range = (int) ((XmincoForSqure / Block.SIZE) * Block.SIZE);
        int new_max_range = (int) (Math.ceil(XmaxcoForSqure / Block.SIZE) + ONE) * Block.SIZE;
        int new_top_range = (int) ((YTopcoForSqure / Block.SIZE) * Block.SIZE);
        int new_bootom_range = (int) (Math.ceil((YBottomcoForSqure / Block.SIZE) + ONE) * Block.SIZE);
        for (int row = 0; row < FULLLEAFSQUARESIZE; row++) {
            for (int col = 1; col <= FULLLEAFSQUARESIZE; col++) {
                int random = rand.nextInt(FULLLEAFSQUARESIZE);
                if(random >= HALFOFLEAFSQURE){
                    Fruit fruit = new Fruit
                            (new Vector2(new_min_range + (col * Block.SIZE),
                                    new_bootom_range - (row * Block.SIZE)),
                                    new Vector2(Block.SIZE / HALFOFLEAFSQURE,
                                            Block.SIZE/ HALFOFLEAFSQURE),
                                    fruit_image);
                    lst.add(fruit);
                }
                if(random <= HALFOFLEAFSQURE){
                    Leaf leaf = new Leaf(
                        new Vector2(new_min_range + (col * Block.SIZE),
                                new_bootom_range - (row * Block.SIZE)),
                            new Vector2(Block.SIZE / FULLLEAFSQUARESIZE,
                                    Block.SIZE/ FULLLEAFSQUARESIZE),
                            leaf_image);
                    lst.add(leaf);
                }

            }
        }
    }

    public ArrayList<GameObject> GetList(){
        return lst;
    }

    public void updateJump(){
        for (AvatarObserver obj : lst){

        }
    }

}

