package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;

import java.awt.*;
import java.util.Random;

/***
 * the leaf object
 */
public class Leaf extends GameObject implements pepse.world.trees.AvatarObserver {
    /***
     * the leaf tag to place in specific layer
     */
    public static final String LEAF = "leaf";
    private static final float LOW_ANGLE_BOUND = -30f;
    private static final float HIGH_ANGLE_BOUND = 30f;
    private static final float TEN = 10;
    private static final float LOW_WIDTH_BOUND = 7f;
    private static final float HIGH_WIDTH_BOUND = 10f;
    private static final Color COLOR = new Color(50,200,30);
    private static final float DEGREES_ANGLE = 90;
    private static final float SHORT_TRANS = 1;
    private static final float LONG_TRANS = 2;
    private int delay_time = PepseGameManager.rand.nextInt(10);




    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        //angle transition
        this.setTag(LEAF);

        new ScheduledTask(
                this,delay_time,
                false,this::ActivateTransitions);

    }

    private void ActivateTransitions(){
        new Transition<>(this,
                (angle)-> this.renderer().setRenderableAngle((float)angle),
                LOW_ANGLE_BOUND, HIGH_ANGLE_BOUND,
                Transition.LINEAR_INTERPOLATOR_FLOAT, LONG_TRANS,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

        //width transition
        new Transition<>(this,
                (width)->this.setDimensions(new Vector2(width,width)),
                LOW_WIDTH_BOUND, HIGH_WIDTH_BOUND,
                Transition.LINEAR_INTERPOLATOR_FLOAT, SHORT_TRANS,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    /***
     * in case the avatar jumped the leaf will transfor its angle by 90 deg
     */
    public void updateJump(){
        new Transition<>(
                this,
                (angle)-> this.renderer().setRenderableAngle((float)angle),
                this.renderer().getRenderableAngle(),
                this.renderer().getRenderableAngle() + DEGREES_ANGLE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                1, Transition.TransitionType.TRANSITION_ONCE,
                null);
    }

}
