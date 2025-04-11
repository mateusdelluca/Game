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
import com.badlogic.gdx.utils.Array;
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
    public static boolean isActive, jointBodies;

    private float length = 200f;

    private Vector2 mousePos = new Vector2();

    private Body playerBody;

    private Joint joint;

    Sprite[] rope = new Sprite[200];
    private boolean created;
    private Body bodyA, bodyB;
    private boolean jointPlayerToBodyA;
    private boolean touched;
    private boolean init;
    private Joint jointAB;
    private boolean jointABactive;
    private Array<Joint> joints = new Array<>();

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
            DistanceJointDef distanceJointDef = new DistanceJointDef();
            bodyA = box(new Vector2(playerBody.getWorldCenter().x + 300, playerBody.getWorldCenter().y + 50), new Vector2(1, 10), BodyDef.BodyType.DynamicBody, false, "bodyA");
            bodyB = box(new Vector2(bodyA.getWorldCenter().x, bodyA.getWorldCenter().y + 100), new Vector2(1, 10), BodyDef.BodyType.StaticBody);
            distanceJointDef.initialize(bodyA, bodyB, bodyA.getWorldCenter(), bodyB.getWorldCenter());
            distanceJointDef.length = 100f;
            world.createJoint(distanceJointDef);
            init = true;
        }


        if (jointPlayerToBodyA) {
            DistanceJointDef distanceJointDef = new DistanceJointDef();
            distanceJointDef.initialize(bodyA, playerBody, bodyA.getWorldCenter(), new Vector2(playerBody.getWorldCenter().x + 32, playerBody.getWorldCenter().y + 64));
            distanceJointDef.length = 1f;
            jointAB = world.createJoint(distanceJointDef);
            jointAB.setUserData("joint");
            joints.add(jointAB);
            jointPlayerToBodyA = false;
        }

        for (Joint joint1 : joints){
            destroyJoint(joint1);
        }
    }

    public void destroyJoint(Joint joint){
        if (joint != null && joint.isActive() && spaceOrClicked() && joint.getUserData() != null && !joint.getUserData().toString().equals("null")){
            joint.setUserData("null");
            joints.removeValue(joint, true);
//            world.destroyJoint(joint);
        }
    }

    private boolean spaceOrClicked(){
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT)
            || Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
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
        if (button == (Input.Buttons.RIGHT)) {
            deactivate();
        } else{
            if (button == (Input.Buttons.LEFT)) {
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
    private void createAnchor(){
        if (anchorBody == null || playerBody == null)
            return;
        if (isActive) {
            if (anchorBody.isActive()){
                if (Level.contains(Images.staticObjects, anchorBody.getWorldCenter())){
                    jointBodies = true;
            }
        }

        DistanceJointDef distanceJointDef = new DistanceJointDef();
        distanceJointDef.initialize(playerBody, anchorBody, playerBody.getWorldCenter(), anchorBody.getWorldCenter());
        distanceJointDef.length = length;
        if (jointBodies) {
            joint = world.createJoint(distanceJointDef);
            joint.setUserData("joint");
            joints.add(joint);
            created = true;
            jointBodies = false;
            }
        }
    }

    public void activateRope(Vector2 target) {
        activate(target);
    }

    public void activate(Vector2 target) {
        if (created)
            deactivate();
        if (!isActive) {
            isActive = true;
            createAnchor();
        }
        if (!created)
            isActive = false;
    }

    public void deactivate() {
       if (isActive) {
           if (joint != null && joint.isActive() && created) {
               System.out.println(joint);
               world.destroyJoint(joint);
               if (jointAB != null && jointAB.isActive() && !jointABactive) {
                   world.destroyJoint(jointAB);
                   jointABactive = true;
               }
               System.out.println(joint);
           }
//           if (anchorBody != null && anchorBody.isActive() && created)
//               world.destroyBody(anchorBody);
           isActive = false;
           created = false;
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
            }

        }
        if (isActive) {
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


        if (bodyA != null && playerBody != null && !jointPlayerToBodyA && !touched){
            if ((body1.getUserData().equals(bodyA.getUserData()) && body2.getUserData().equals("Boy"))
                || (body2.getUserData().equals(bodyA.getUserData()) && body1.getUserData().equals("Boy"))){
                touched = true;
                jointPlayerToBodyA = true;
            }
        }

    }
}
