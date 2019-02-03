package org.dimdev.rift.mixin.optifine.client;

import org.dimdev.rift.injectedmethods.RiftFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Mixin(targets = "net/optifine/CustomColors", remap = false)
public class MixinCustomColors {
	@Inject(at = @At("RETURN"), method = "getFluidColor(Lnet/minecraft/world/IWorldReader;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/optifine/render/RenderEnv;)I", cancellable = true, remap = true, constraints = "OPTIFINE(1+)")
	private static void fixFluidColours(IWorldReader world, IBlockState state, BlockPos pos, @Coerce Object renderEnv, CallbackInfoReturnable<Integer> callback) {
		IFluidState fluidState = world.getFluidState(pos);

		if (fluidState.getFluid() instanceof RiftFluid) {
			RiftFluid fluid = (RiftFluid) fluidState.getFluid();
			if (fluid.ignoreOptifine()) callback.setReturnValue(fluid.getColorMultiplier(world, pos));
		}
	}
}