package com.mygdx.game.items.minis;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.items.Rifle;

import java.util.Random;

import static com.mygdx.game.entities.Boy.minis;
import static com.mygdx.game.images.Images.itemsDraw_minis;
import static com.mygdx.game.sfx.Sounds.TRIGGER;

public class Minis extends Objeto{


    public static float R_VELOCITY = 20f;
    private String name;
    public static final float WIDTH = 30, HEIGHT = 30;
    public static final Vector2 dimensions = new Vector2(WIDTH,HEIGHT);
    private boolean dropped;

    private Sprite sprite;

    public Minis(Vector2 position, String name){
        body = BodiesAndShapes.circle(position, dimensions.x/2f, BodyDef.BodyType.DynamicBody, false, this.toString(), 10f);
        dropped = true;
        visible = true;
        sprite = new Sprite(itemsDraw_minis.get(name)); dropped();
        body.setUserData(name);
        minis.add(this);
    }

    public Minis(Vector2 position){
        body = BodiesAndShapes.circle(position, dimensions.x/2f, BodyDef.BodyType.DynamicBody, false, this.toString(), 10f);
        dropped = true;
        visible = true;
        name = random_name();
        sprite = new Sprite(itemsDraw_minis.get(name)); dropped();
        body.setUserData(name);
        minis.add(this);
    }

    private String random_name(){
        int rand = new Random().nextInt(4);
        switch (rand){
            case 0:{
                return "Blue Potion";
            }
            case 1:{
                return "Red Potion";
            }
            case 2:{
                return "Green Potion";
            }
            case 3:{
                return "Cartridge";
            }
        } return "Red Potion";
    }

    @Override
    public void render(SpriteBatch s) {
        update();
        if (visible && body != null && sprite != null) {
            sprite.setPosition(body.getPosition().x - WIDTH/2, body.getPosition().y - HEIGHT/2f);
            sprite.setSize(WIDTH, HEIGHT);
            sprite.setOriginCenter();
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
            sprite.draw(s);
        }
    }

    @Override
    public void update(){

    }

    public void dropped(){
        if (dropped){
            dropped = false;
            body.setLinearVelocity(new Vector2(oneOrMinusOne() * (1f), 30f));
        }
    }

    private int oneOrMinusOne(){
        return random_1() ? -1 : 1;
    }

    private boolean random_1(){
        return new Random().nextInt(2) == 0;
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return name;
    }

    private void takeItem(){
        visible = false;
        TRIGGER.play();
    }


    public void beginContact(Body bodyA, Body bodyB){
        if (bodyA.equals(body) && bodyB.getUserData().toString().equals("Boy") ||
            (bodyB.equals(body) && bodyA.getUserData().toString().equals("Boy"))){
            visible = false;
            TRIGGER.play();
            body.setLinearVelocity(0f,0f);
            body.getFixtureList().get(0).setSensor(true);
            if (name.equals("Cartridge"))
                Rifle.addCartridge = true;
            if (name.equals("Blue Potion"))
                PowerBar.sp_0 += 10;
            if (name.equals("Red Potion"))
                PowerBar.hp_0 += 10;
            if (name.equals("Green Potion"))
                PowerBar.fuel += 30;
        }
    }


}
