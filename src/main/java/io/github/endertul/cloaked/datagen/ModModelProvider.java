package io.github.endertul.cloaked.datagen;

import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CLOAK);
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.DECLOAKER, Models.GENERATED);
		itemModelGenerator.register(ModItems.SLOW_CLOAKER, Models.GENERATED);
		itemModelGenerator.register(ModItems.QUICK_CLOAKER, Models.GENERATED);
	}
}