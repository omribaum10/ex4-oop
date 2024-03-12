package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;

/***
 * responsible on fruit obzect behavior
 */
public class Fruit extends GameObject implements pepse.world.trees.AvatarObserver {
    private static final String FRUIT = "fruit";
    private static final String DELAYED_STATE = "delayed fruit";
    private static final int DELAY_TIME = 30;
    private static Renderable renderable;

    /**
     * Construct a new fruit GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Fruit(Vector2 topLeftCorner,
                 Vector2 dimensions,
                 Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        super.setTag(FRUIT);
        this.renderable = renderable;
    }

    /***
     * in case of avatar colision the eneregy will increase by 10 and the fruit will desapear for 30 sec
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(Avatar.AVATAR_TAG)){
            if(super.getTag().equals(FRUIT)){
                Avatar.addEnergy();
                super.setTag(DELAYED_STATE);
                this.renderer().setRenderable(null);
                new ScheduledTask(
                        this, DELAY_TIME,
                        false, this::activateFruit);
            }
        }

    }

    private void activateFruit(){
        super.setTag( FRUIT);
        this.renderer().setRenderable(renderable);
    }

    /***
     * in case the avatar jumped the fruit will change its color
     */
    public void updateJump(){
        if(super.getTag().equals(FRUIT)){
            OvalRenderable new_image =  new OvalRenderable(ColorSupplier.approximateColor(Tree.FRUIT_COLOR));
            renderable = new_image;
            this.renderer().setRenderable(new_image);
        }
    }
}
