package io.github.endertul.cloaked.item;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.item.custom.DecloakerItem;
import io.github.endertul.cloaked.item.custom.QuickCloakerItem;
import io.github.endertul.cloaked.item.custom.SlowCloakerItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
	public static Item DECLOAKER = registerItem("decloaker",
		new DecloakerItem(new FabricItemSettings().rarity(Rarity.EPIC)));
	public static Item SLOW_CLOAKER = registerItem("slow_cloaker",
		new SlowCloakerItem(new FabricItemSettings().rarity(Rarity.EPIC)));
	public static Item QUICK_CLOAKER = registerItem("quick_cloaker",
		new QuickCloakerItem(new FabricItemSettings().rarity(Rarity.EPIC)));
	
	private static void addItemsToFunctionalItemGroup(FabricItemGroupEntries entries) {
		entries.add(DECLOAKER);
		entries.add(SLOW_CLOAKER);
		entries.add(QUICK_CLOAKER);
	}
	
	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(Cloaked.MOD_ID, name), item);
	}
	
	public static void registerModItems() {
		Cloaked.LOGGER.info("Registering mod items for " + Cloaked.MOD_ID);
		
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItems::addItemsToFunctionalItemGroup);
	}
}
