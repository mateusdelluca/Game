package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.GUNSHOT;

public class Magazine implements Item{

    private final int bulletsLimitMagazine;
    @Getter @Setter
    private int numberOfBulletsInMagazine;
    @Getter
    @Setter
    private ArrayList<Bullet> bullets = new ArrayList<>();
    public static String stringNumbBullets = "";
    public static boolean showingNumbBullets = false;
    @Getter @Setter
    private int numMagazines = 3;
    @Getter @Setter
    private boolean recharging;

    public Magazine(final int bulletsLimitMagazine){
        this.bulletsLimitMagazine = bulletsLimitMagazine;
        numberOfBulletsInMagazine = bulletsLimitMagazine;
    }

    @Override
    public void render(SpriteBatch s) {
        for (Bullet bullet : bullets)
            bullet.render(s);
        if (showingNumbBullets)
            stringNumbBullets = numberOfBulletsInMagazine + "/" + bulletsLimitMagazine * numMagazines;
        else
            stringNumbBullets = "";
        if (numberOfBulletsInMagazine <= 0 && numMagazines > 0 && numMagazines < 3) {
            recharging = true;
            Timer timer = new Timer();
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    if (recharging) {
                        numMagazines--;
                        numberOfBulletsInMagazine = bulletsLimitMagazine;
                        recharging = false;
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
