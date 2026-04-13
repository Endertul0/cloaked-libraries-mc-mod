package io.github.endertul.cloaked.datagen;

import io.github.endertul.cloaked.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
	//private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
	//        ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.NETHER_RUBY_ORE, ModBlocks.END_STONE_RUBY_ORE);
	
	public ModRecipeProvider(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generate(RecipeExporter exporter) {
		//offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
		//        0.7f, 200, "ruby");
		//offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
		//        0.7f, 100, "ruby");
		
		//offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.DECORATIONS,
		//        ModBlocks.RUBY_BLOCK);
		
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DECLOAKER, 1)
			.pattern("SES")
			.pattern("EYE")
			.pattern("SES")
			.input('S', Items.END_STONE)
			.input('E', Items.ENDER_PEARL)
			.input('Y', Items.ENDER_EYE)
			.criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
			.offerTo(exporter, new Identifier(getRecipeName(ModItems.DECLOAKER)));
	}
}