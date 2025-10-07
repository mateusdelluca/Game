package com.mygdx.game.entities;

import com.mygdx.game.images.PowerBar;
import com.mygdx.game.screens.Stats;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

import static com.mygdx.game.images.PowerBar.hit;
import static com.mygdx.game.screens.Stats.*;

public class Character_Features {

    @Getter @Setter
    private float hp = PowerBar.hp_0; //TODO: arrumar o hp da classe PowerBar colocando nesta classe e usar para outros personagens
    @Getter @Setter
    private float attack = 1f, def = 1, jumpingStrength;
    @Getter @Setter
    private int[] stats_values = new int[STATS_SIZE];

    //TODO: por enquanto o enemy_atack fica nesta classe, mas implementar para cada personagem
    private float enemy_attack = 2f;

    public Character_Features(){
        Arrays.fill(stats_values, 1);
    }

    public void update(){
        PowerBar.maxHP = 150f + (6f * stats_values[VIT]);
        PowerBar.maxPower = 50f + (5f* stats_values[WSD]);
        Boy.attack = 1f + (0.5f * stats_values[STR]);
        def = 1f + (0.5f * stats_values[VIT]);
        if (hit)
            hp += (enemy_attack >= def/2f ? -Math.abs(enemy_attack - def/2f) : -Math.abs(Math.min((enemy_attack - def/4f), 5f)));
        jumpingStrength = 5000f + (200 * stats_values[STR]);
    }
}
