package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {


    private static final String SUN = "sun";
    private static final float FIVE = 6;
    private static final float TWO = 2;
    private static final float THIRD = 0.333F;
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
//        float sun_koter = (windowDimensions.y() - Block.SIZE) / FIVE;
        float sun_koter = SUN_KOTER;
        Vector2 sun_dimensions = new Vector2(sun_koter,sun_koter);
        OvalRenderable sun_image = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(Vector2.ZERO, sun_dimensions, sun_image);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN);

        Vector2 initialSunCenter =
                new Vector2(windowDimensions.x() / TWO, THIRD * windowDimensions.y());
        //create transition for the sun
        Vector2 cycleCenter = new Vector2(windowDimensions.x() / TWO,TWO * THIRD * windowDimensions.y());
        new Transition<>(sun,
                (Float angle) -> sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                INITIAL_VALUR, FINAL_VALUE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                //might be a problen with long lifesycle!!!!
                TWO*cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }

}
