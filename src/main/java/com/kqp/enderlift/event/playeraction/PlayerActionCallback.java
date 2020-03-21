package com.kqp.enderlift.event.playeraction;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface PlayerActionCallback {
    void interact(PlayerEntity player, World world, Action action, boolean newState);
}