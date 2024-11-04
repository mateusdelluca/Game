package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Stickman extends Objeto{

//    private FixtureDef fixtureDef;
//    protected BodyDef bDef = new BodyDef();
    protected World world;
//    protected PolygonShape shape;
//    private Body body;
    protected boolean flip;
    public static final int WIDTH = 400, HEIGHT = 300;
    public StickmanBoxBounds box;
    public float rotation;
    protected boolean visible = true;
    protected boolean split;
    protected boolean stand, hited;



    public Stickman(World world){
        this.world = world;
        createBody();
    }

    public void createBody(){
        box = new StickmanBoxBounds(world);
    }

    protected boolean loopTrueOrFalse(String name){
        return (name.equals("WALKING") && getBody().getLinearVelocity().x != 0) | (name.equals("WALKING") && getBody().getLinearVelocity().y != 0)
                | (name.equals("IDLE") && Math.abs(getBody().getLinearVelocity().x) <= 1.0f) | (name.equals("E_WALKING") && getBody().getLinearVelocity().x != 0) |
                (name.equals("E_WALKING") && getBody().getLinearVelocity().y != 0) | (name.equals("E_IDLE") && Math.abs(getBody().getLinearVelocity().x) <= 1.0f) |
                (name.equals("IDLE_FLASH"));
    }

    public abstract void render(SpriteBatch s);

    public abstract void update(float delta);

    public void dispose(){
        world.dispose();
    }

    public Body getBody(){
        return box.getBody();
    }

    public Rectangle getBodyBounds(){
        return new Rectangle(getBody().getPosition().x + WIDTH/2f - 25, getBody().getPosition().y + 90, 50, 150);
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(getBody().getPosition().x, getBody().getPosition().y, width, height);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public void setHited(boolean b) {
        hited = b;
    }

    public boolean isHited() {
        return hited;
    }
}
