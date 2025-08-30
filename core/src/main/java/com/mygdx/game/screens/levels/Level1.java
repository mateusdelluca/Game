package com.mygdx.game.screens.levels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Monster1;

public class Level1 extends Level{
    public Level1() throws Exception {

        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(500, 400),     Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(800, 400),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1300, 400),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1600, 400),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1850, 500),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2650, 600),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(3300, 600),    Monster1.class.getSimpleName() + monsters1.size()));
    }
}
