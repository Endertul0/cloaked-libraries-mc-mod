package io.github.endertul.cloaked.block;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.block.custom.CloakBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
	
	public static final Block CLOAK = registerBlock("cloak",
		new CloakBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.SCULK_SENSOR)));
	
	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block);
		return Registry.register(Registries.BLOCK, Identifier.of(Cloaked.MOD_ID, name), block);
	}
	
	private static Item registerBlockItem(String name, Block block) {
		return Registry.register(Registries.ITEM, Identifier.of(Cloaked.MOD_ID, name),
			new BlockItem(block, new Item.Settings()));
	}
	
	public static void registerModBlocks() {
		Cloaked.LOGGER.info("Registering mod blocks for " + Cloaked.MOD_ID);
	}
}
