package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static com.mygdx.game.screens.levels.Level.player;
import static com.mygdx.game.sfx.Sounds.clink2;

public class Crystal extends Item{

    public static final float WIDTH = 40, HEIGHT = 80;

    private Sprite sprite = new Sprite(Images.crystal);
    public Crystal(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());
        visible = true;
    }

    @Override
    public void render(SpriteBatch s) {
        super.render(s);
        if (visible) {
            s.draw(sprite, body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
        } else{
            body.setTransform(new Vector2(-100_000, -100_000), 0);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void update() {
        super.update();
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


    public void beginContact(Body body1, Body body2){
        if ((body1.equals(body) && body2.getUserData().toString().contains("Player"))
            ||
            body2.equals(body) && body1.getUserData().toString().contains("Player")){
            visible = false;
            clink2.play();
            player.takeItem(this);
        }
    }
}
