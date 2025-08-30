package com.mygdx.game.screens.levels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Monster1;

public class Level3 extends Level{
    public Level3() throws Exception {

        monsters1.clear();

        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(640, 6000 - 2000),     Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(480, 6000 - 2000),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2080, 6000 - 360),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(3200, 6000 - 1080),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(4800, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2680, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(240, 6000 - 5880),    Monster1.class.getSimpleName() + monsters1.size()));

    }
}
