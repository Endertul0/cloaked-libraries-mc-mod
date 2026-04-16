package io.github.endertul.cloaked.item;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
	public static final ItemGroup CLOAKED_TAB = Registry.register(Registries.ITEM_GROUP,
		Identifier.of(Cloaked.MOD_ID, "cloaked_tab"),
		FabricItemGroup.builder().displayName(Text.translatable("itemgroup.cloaked_tab"))
			.icon(() -> new ItemStack(ModItems.DECLOAKER)).entries((displayContext, entries) -> {
				entries.add(ModBlocks.CLOAK);
				entries.add(ModItems.DECLOAKER);
				entries.add(ModItems.SLOW_CLOAKER);
				entries.add(ModItems.QUICK_CLOAKER);
			}).build());
	
	public static void registerItemGroups() {
		Cloaked.LOGGER.info("Registering item group for " + Cloaked.MOD_ID);
	}
}
