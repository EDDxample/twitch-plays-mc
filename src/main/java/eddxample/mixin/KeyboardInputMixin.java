package eddxample.mixin;

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
        this.pressingForward = this.settings.keyForward.isPressed();
        this.pressingBack = this.settings.keyBack.isPressed();
        this.pressingLeft = this.settings.keyLeft.isPressed();
        this.pressingRight = this.settings.keyRight.isPressed();
        this.movementForward = this.pressingForward == this.pressingBack ? 0.0F : (this.pressingForward ? 1.0F : -1.0F);
        this.movementSideways = this.pressingLeft == this.pressingRight ? 0.0F : (this.pressingLeft ? 1.0F : -1.0F);
        this.jumping = this.settings.keyJump.isPressed();
        this.sneaking = this.settings.keySneak.isPressed();
        if (bl) {
            this.movementSideways = (float)((double)this.movementSideways * 0.3D);
            this.movementForward = (float)((double)this.movementForward * 0.3D);
        }
        ci.cancel();
    }
}
