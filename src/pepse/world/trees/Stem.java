package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

public class Stem extends GameObject implements AvatarObserver {
    private static final Color TREECOLOR = new Color(100, 50, 20);

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Stem(Vector2 topLeftCorner, Vector2 dimensions) {

        super(topLeftCorner, dimensions, new RectangleRenderable(ColorSupplier.approximateColor(TREECOLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

    }

    /***
     * in case the avatar jumped the tree stem will change its color
     */
    @Override
    public void updateJump() {
        RectangleRenderable stem_image =
                new RectangleRenderable(ColorSupplier.approximateColor(TREECOLOR));
        this.renderer().setRenderable(stem_image);
    }

}
