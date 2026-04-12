package io.github.endertul.cloaked.block.custom;

import io.github.endertul.cloaked.block.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;

public class CloakBlockEntity extends BlockEntity {
	private BlockState storedState;
	private Block storedBlock;
	private NbtCompound storedNbt;
	
	public CloakBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CLOAK_BLOCK_ENTITY, pos, state);
	}
	
	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		if (nbt.contains("sstate") && nbt.contains("snbt")) {
			this.storedState = (BlockState) nbt.get("sstate");
			this.storedBlock = this.storedState.getBlock();
			this.storedNbt = (NbtCompound) nbt.get("snbt");
		} else {
			nbt.put("sstate", NbtHelper.fromBlockState(Blocks.STONE.getDefaultState()));
			this.storedState = (BlockState) nbt.get("sstate");
			this.storedBlock = this.storedState.getBlock();
			this.storedNbt = new NbtCompound();
		}
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.put("sstate", NbtHelper.fromBlockState(this.storedState));
		nbt.put("snbt", storedNbt);
	}
	
	public Block getStoredBlock() {
		return this.storedBlock;
	}
	
	public BlockState getStoredBlockState() {
		return this.storedState;
	}
	
	public void setStoredBlockState(BlockState state) {
		this.storedState = state;
	}
	
	public NbtCompound getStoredNbt() {
		return this.storedNbt;
	}
	
	public void setStoredNbt(NbtCompound nbt) {
		this.storedNbt = nbt;
	}
}
