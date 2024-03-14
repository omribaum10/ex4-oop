package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * @author omri baum and ayelet hashhar halevy
 * creates the sun gameobhect thet cycles on screen
 */
public class Sun {


    private static final float DOUBLE = 2;
    private static final float WINDOW_FACTOR = 0.333F;
    private static final Float INITIAL_VALUR = (float) 0;
    private static final Float FINAL_VALUE = 360F;
    private static final float SUN_KOTER = 50;

    /**
     *
     * @param windowDimensions WINDOW DIMENSIONS
     * @param cycleLength time of life cyclelength
     * @return sun gameobject
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength){
        float sun_koter = SUN_KOTER;
        Vector2 sun_dimensions = new Vector2(sun_koter,sun_koter);
        OvalRenderable sun_image = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(Vector2.ZERO, sun_dimensions, sun_image);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        Vector2 initialSunCenter =
                new Vector2(windowDimensions.x() / DOUBLE, WINDOW_FACTOR * windowDimensions.y());
        //create transition for the sun
        Vector2 cycleCenter =
                new Vector2(windowDimensions.x() / DOUBLE,
                        DOUBLE * WINDOW_FACTOR * windowDimensions.y());
        new Transition<>(sun,
                (Float angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                INITIAL_VALUR, FINAL_VALUE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                //might be a problen with long lifesycle!!!!
                DOUBLE *cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }

}
