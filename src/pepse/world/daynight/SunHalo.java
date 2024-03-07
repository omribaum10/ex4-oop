package pepse.world.daynight;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {
    private static final Color BASE_GROUND_COLOR =
            new Color(255, 255, 0, 20);
    private static final float SUN_HALO_KOTER = 70;
    private static final String SUN_HALO = "sun halo";
    private static final float TWO = 2;
    private static final float THIRD = 0.333F;

    /**
     *
     * @param windowDimensions WINDOW DIMENSIONS
     * @param cycleLength time of life cyclelength
     * @return sun gameobject
     */
    public static GameObject create(GameObject sun){


//        float sun_koter = (windowDimensions.y() - Block.SIZE) / FIVE;
        float sun_koter = SUN_HALO_KOTER;
        Vector2 sun_dimensions = new Vector2(sun_koter,sun_koter);
        OvalRenderable sun_image = new OvalRenderable(BASE_GROUND_COLOR);
        GameObject sun_halo = new GameObject(Vector2.ZERO, sun_dimensions, sun_image);
        sun_halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_HALO);
        sun_halo.addComponent(parameter -> sun_halo.setCenter(sun.getCenter()));

        return sun_halo;
    }
}
