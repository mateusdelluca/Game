package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.images.PowerBar;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Arrays;

import static com.mygdx.game.entities.Player.lvlUP;
import static com.mygdx.game.images.PowerBar.hit;
import static com.mygdx.game.screens.Stats.*;

public class Character_Features {

    @Getter @Setter
    private float hp = PowerBar.hp_0; //TODO: arrumar o hp da classe PowerBar colocando nesta classe e usar para outros personagens
    @Getter @Setter
    private float attack = 1f, def = 1, jumpingStrength = 10_000f, maxVelocityWalking = 10f;
    @Getter @Setter
    private int[] stats_values = new int[STATS_SIZE];
    public static float velocityX = 1000f, laserDistance = 1000f;

    //TODO: por enquanto o enemy_atack fica nesta classe, mas implementar para cada personagem
    private float enemy_attack = 10f;
    @Getter
    private int laserDamage = 1, damage;
    @Getter
    private float powerSpent = PowerBar.power;
    public static float recoveryPowerGreenPotion = 30f, recoveryPowerBluePotion = 10f, recoveryPowerRedPotion = 10f;

    public Character_Features(){
        Arrays.fill(stats_values, 1);
        init();
    }

    public void drawDamage(SpriteBatch spriteBatch, BitmapFont font, Body body){
        font.draw(spriteBatch, "" + damage, body.getPosition().x, body.getPosition().y);
    }

    public void update(){
        PowerBar.maxSP = 40f + (4 * stats_values[WSD]);
        PowerBar.maxHP = 150f + (6f * stats_values[VIT]);
        PowerBar.maxPower = 50f + (5f* stats_values[WSD]);
        attack = 1f + (0.5f * stats_values[STR]);
        velocityX = 850f + (150 * stats_values[AGI]);
        def = 1f + (0.5f * stats_values[VIT]);
        jumpingStrength = 10_000f + (200f * stats_values[STR]);
        laserDistance = 8_000f + (3000f * stats_values[DEX]);
        laserDamage = (int) (1 + (0.5f * stats_values[DEX]));
        recoveryPowerGreenPotion = 10f + stats_values[WSD]/2f;
        powerSpent = 2f/(stats_values[WSD]/2f);
        recoveryPowerBluePotion = 10f + stats_values[WSD] / 4f;
        recoveryPowerRedPotion = 10f+ stats_values[VIT] / 4f;
        damage = (int) (enemy_attack >= def ? -Math.abs(Math.min(enemy_attack - (def / 2f), enemy_attack)) : -Math.abs(Math.min((enemy_attack - (def / 4f)), enemy_attack)));
        if (hit) {
            hp += damage;
            hit = false;
        }
        if (!lvlUP && exp_Points >= 25) {
            points += 10;
            base_level += 1;
            exp_Points = 1;
//            EAGLE.play();
            lvlUP = true;
        }
    }

    public void init(){
        PowerBar.maxSP = 40f + (4 * stats_values[WSD]);
        PowerBar.maxHP = 150f + (6f * stats_values[VIT]);
        PowerBar.maxPower = 50f + (5f* stats_values[WSD]);
        attack = 1f + (0.5f * stats_values[STR]);
        velocityX = 850f + (150 * stats_values[AGI]);
        def = 1f + (0.5f * stats_values[VIT]);
        jumpingStrength = 5000f + (200f * stats_values[STR]);
        laserDistance = 8_000f + (3000f * stats_values[DEX]);
        laserDamage = (int) (1 + (0.5 * stats_values[DEX]));
        recoveryPowerGreenPotion = 10f + stats_values[WSD]/2f;
        powerSpent = 2f/(stats_values[WSD]/2f);
        recoveryPowerBluePotion = 10f + stats_values[WSD] / 4f;
        recoveryPowerRedPotion = 10f+ stats_values[VIT] / 4f;
        damage = (int) (enemy_attack >= def ? -Math.abs(Math.min(enemy_attack - (def / 2f), enemy_attack)) : -Math.abs(Math.min((enemy_attack - (def / 4f)), enemy_attack)));
        if (!lvlUP && exp_Points >= 25) {
            points += 10;
            base_level += 1;
            exp_Points = 1;
//            EAGLE.play();
            lvlUP = true;
        }
    }
}
