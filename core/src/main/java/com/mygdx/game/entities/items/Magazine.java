package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.GUNSHOT;
import static com.mygdx.game.sfx.Sounds.TRIGGER;

public class Magazine implements Item{

    private final int bulletsPerMagazine;
    @Getter @Setter
    private int numberOfBulletsInMagazine;
    @Getter
    @Setter
    private ArrayList<Bullet> bullets = new ArrayList<>();
    public static String stringNumbBullets = "";
    public static boolean showingNumbBullets = false;
    @Getter @Setter
    private int numMagazines;
    @Getter @Setter
    private boolean recharging;

    public Magazine(final int bulletsPerMagazine){
        this.bulletsPerMagazine = bulletsPerMagazine;
        numberOfBulletsInMagazine = bulletsPerMagazine;
        numMagazines = 3;
    }

    public Magazine(final int bulletsPerMagazine, int numMagazines){
        this.bulletsPerMagazine = bulletsPerMagazine;
        numberOfBulletsInMagazine = bulletsPerMagazine;
        this.numMagazines = numMagazines;
    }

    @Override
    public void render(SpriteBatch s) {
        for (Bullet bullet : bullets)
            bullet.render(s);
        if (showingNumbBullets)
            stringNumbBullets = numberOfBulletsInMagazine + "/" + bulletsPerMagazine * numMagazines;
        else
            stringNumbBullets = "";
        if (numberOfBulletsInMagazine <= 0 && numMagazines > 0) {
            recharging = true;
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    if (recharging) {
                        if (numMagazines >= 2)
                            numMagazines--;
                        numberOfBulletsInMagazine = bulletsPerMagazine;
                        recharging = false;
                        TRIGGER.play();
                    }
                }
            }, 1);
        }
    }

    @Override
    public void updateItem() {
//        if (numMagazines > 0 && numberOfBulletsInMagazine <= 0) {
//            numMagazines--;
//            numberOfBulletsInMagazine = bulletsLimitMagazine;
//        }
    }

    public void newBullet(Bullet bullet){
        if (numberOfBulletsInMagazine > 0) {
            numberOfBulletsInMagazine--;
            GUNSHOT.play();
            bullets.add(bullet);
        }
    }
}
