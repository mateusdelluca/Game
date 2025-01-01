package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.GUNSHOT;

public class Magazine implements Item{

    private int bulletsLimit = 30;

    @Getter
    @Setter
    private ArrayList<Bullet> bullets = new ArrayList<>();
    public static String numbBullets = "30/90";
    public static boolean showingNumbBullets = false;

    public Magazine(int bulletsLimit){
        this.bulletsLimit = bulletsLimit;
    }

    @Override
    public void render(SpriteBatch s) {
       if (limitOfBullets()) {
           for (Bullet bullet : bullets)
               bullet.render(s);
       }
        if (showingNumbBullets)
            numbBullets = bulletsLimit + " /90";
        else
            numbBullets = "";
    }

    @Override
    public void updateItem() {

    }

    private boolean limitOfBullets(){
        return bullets.size() < bulletsLimit;
    }

    public void newBullet(Bullet bullet){
        bullets.add(bullet);
        if (bulletsLimit > 0) {
            bulletsLimit--;
            GUNSHOT.play();
        }

    }
}
