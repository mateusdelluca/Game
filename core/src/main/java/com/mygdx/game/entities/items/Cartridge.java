package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.GUNSHOT;

public class Cartridge implements Item{

    @Getter @Setter
    private ArrayList<Bullet> bulletsLeft = new ArrayList<>(); //just to count
    private ArrayList<Bullet> bulletsToDraw = new ArrayList<>();  //just to draw the bullets
    @Getter
    private boolean reloading;
//    public static String stringNumbBullets = "";

    public static final int TOTAL_BULLETS_PER_CARTRIDGE = 30;
//    public static String usingRifle = "";
//    public static boolean showingNumBullets;
    public Cartridge(){
        for (int index = 1; index <= TOTAL_BULLETS_PER_CARTRIDGE; index++){
            bulletsLeft.add(new Bullet());
        }
    }

    public void addAndRemove(Bullet bullet){    //este é o método para contar as balas do pente dentro do rifle
        if (!reloading) {
            if (!bulletsLeft.isEmpty()) {
                bulletsLeft.removeLast();
                bulletsToDraw.add(bullet);
                GUNSHOT.play();
            }
        }
    }

    @Override
    public void render(SpriteBatch s) {
        for (Bullet b : bulletsToDraw)
            b.render(s);
    }

    @Override
    public void updateItem() {

    }

    @Override
    public String toString(){
        return "" + (bulletsLeft.size());
    }
}
