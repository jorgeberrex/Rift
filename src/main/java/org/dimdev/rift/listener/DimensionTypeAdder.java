package org.dimdev.rift.listener;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

import java.util.function.Function;
import java.util.function.Supplier;

public interface DimensionTypeAdder {
    static DimensionType addDimensionType(int id, ResourceLocation name, String suffix, Supplier<? extends Dimension> dimensionSupplier) {
    	return DimensionType.register(name.toString(), new DimensionType(id, suffix, name.getPath(), dimensionSupplier));
    }

    static DimensionType addDimensionType(int id, ResourceLocation name, String suffix, Supplier<? extends Dimension> dimensionSupplier, Function<WorldServer, ? extends Teleporter> teleporter) {
    	class CustomTeleporterDimensionType extends DimensionType implements TeleporterProvider {
			public CustomTeleporterDimensionType() {
				super(id, suffix, name.getPath(), dimensionSupplier);
			}

			@Override
			public Teleporter makeTeleporter(WorldServer world) {
				return teleporter.apply(world);
			}
    	}
    	return DimensionType.register(name.toString(), new CustomTeleporterDimensionType());
    }

    void registerDimensionTypes();

    /**
     * Implement this on your {@link DimensionType} to allow a custom {@link Teleporter} for {@link WorldServerMulti}
     * 
     * @author Chocohead
     */
    interface TeleporterProvider {
    	Teleporter makeTeleporter(WorldServer world);
    }
}
