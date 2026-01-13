package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;
import com.mygdx.game.screens.levels.Level;
import com.mygdx.game.system.BodyData;

import static com.mygdx.game.images.Images.ninjaRope;

public class NinjaRope extends Item{

    public static final float WIDTH = 75f, HEIGHT = 85f;
    private final Sprite ninjaRopeSprite = new Sprite(ninjaRope);

    public NinjaRope(Vector2 position){
        super(WIDTH, HEIGHT);
        body = BodiesAndShapes.box(position, new Vector2(WIDTH/2, HEIGHT/2), BodyDef.BodyType.StaticBody, true, this.toString());
        body.setTransform(position, 0);
        Level.items.put(this.toString(), this);
    }


    public void render(SpriteBatch s){
        ninjaRopeSprite.setPosition(body.getPosition().x - WIDTH/4f, body.getPosition().y - HEIGHT/4f);
        ninjaRopeSprite.rotate(-1f);
        if (visible)
            ninjaRopeSprite.draw(s);
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

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
    public String toString() {
        return getClass().getSimpleName();
    }
}
