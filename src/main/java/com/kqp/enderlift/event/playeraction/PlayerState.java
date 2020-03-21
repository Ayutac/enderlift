package com.kqp.enderlift.event.playeraction;

public class PlayerState {
    public boolean crouching = false;
    public double velY = 0.0D;

    public PlayerState(boolean crouching, double velY) {
        this.crouching = crouching;
        this.velY = velY;
    }
}