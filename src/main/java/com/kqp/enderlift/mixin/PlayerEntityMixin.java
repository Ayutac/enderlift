package com.kqp.enderlift.mixin;

import com.kqp.enderlift.event.playeraction.Action;
import com.kqp.enderlift.event.playeraction.PlayerActionEvent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "jump()V")
    public void jump(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) ((Object) this);

        PlayerActionEvent.publishEvent(player, player.world, Action.JUMP);
    }

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void tick(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) ((Object) this);

        PlayerActionEvent.updatePlayerCrouchState(player);
    }
}