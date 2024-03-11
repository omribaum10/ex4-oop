package pepse.world;

import danogl.GameManager;
import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.trees.AvatarObserver;
import pepse.world.trees.Tree;

/**
 * class for blocks crating the world
 * @author עמרי באום
 */
public class Block extends GameObject implements AvatarObserver {
    public static final int SIZE = 30;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Block(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

    }

    /***
     * in case the avatar jumped the tree stem will change its color
     */
    @Override
    public void updateJump() {
        RectangleRenderable stem_image =
                new RectangleRenderable(ColorSupplier.approximateColor(Tree.TREECOLOR));
        this.renderer().setRenderable(stem_image);
    }

}
