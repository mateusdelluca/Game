package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.sfx.Sounds.LASER_HEADSET;

public class Laser_Headset implements Item{


    @Getter @Setter
    private boolean shoot;

    @Override
    public void render(SpriteBatch s) {


        if (shoot){
            shoot();
        }
    }

    public void shoot(){
        if (shoot){
            shoot = false;
            LASER_HEADSET.play();
        }
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void update() {
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
    public void setVisible(boolean b) {

    }
}
