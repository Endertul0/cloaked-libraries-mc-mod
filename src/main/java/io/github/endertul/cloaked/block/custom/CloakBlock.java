package io.github.endertul.cloaked.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CloakBlock extends BlockWithEntity {
	public static final MapCodec<CloakBlock> CODEC = createCodec(CloakBlock::new);
	
	public CloakBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}
	
	//TODO: make this work lol
	//@Override
	//public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
	//	return ModBlocks.CLOAK.asItem().getDefaultStack();
	//}
	
	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CloakBlockEntity(pos, state);
	}
}
