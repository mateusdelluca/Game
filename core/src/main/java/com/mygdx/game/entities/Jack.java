package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Images;

import java.util.ArrayList;

import static com.mygdx.game.screens.levels.Level.bullets;

public class Jack extends Objeto{

    public static final float DIVISOR = 1.4f;
    public static final float WIDTH = Images.jack.getWidth()/DIVISOR, HEIGHT = Images.jack.getHeight()/DIVISOR;
    private boolean flip = true;


    private float timer;
    public Jack(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }

    private void update(){
        timer += Gdx.graphics.getDeltaTime();
        if (timer > 3f){
            bullets.add(new Bullet(world, new Vector2(!flip ? getBody().getPosition().x +
                WIDTH / 2f : getBody().getPosition().x - WIDTH / 2f,
                getBody().getPosition().y + HEIGHT / 2f), !flip, (float) Math.PI));
            timer = 0f;
        }
    }

    public void render(SpriteBatch s){
        update();
        Sprite sprite = new Sprite(Images.jack);
        sprite.flip(flip, false);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(s);
    }

    @Override
    public void render(ShapeRenderer s) {

    }
}
