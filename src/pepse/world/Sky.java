package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;

import java.awt.*;

/***
 * responsible on sky object
 */
public class Sky {
    private static final String DECODE_COLOR = "#80C6E5";
    private static final Color BASIC_SKY_COLOR = Color.decode(DECODE_COLOR);
    private static final String SKY = "sky";

    /***
     * creates the sky background
     * @param windowDimensions
     * @return
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(SKY);

        return sky;
    }

}
