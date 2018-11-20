package org.dimdev.testmod;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * A custom teleporter is technically optional, it depends on how your custom dimension is entered to whether one is likely necessary
 */
public class TestTeleporter extends Teleporter {
	public TestTeleporter(WorldServer world) {
		super(world);
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {
		int entityX = MathHelper.floor(entity.posX);
        int entityY = MathHelper.floor(entity.posY) - 1;
        int entityZ = MathHelper.floor(entity.posZ);

		for (int z = -2; z <= 2; ++z) {
			for (int x = -2; x <= 2; ++x) {
				for (int y = -1; y < 3; ++y) {
					int platformX = entityX + x * 1 + z * 0;
					int platformY = entityY + y;
					int platformZ = entityZ + x * 0 - z * 1;
					boolean floor = y < 0;
					world.setBlockState(new BlockPos(platformX, platformY, platformZ), floor ? Blocks.GREEN_CONCRETE.getDefaultState() : Blocks.AIR.getDefaultState());
				}
			}
		}

        entity.setLocationAndAngles(entityX, entityY, entityZ, entity.rotationYaw, 0F);
        entity.motionX = 0D;
        entity.motionY = 0D;
        entity.motionZ = 0D;
	}
}