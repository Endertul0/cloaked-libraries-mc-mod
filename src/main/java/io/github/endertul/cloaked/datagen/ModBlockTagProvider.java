package io.github.endertul.cloaked.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	protected void configure(RegistryWrapper.WrapperLookup arg) {
		//getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
		//.add(ModBlocks.CLOAK);
		
		//getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
		//.add(ModBlocks.RUBY_BLOCK);
		
		//getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
		//.add(ModBlocks.RAW_RUBY_BLOCK)
		//.add(ModBlocks.RUBY_ORE);
		
		//getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
		//.add(ModBlocks.DEEPSLATE_RUBY_ORE);
	}
}