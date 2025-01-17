package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.Item;
import com.mygdx.game.images.Images;

public class Portal extends Objeto implements Item{

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;

    public boolean open_portal;

    public Portal(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setUserData(getClass().getSimpleName());
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch s){
        s.setColor(1f,1f,1f,1f);
        s.draw(Images.portal.currentSpriteFrame(!open_portal, true, false), body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public void updateItem() {
        open_portal = true;
    }

    @Override
    public void update() {

    }

    @Override
    public void renderShape(ShapeRenderer s) {
        s.rect(body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Rectangle getRectangle(){
        return new Rectangle(body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }

}
