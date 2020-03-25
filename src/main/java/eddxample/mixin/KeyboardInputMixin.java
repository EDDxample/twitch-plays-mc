package eddxample.mixin;

import eddxample.twitch.MessageController;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {

    public KeyboardInputMixin(GameOptions settings) { this.settings = settings; }

    @Shadow private final GameOptions settings;

    @Inject(method = "tick" , at = @At("HEAD"), cancellable = true)
    public void tick(boolean bl, CallbackInfo ci) {
        this.pressingForward = this.settings.keyForward.isPressed() || MessageController.flags.get("F");
        this.pressingBack = this.settings.keyBack.isPressed() || MessageController.flags.get("B");
        this.pressingLeft = this.settings.keyLeft.isPressed() || MessageController.flags.get("L");
        this.pressingRight = this.settings.keyRight.isPressed() || MessageController.flags.get("R");
        this.movementForward = this.pressingForward == this.pressingBack ? 0.0F : (this.pressingForward ? 1.0F : -1.0F);
        this.movementSideways = this.pressingLeft == this.pressingRight ? 0.0F : (this.pressingLeft ? 1.0F : -1.0F);
        this.jumping = this.settings.keyJump.isPressed() || MessageController.flags.get("J");
        this.sneaking = this.settings.keySneak.isPressed();
        if (bl) {
            this.movementSideways = (float)((double)this.movementSideways * 0.3D);
            this.movementForward = (float)((double)this.movementForward * 0.3D);
        }

        MessageController.resetAll();
        ci.cancel();
    }
}
