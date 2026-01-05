package com.mygdx.game.platform;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.entities.Objeto;

import static com.mygdx.game.bodiesAndShapes.BodiesAndShapes.box;

public class JointFragment extends Objeto {

    public static final float DIVISOR = 1.5f;

    public static final float SIDE = 60f/DIVISOR;


    private Body begin, end;

    public JointFragment(Vector2 mousePosition, Vector2 playerPosition){
        begin = box(new Vector2(playerPosition.x - SIDE /2f, playerPosition.y - SIDE /2f), new Vector2(SIDE /2f, SIDE /2f), BodyDef.BodyType.StaticBody, true, "begin", 10f);
        end = box(new Vector2(mousePosition.x - SIDE /2f, mousePosition.y - SIDE /2f), new Vector2(SIDE /2f, SIDE /2f), BodyDef.BodyType.StaticBody, true, "end", 10f);
    }







    @Override
    public void renderShape(ShapeRenderer s) {

    }
}
