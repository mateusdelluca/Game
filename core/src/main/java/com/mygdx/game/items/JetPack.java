package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.system.BodyData;

import static com.mygdx.game.images.Images.spriteJetPack;
import static com.mygdx.game.screens.levels.Level.world;

public class JetPack extends Item{

    public static final float WIDTH = 128f, HEIGHT = 128f;

    private float angle = 0;

    public JetPack(Vector2 position){
        super(WIDTH, HEIGHT);
        body = createBody(new Vector2(WIDTH/2f, HEIGHT/2f), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());
    }

    @Override
    public void render(SpriteBatch s) {
//        if (body == null)
//            body = bodyData.convertDataToBody(BodyDef.BodyType.StaticBody, true);
        if (visible) {
            spriteJetPack.setOrigin(WIDTH/2f, HEIGHT/2f);
            spriteJetPack.setPosition(body.getPosition().x, body.getPosition().y);
            spriteJetPack.setRotation(angle++);
            spriteJetPack.draw(s);
        }
    }

    @Override
    public void update() {
        if (!visible && body != null) {
            body.setTransform(10_000, 10_000, 0);
            body.setUserData("null");
            world.destroyBody(body);
            body = null;
        }
    }

    @Override
    public void renderShape(ShapeRenderer s) {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
