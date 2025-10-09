package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.images.Robot2_Sprites;
import lombok.Getter;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;
import static com.mygdx.game.images.Robot2_Sprites.HEIGHT;
import static com.mygdx.game.images.Robot2_Sprites.WIDTH;

public class Robot extends Objeto{

    @Getter
    private Character_Features char_features;
    private Robot2_Sprites sprites = new Robot2_Sprites();

    private boolean looping = true, facingRight, useOnlyLastFrame;

    public Robot(Vector2 position){
        super(WIDTH, HEIGHT);
        dimensions = new Vector2(46/2f, 108/2f);
        body = box(position, dimensions, BodyDef.BodyType.DynamicBody, false);
        char_features = new Character_Features();
        visible = true;
        facingRight = false;
        sprites.changeAnimation("walking");
    }

    @Override
    public void render(SpriteBatch s) {
        if (visible) {
//            Sprite sprite = new Sprite(sprites.currentAnimation.currentSpriteFrame(useOnlyLastFrame, looping, facingRight));
//            sprite.setPosition(body.getPosition().x - dimensions.x - 20f, body.getPosition().y - dimensions.y/2f);
//            sprite.setSize(Robot2_Sprites.WIDTH, Robot2_Sprites.HEIGHT);
//            sprite.draw(s);
            Sprite sprite = new Sprite(sprites.currentAnimation.currentSpriteFrame(useOnlyLastFrame, looping, facingRight));
            sprite.setPosition(body.getPosition().x - dimensions.x - 40, body.getPosition().y - 120f/2f);
            sprite.setSize(WIDTH, HEIGHT);
            sprite.draw(s);
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }
}
