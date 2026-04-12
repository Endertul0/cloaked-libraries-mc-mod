package io.github.endertul.cloaked.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EncodedEntity extends PassiveEntity {
	private static final TrackedData<NbtCompound> STORED_NBT = DataTracker.registerData(EncodedEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
	
	public EncodedEntity(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
	}
	
	public static DefaultAttributeContainer.Builder createEncodedEntityAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0);
	}
	
	@Override
	public void tickMovement() {
		//super.tickMovement();
	}
	
	@Override
	public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}
	
	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		return ! damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY);
	}
	
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(STORED_NBT, new NbtCompound());
	}
	
	public NbtCompound getStoredNbt() {
		return this.dataTracker.get(STORED_NBT);
	}
	
	public void setStoredNbt(NbtCompound entityNBT) {
		this.dataTracker.set(STORED_NBT, entityNBT);
	}
	
	public Optional<EntityType<?>> getStoredEntity() {
		return EntityType.get(this.dataTracker.get(STORED_NBT).getString("id"));
	}
}