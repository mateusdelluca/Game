package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.images.Monster_Sprites;

public class Monster3 extends Objeto{

    public static final float DIVISOR = 4f;
    public static final float WIDTH = 250/DIVISOR, HEIGHT = 250/DIVISOR;
    Monster_Sprites animation = new Monster_Sprites();

    public Monster3(Vector2 position){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.DynamicBody, false, this.toString(), 1f);
        body.setTransform(position, 0);
        animation.changeAnimation("");
        body.setFixedRotation(true);
    }

    @Override
    public void render(SpriteBatch s) {
        Sprite sprite = new Sprite(animation.currentAnimation.currentSpriteFrameUpdateStateTime(false, true, false));
        sprite.setSize(WIDTH, HEIGHT);
        sprite.setPosition(body.getPosition().x - WIDTH/4f, body.getPosition().y - HEIGHT/4f);
        sprite.draw(s);
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
