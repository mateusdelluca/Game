package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.GUNSHOT;
import static com.mygdx.game.sfx.Sounds.TRIGGER;

public class Magazine implements Item{

    private final int numberBulletsPerMagazine;
    @Getter @Setter
    private int numberBulletsInMagazine;
    @Getter
    @Setter
    private ArrayList<Bullet> bullets = new ArrayList<>();
    public static String stringNumbBullets = "";
    public static boolean showingNumbBullets = false;
    @Getter @Setter
    private int numberMagazines, restOfBullets;
    @Getter @Setter
    private boolean recharging;
    private int total;
    public Magazine(final int numberBulletsPerMagazine){
        this.numberBulletsPerMagazine = numberBulletsPerMagazine;
        numberBulletsInMagazine = numberBulletsPerMagazine;
        numberMagazines = 3;
    }

    public Magazine(final int numberBulletsPerMagazine, int numberMagazines){
        this.numberBulletsPerMagazine = numberBulletsPerMagazine;
        numberBulletsInMagazine = numberBulletsPerMagazine;
        this.numberMagazines = numberMagazines;
    }

    @Override
    public void render(SpriteBatch s) {
        for (Bullet bullet : bullets)
            bullet.render(s);
        if (showingNumbBullets) {
            total = (numberBulletsPerMagazine * numberMagazines) + restOfBullets;
            stringNumbBullets = numberBulletsInMagazine + "/" + total;
        }
        else
            stringNumbBullets = "";
    }

    private void recharging(){
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (recharging) {
                    if (numberMagazines == 0 && restOfBullets > 0)
                        numberBulletsInMagazine = restOfBullets;
                    if (numberMagazines > 0) {
                        if (numberBulletsInMagazine < numberBulletsPerMagazine)
                            restOfBullets += numberBulletsInMagazine;
                        numberBulletsInMagazine = numberBulletsPerMagazine;
                        numberMagazines--;
                    }
                    TRIGGER.play();
                    recharging = false;
                }
            }
        }, 1);
    }

    @Override
    public void updateItem() {
        recharging();
    }

    public void newBullet(Bullet bullet){
        if (numberBulletsInMagazine > 0 && !recharging) {
            numberBulletsInMagazine--;
            GUNSHOT.play();
            bullets.add(bullet);
        }
    }
}
