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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.mygdx.game.Bodies.Builder;
import com.mygdx.game.entities.Objeto;
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
    public static boolean isActive;

    private float length = 200f;

    private Vector2 mousePos = new Vector2();

    private Body playerBody;

    private Joint joint;


    public NinjaRope(Body playerBody){
        this.playerBody = playerBody;
    }

    public NinjaRope(Vector2 position){
        super(75f, 85f);
        body = createBody(new Vector2(75/2f,85/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(this.toString());
    }

    private void createAnchor(){
//      // Criação do Ponto de Ancoragem
//        anchorBody2 = circle(new Vector2(400f,6000 - 400f), 10);
        if (isActive) {

            anchorBody = box(new Vector2(mousePos), new Vector2(20, 20), BodyDef.BodyType.StaticBody, true);
            // Criação do RopeJoint
            DistanceJointDef distanceJointDef = new DistanceJointDef();
            distanceJointDef.initialize(playerBody, anchorBody, playerBody.getPosition(), anchorBody.getPosition());
            distanceJointDef.length = length;
            joint = world.createJoint(distanceJointDef);
        }
    }

    public void render(SpriteBatch batch) {
        if (body != null) {
            Sprite sprite = new Sprite(ninjaRope_inventory);
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.setOriginCenter();
            sprite.rotate(1f);
            if (visible)
                sprite.draw(batch);
        }
        if (isActive) {
            batch.draw(shoot, worldX - 13, worldY - 9);
        }
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
                activateRope(mousePos);
            }
        }

    }

    public void activateRope(Vector2 target) {
        activate(target);
    }

    public void activate(Vector2 target) {
        deactivate();
        if (!isActive) {
            angle();
            isActive = true;
            createAnchor();
        }
    }

    public void deactivate() {
        if (isActive) {
            if (joint.isActive() && joint != null) {
                System.out.println(joint);
                world.destroyJoint(joint);
                System.out.println(joint);
            }
            if (anchorBody != null && anchorBody.isActive())
                world.destroyBody(anchorBody);
            isActive = false;
        }
    }

    public void render(ShapeRenderer shapeRenderer, Rectangle rect){
        shapeRenderer.setColor(Color.RED);
        Vector2 playerPos = playerBody.getPosition();
        Vector2 anchorPos = mousePos;
        shapeRenderer.line(playerBody.getWorldCenter().x + 50, playerBody.getWorldCenter().y, anchorPos.x, anchorPos.y);
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
