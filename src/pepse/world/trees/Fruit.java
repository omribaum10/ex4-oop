package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.world.Avatar;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Fruit extends GameObject implements pepse.world.trees.AvatarObserver {
    public static final String FRUIT_TAG = "fruit";
    public static final String DELAYED_FRUIT_TAG = "delayed fruit";
    public static final int DELAY_TIME = 30;
    public static Renderable renderable;
    /**
     * Construct a new GameObject instance.
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
        this.setTag(FRUIT_TAG);
        this.renderable = renderable;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(Avatar.AVATAR_TAG)){
            if(this.getTag().equals(FRUIT_TAG)){
                Avatar.addEnergy();
                this.setTag(DELAYED_FRUIT_TAG);
                this.renderer().setRenderable(null);
                new ScheduledTask(
                        this, DELAY_TIME,
                        false, this::activateFruit);
            }
        }

    }

    private void activateFruit(){
        this.setTag(FRUIT_TAG);
        this.renderer().setRenderable(renderable);
    }

    public void updateJump(){
        if(this.getTag().equals(FRUIT_TAG)){
            int new_tone = PepseGameManager.rand.nextInt(256);
            OvalRenderable new_image =  new OvalRenderable(new Color(new_tone,new_tone,new_tone));
            renderable = new_image;
            this.renderer().setRenderable(new_image);
        }
    }
}
