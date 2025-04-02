package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

import static com.mygdx.game.Bodies.Builder.box;
import static com.mygdx.game.Bodies.Builder.circle;
import static com.mygdx.game.images.Images.shoot;
import static com.mygdx.game.screens.levels.Level_Manager.viewport;
import static com.mygdx.game.screens.levels.Level_Manager.world;

public class NinjaRope {

    private Body playerBody, anchorBody;
    private float worldX, worldY;
    private float degrees, radians;
    private Vector2 endPoint;
    private boolean isActive;

    private float length = 500f;

    private Vector2 mousePos = new Vector2();

    public NinjaRope(Body playerBody){
        this.playerBody = playerBody;
    }

    private void createAnchor(){
    // Criação do Ponto de Ancoragem
        anchorBody = circle(playerBody.getPosition(), 1);

    // Criação do RopeJoint
        RopeJointDef ropeDef = new RopeJointDef();
        ropeDef.bodyA = playerBody;
        ropeDef.bodyB = anchorBody;
        ropeDef.maxLength = length; // Comprimento máximo da corda
        ropeDef.localAnchorA.set(playerBody.getPosition()); // Ponto no jogador
        ropeDef.localAnchorB.set(mousePos); // Ponto na âncora
        world.createJoint(ropeDef);

    }

    public void render(SpriteBatch batch) {
//        if (!isActive) {
        // Renderiza a corda
//            batch.draw(...); // Desenhe a corda entre startPoint e endPoint
        batch.draw(shoot, worldX - 13, worldY - 9);
//        }
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
        if (button == (Input.Buttons.LEFT)) {
            Vector3 mousePos1 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(mousePos1); // Desprojetar a posição do mouse
            worldX = mousePos1.x;
            worldY = mousePos1.y;
            mousePos = new Vector2(mousePos1.x, mousePos1.y);
            activate(mousePos);
        }
    }

    public void activateRope(Vector2 target) {
        activate(target);
    }

    public void activate(Vector2 target) {
        angle();
        playerBody.setLinearVelocity((float) (target.x * Math.cos(radians)), (float) Math.abs(target.y * Math.sin(target.y)));
        this.isActive = true;
        createAnchor();
    }

    public void deactivate() {
        isActive = false;
    }

    public void render(ShapeRenderer shapeRenderer, Rectangle rect){
        shapeRenderer.setColor(Color.RED);
        Vector2 playerPos = playerBody.getPosition();
        Vector2 anchorPos = mousePos;
        shapeRenderer.line(rect.x + rect.width/2f, rect.y + 100, anchorPos.x, anchorPos.y);


    }

}
