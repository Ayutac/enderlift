package com.kqp.enderlift.event.playeraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class PlayerActionEvent {
    private static final List<PlayerActionCallback> LISTENERS = new ArrayList();
 
    private static final Map<PlayerEntity, PlayerState> PLAYER_STATES = new HashMap();

    public static void onWorldTick(World world) {
        if (!world.isClient) {
            List<ServerPlayerEntity> players = world.getServer().getPlayerManager().getPlayerList();

            for (ServerPlayerEntity player : players) {
                PlayerState prev = PLAYER_STATES.get(player);
                PlayerState curr = new PlayerState(player.isSneaking(), player.getVelocity().getY());

                if (prev != null) {
                    if (prev.crouching != curr.crouching) {
                        publishEvent(player, world, Action.CROUCH, curr.crouching);
                    }

                    if (curr.velY > 0.0D && curr.velY > prev.velY) {
                        publishEvent(player, world, Action.JUMP, true);
                    }
                }

                PLAYER_STATES.put(player, curr);
            }
        }
    }

    private static void publishEvent(PlayerEntity player, World world, Action action, boolean newState) {
        for (PlayerActionCallback listener : LISTENERS) {
            listener.interact(player, world, action, newState);
        }
    }

    public static void register(PlayerActionCallback callback) {
        LISTENERS.add(callback);
    }
}