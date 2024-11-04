//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game.sfx;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.Serializable;

public class Audio implements Serializable {
    private Clip clip;

    public Audio(String endereco) {
        try {
            File file = new File(endereco);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void play() {
        if (!this.clip.isRunning()) {
            this.clip.setFramePosition(0);
            this.clip.start();
        }

    }

    public void play2() {
        this.clip.setFramePosition(0);
        this.clip.setMicrosecondPosition(0L);
        this.clip.start();
    }

    public void loopClip(int secondsPosition) {
        this.clip.start();
        this.clip.setMicrosecondPosition((long)secondsPosition);
    }

    public Clip getClip() {
        return this.clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public void playTime(long sound_time) {
        if (!this.clip.isRunning()) {
            this.clip.start();
            this.clip.setMicrosecondPosition(sound_time);
        }

    }
}
