package com.mygdx.game.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.bodiesAndShapes.BodiesAndShapes;

import static com.mygdx.game.screens.levels.Level_Manager.viewport;

public class Mouse {

    public static Body mouseBody;
    public static Vector3 mouseCoord;

    public static boolean clicked;

    public Mouse(Vector2 position){
        mouseBody = BodiesAndShapes.box(position, new Vector2(6f,18f), BodyDef.BodyType.DynamicBody, true, "Mouse");
        mouseBody.setGravityScale(0f);
    }

    public void mouseMoved(int x, int y){
        mouseCoord = new Vector3(x,y,0);
        viewport.unproject(mouseCoord);
        mouseBody.setTransform(mouseCoord.x, mouseCoord.y, mouseBody.getAngle());
    }

    public void touchDown(int x, int y, int button){
        if (button == Input.Buttons.LEFT){
            clicked = true;
        } else{
            clicked = false;
        }
    }

}
