package com.kqp.enderlift.event.playeraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class PlayerActionEvent {
    private static final List<PlayerActionCallback> LISTENERS = new ArrayList<PlayerActionCallback>();

    private static Map<PlayerEntity, Boolean> CROUCH_MAP = new HashMap<PlayerEntity, Boolean>();

    public static void updatePlayerCrouchState(PlayerEntity player) {
        Boolean prev = CROUCH_MAP.get(player);

        if (prev != null) {
            if (prev != player.isSneaking() && player.isSneaking()) {
                publishEvent(player, player.world, Action.CROUCH);
            }
        }

        CROUCH_MAP.put(player, player.isSneaking());
    }

    public static void publishEvent(PlayerEntity player, World world, Action action) {
        for (PlayerActionCallback listener : LISTENERS) {
            listener.interact(player, world, action);
        }
    }

    public static void register(PlayerActionCallback callback) {
        LISTENERS.add(callback);
    }
}