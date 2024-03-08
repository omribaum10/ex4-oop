package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Supplier;

public class EnergyBar extends GameObject {

    private float energyrate;
    private Supplier<Float> getenergy;
    private static final int FIFTEEN = 15;
    private static final String BAR_TAG = "energy";
    private static final float TWENTYFIVE = 25;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public EnergyBar(Vector2 topLeftCorner, Vector2 dimensions,
                     Supplier<Float> energy) {
        super(topLeftCorner, dimensions, null);
        this.getenergy = energy;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderer().setRenderable(new TextRenderable(getenergy.get().toString()));
    }

    //    public static GameObject Create(Vector2 windowDimensions,
//                                    UserInputListener inputListener,
//                                    ImageReader imageReader){
//        float Barwidth = windowDimensions.x() / FIFTEEN;
//        float Barheight = windowDimensions.y() / TWENTYFIVE;
//        RectangleRenderable NightRectangle = new RectangleRenderable(Color.BLACK);
//        GameObject night = new GameObject(Vector2.ZERO,
//                new Vector2(Barwidth,Barheight), NightRectangle);
////        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
//        night.setTag(BAR_TAG);
//
//        GameObject avatar = new Avatar(Vector2.ZERO,inputListener,imageReader);
//        // Creates a energy bar
//        new Transition<>(night, night.renderer()::setRenderable, 100, 0,
//                Transition.LINEAR_INTERPOLATOR_FLOAT,
//                (avatar.callback.GetEnergyRate())->  ,
//                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
//
//        return night;
    }

