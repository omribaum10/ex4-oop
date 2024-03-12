package pepse.world.daynight;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * @author omri baum ayeylet hashhar halevy
 * creates a rectangle to blacken the screen
 */
public class Night {
    private static final float LIGHT = 0f;
    private static final float DARK = 0.5f;

    /**
     *
     * @param windowDimensions to modify rectangle dimensions
     * @param cycleLength of day and night
     * @return Gameobject responsible of day and night
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength){
        RectangleRenderable NightRectangle = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, NightRectangle);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Creates a day cycle increasing and decreasing the opacity
        new Transition<>(night, night.renderer()::setOpaqueness, LIGHT, DARK,
                Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

        return night;
    }

}

