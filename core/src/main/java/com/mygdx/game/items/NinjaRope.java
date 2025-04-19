package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.levels.Level;
import com.mygdx.game.system.BodyData;

import static com.mygdx.game.Bodies.Builder.box;
import static com.mygdx.game.Bodies.Builder.circle;
import static com.mygdx.game.images.Images.ninjaRope_inventory;
import static com.mygdx.game.images.Images.shoot;
import static com.mygdx.game.screens.levels.Level_Manager.viewport;
import static com.mygdx.game.screens.levels.Level_Manager.world;

public class NinjaRope extends Objeto implements Item{

    private Body anchorBody, anchorBody2;
    private float worldX, worldY;
    private float degrees, radians;
    private Vector2 endPoint;
    public static final int LIMIT = 20;
    public static boolean isActive2;
    public static boolean[] isActive = new boolean[LIMIT], jointBodies = new boolean[LIMIT];

    private float length = 200f;

    private Vector2 mousePos = new Vector2();

    private Body playerBody;

    private Joint joint;

    Sprite[] rope = new Sprite[200];
    private boolean[] created = new boolean[LIMIT];

    private Body[] bodyA = new Body[LIMIT], bodyB = new Body[LIMIT];
    private boolean[] jointPlayerToBodyA = new boolean[LIMIT];
    private boolean[] touched = new boolean[LIMIT];
    private boolean init;
    private Joint[] jointsBodiesAB = new Joint[LIMIT];
    private Joint[] jointsAB = new Joint[LIMIT];
    private Joint[] joints = new Joint[LIMIT];
    private boolean created2;
    private boolean mouseButtonLeft, mouseButtonRight;

    public NinjaRope(Body playerBody){
        this.playerBody = playerBody;
    }

    public NinjaRope(Vector2 position){
        super(75f, 85f);
        body = createBody(new Vector2(75/2f,85/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(this.toString());
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void update() {
        if (!init) {
            for (int index = 0; index < LIMIT; index++) {
                DistanceJointDef distanceJointDef = new DistanceJointDef();
                bodyA[index] = box(new Vector2(playerBody.getWorldCenter().x + 300 + (50 * index), playerBody.getWorldCenter().y + 50), new Vector2(1, 10), BodyDef.BodyType.DynamicBody, false, "bodyA");
                bodyB[index] = box(new Vector2(bodyA[index].getWorldCenter().x, bodyA[index].getWorldCenter().y + 100), new Vector2(1, 10), BodyDef.BodyType.StaticBody);
                bodyA[index].setUserData("joint");
                bodyB[index].setUserData("joint");
                distanceJointDef.initialize(bodyA[index], bodyB[index], bodyA[index].getWorldCenter(), bodyB[index].getWorldCenter());
                distanceJointDef.length = 100f;
                jointsBodiesAB[index] = world.createJoint(distanceJointDef);
                jointsBodiesAB[index].setUserData("joint");
                init = true;
            }
        }

        for (int index = 0; index < LIMIT; index++) {
            if (jointPlayerToBodyA[index]) {
                DistanceJointDef distanceJointDef = new DistanceJointDef();
                distanceJointDef.initialize(bodyA[index], playerBody, bodyA[index].getWorldCenter(), new Vector2(playerBody.getWorldCenter().x + 32, playerBody.getWorldCenter().y + 64));
                distanceJointDef.length = 1f;
                jointsAB[index] = world.createJoint(distanceJointDef);
                jointsAB[index].setUserData("joint");
                joints[index] = jointsAB[index];
                jointPlayerToBodyA[index] = false;
            }
        }
//        destroyJoint(jointsAB);
    }

    public void destroyJoint(Joint... joints1){
        if (spacePressedOrMouseButtonClicked()) {
            for (int index = 0; index < joints1.length; index++) {
                if (joints1[index] != null) {
                    if (jointPlayerToBodyA[index]) {
                        world.destroyJoint(joints1[index]);
                        jointsAB[index] = null;
                        jointPlayerToBodyA[index] = false;
                        break;
                    }
                }
            }
        }
    }

    private boolean spacePressedOrMouseButtonClicked(){
        return mouseButtonRight || mouseButtonLeft;
    }

    @Override
    public void setUserData(Body body) {

    }

    @Override
    public void setUserData(String name) {

    }

    @Override
    public BodyData getBodyData() {
        return null;
    }

    @Override
    public void setVisible(boolean b) {
        visible = b;
    }

    public void mouseMoved(int screenX, int screenY){
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0f);
        viewport.unproject(worldCoordinates);
        worldX = worldCoordinates.x;
        worldY = worldCoordinates.y;

    }

    public void touchDown(int button){
        justTouched(button);
    }

    public void angle(){
        float dx = worldX - Math.abs(playerBody.getPosition().x + 64);
        float dy = worldY - Math.abs(playerBody.getPosition().y + 64);
        degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
        radians = (float) Math.atan2(dy, dx);
    }

    public void justTouched(int button) {
        if (isActive2) {
            System.out.println(Input.Buttons.RIGHT);
            if (button == (Input.Buttons.RIGHT)) {
                deactivate();
                mouseButtonRight = !mouseButtonRight;
            } else {
                if (button == (Input.Buttons.LEFT)) {
                    mouseButtonLeft = !mouseButtonLeft;
                    deactivate();
                    Vector3 mousePos1 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    viewport.unproject(mousePos1); // Desprojetar a posição do mouse
                    worldX = mousePos1.x;
                    worldY = mousePos1.y;
                    mousePos = new Vector2(mousePos1.x, mousePos1.y);
                    anchorBody = box(new Vector2(mousePos), new Vector2(5, 5), BodyDef.BodyType.StaticBody, true, "Rope");
                    activateRope(mousePos);
                }
            }
        }
    }
    private void createAnchor() {
        if (anchorBody == null || playerBody == null)
            return;

        if (anchorBody.isActive()) {
            if (Level.contains(Images.staticObjects, anchorBody.getWorldCenter())) {
               if (joint != null && created2) {
                   world.destroyJoint(joint);
                   created2 = false;
               }
               DistanceJointDef distanceJointDef = new DistanceJointDef();
               distanceJointDef.initialize(playerBody, anchorBody, playerBody.getWorldCenter(), anchorBody.getWorldCenter());
               distanceJointDef.length = length;
               joint = world.createJoint(distanceJointDef);
               joint.setUserData("joint");
               created2 = true;
            }
        }
    }

    public void activateRope(Vector2 target) {
        activate(target);
    }

    public void activate(Vector2 target) {
        deactivate();
        createAnchor();
    }

    public void deactivate() {
       for (int index = 0; index < LIMIT; index++) {
           if (jointsAB[index] != null) {
               world.destroyJoint(jointsAB[index]);
               jointsAB[index] = null;
           }
       }
    }


    public void render(ShapeRenderer shapeRenderer, Rectangle rect){
        shapeRenderer.setColor(Color.RED);
        Vector2 playerPos = playerBody.getPosition();
        Vector2 anchorPos = mousePos;
//        shapeRenderer.line(playerBody.getWorldCenter().x + 50, playerBody.getWorldCenter().y, anchorPos.x, anchorPos.y);
    }

    public void render(SpriteBatch batch) {
        if (body != null) {
            Sprite sprite = new Sprite(ninjaRope_inventory);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.setOriginCenter();
            sprite.rotate(1f);

            if (visible) {
                sprite.draw(batch);
            } else{
                body.setTransform(new Vector2(-10_000,10_000), 0);
            }

        }
        if (isActive2) {
            batch.draw(shoot, worldX - 13, worldY - 9);
        }
        if (playerBody != null){
            for (int i = (int) playerBody.getWorldCenter().x; i < mousePos.x && i < 200; i++) {
                rope[i] = (Images.rope);
                rope[i].setPosition(playerBody.getWorldCenter().x + (80f * i) - 20f,
                    playerBody.getWorldCenter().y + (20f * 1));
                rope[i].setSize(80f, 20f);
    //            rope[i].setRotation(45f);
                rope[i].draw(batch);
            }
        }
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void beginContact(Contact contact) {

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        if (body1 == null || body2 == null)
            return;

        for (int index = 0; index < LIMIT; index++) {
            if (bodyA[index] != null && playerBody != null && !jointPlayerToBodyA[index] && !touched[index]) {
                if ((body1.getUserData().equals(bodyA[index].getUserData()) && body2.getUserData().equals("Boy"))
                    || (body2.getUserData().equals(bodyA[index].getUserData()) && body1.getUserData().equals("Boy"))) {
                    touched[index] = true;
                    jointPlayerToBodyA[index] = true;
                }
            }
        }

    }
}
