package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.GUNSHOT;

public class Cartridge implements Item{

    @Getter @Setter
    private ArrayList<Bullet> bulletsLeft = new ArrayList<>(); //just to count
    @Getter @Setter
    private ArrayList<Bullet> bulletsToDraw = new ArrayList<>();  //just to draw the bullets

    public static ArrayList<Bullet> bullets = new ArrayList<>();

    public static final int MAX_ROUNDS = 20, MAX_ROUNDS2 = 30;

    @Getter @Setter
    private int accumulated = 0;

    public Cartridge(){
        bulletsLeft = init(MAX_ROUNDS);
    }

    public ArrayList<Bullet> init(int max_rounds){
        if (!bulletsLeft.isEmpty())
            bulletsLeft.clear();
        for (int index = 1; index <= max_rounds; index++){
            bulletsLeft.add(new Bullet());
        }
        return bulletsLeft;
    }

    public void addAndRemove(Bullet bullet, Rifle rifle){    //este é o método para contar as balas do pente dentro do rifle
        if (!rifle.isReloading()) {
            if (!bulletsLeft.isEmpty()) {
                bulletsLeft.removeLast();
                bulletsToDraw.add(bullet);
                if (accumulated < MAX_ROUNDS)
                    accumulated++;
                GUNSHOT.play();
                bullets.add(bullet);
            }
        }
    }

    @Override
    public void render(SpriteBatch s) {
        for (Bullet b : bulletsToDraw) {
            b.render(s);
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
        for (Bullet b : bulletsLeft)
            b.update();
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

    @Override
    public String toString(){
        return "" + (bulletsLeft.size());
    }
}
