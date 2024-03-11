package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;
import pepse.world.trees.AvatarObserver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/***
 * responsible on the avatar singelton
 */
public class Avatar extends GameObject{
    private static final double TIME_DELAY = 0.1;
    private static Avatar avatar;
    /***
     * the avatar tag to check in fruit colision
     */
    public static final String AVATAR_TAG = "avatar";
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    private static final float AVATAR_SPACE = 50;
    private static final String TERRAIN = "ground";
    private static final float MIN_JUMP_ENERGY = 10;
    private static final float ZERO_VELOCITY = 0;
    private static final float ONE_FACTOR = 1;
    private static final float MIN_MOVE_ENERGY = 0.5F;
    private static final float MAX_ENERGY = 100;
    String[] stand_set = new String[] {"assets/idle_0.png", "assets/idle_1.png",
            "assets/idle_2.png","assets/idle_3.png"};
    String[] jump_set = new String[] {"assets/jump_0.png", "assets/jump_1.png",
            "assets/jump_2.png","assets/jump_3.png"};
    String[] move_set = new String[] {"assets/run_0.png", "assets/run_1.png",
            "assets/run_2.png","assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};

    private static UserInputListener inputListener;
    private static ImageReader imageReader;
    private static float Energy;
    private static boolean AirFlag;
    private static boolean MoveFlag;
    private static AnimationRenderable standing;
    private static AnimationRenderable jumping;
    private static AnimationRenderable moving;
    private static ArrayList<AvatarObserver> observers = new ArrayList<>();


    /**
          *
          * @param pos initial position
          * @param inputListener for moving the avatar
          * @param imageReader to read avatar image
          */
    private Avatar(Vector2 pos,
                  UserInputListener inputListener,
                  ImageReader imageReader) {
        super(new Vector2(pos.x() , pos.y() - AVATAR_SPACE),
                Vector2.ONES.mult(AVATAR_SPACE),
                imageReader.readImage("idle_0.png",
                        true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.Energy = 0f;
//        this.Energy = 100;
        this.AirFlag = true;
        this.MoveFlag = false;
        this.standing =
                new AnimationRenderable(stand_set,imageReader,true,TIME_DELAY);
        this.jumping =
                new AnimationRenderable(jump_set,imageReader,true,TIME_DELAY);
        this.moving =
                new AnimationRenderable(move_set,imageReader,true,TIME_DELAY);
        this.setTag(AVATAR_TAG);
    }

    /***
     * in case it is exist returs an instance of the single avatar and otherwise
     * initialze the avatar and returns the instance
     * @param pos initial position
     * @param inputListener for moving the avatar
     * @param imageReader to read avatar image
     * @return the instance of the single avatar
     */
    public static Avatar getInstance(Vector2 pos,
                                     UserInputListener inputListener,
                                     ImageReader imageReader ){
        if(avatar != null){
            return avatar;
        }
        avatar = new Avatar(pos,inputListener,imageReader);
        return avatar;
    }

    /***
     * add 10 to the current energy count in limit of 100
     */
    public static void addEnergy() {
        Energy = Math.min(Energy + MIN_JUMP_ENERGY, MAX_ENERGY);
    }

    /***
     * gets the current avatar energy
     * @return the current avatar energy
     */
    public static Float getenergy(){
        return Energy;
    }

    /***
     * add observer object
     * @param observer
     */
    public static void AddObserver(AvatarObserver observer){
        observers.add(observer);
    }

    private static void UpdateObservers(){
        for(AvatarObserver observer : observers){
            observer.updateJump();
        }
    }

    /***
     * updates the avatar movement and related objects
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = ZERO_VELOCITY;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)&&
                Energy >= MIN_MOVE_ENERGY){
            this.Energy -= MIN_MOVE_ENERGY;
            xVel -= VELOCITY_X;
            renderer().setRenderable(moving);
        }
        else if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                Energy >= MIN_MOVE_ENERGY){
            this.Energy -= MIN_MOVE_ENERGY;
            xVel += VELOCITY_X;
            renderer().setRenderable(moving);
        }
        else if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                getVelocity().y() == ZERO_VELOCITY &&
                Energy >= MIN_JUMP_ENERGY){
            this.Energy -= MIN_JUMP_ENERGY;
            transform().setVelocityY(VELOCITY_Y);
            renderer().setRenderable(jumping);
            UpdateObservers();
        }
        else if(getVelocity().y() == 0 &&
                inputListener.isKeyPressed(KeyEvent.VK_RIGHT)  == false &&
                inputListener.isKeyPressed(KeyEvent.VK_LEFT)  == false){
            renderer().setRenderable(standing);
            this.Energy = Math.min(MAX_ENERGY,Energy + ONE_FACTOR);
        }
        transform().setVelocityX(xVel);
    }
}

