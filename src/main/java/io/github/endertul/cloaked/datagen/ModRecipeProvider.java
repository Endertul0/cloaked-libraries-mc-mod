package io.github.endertul.cloaked.datagen;

import io.github.endertul.cloaked.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
	public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	public void generate(RecipeExporter exporter) {
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DECLOAKER, 1)
			.pattern("SES")
			.pattern("EYE")
			.pattern("SES")
			.input('S', Items.END_STONE)
			.input('E', Items.ENDER_PEARL)
			.input('Y', Items.ENDER_EYE)
			.criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
			.offerTo(exporter, Identifier.of(getRecipeName(ModItems.DECLOAKER)));
	}
}