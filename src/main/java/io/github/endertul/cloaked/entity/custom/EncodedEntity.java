package io.github.endertul.cloaked.entity.custom;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.endertul.cloaked.Cloaked;
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
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EncodedEntity extends PassiveEntity {
	private static final TrackedData<String> STORED_ENTITY = DataTracker.registerData(EncodedEntity.class, TrackedDataHandlerRegistry.STRING);
	private static final TrackedData<String> STORED_NBT_STRING = DataTracker.registerData(EncodedEntity.class, TrackedDataHandlerRegistry.STRING);
	
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
		this.dataTracker.startTracking(STORED_ENTITY, EntityType.SQUID.getTranslationKey());
		this.dataTracker.startTracking(STORED_NBT_STRING, "{}");
	}
	
	public NbtCompound getStoredNbt() {
		try {
			return StringNbtReader.parse(this.dataTracker.get(STORED_NBT_STRING));
		} catch (CommandSyntaxException e) {
			Cloaked.LOGGER.error(e.getMessage());
		}
		
		return new NbtCompound();
	}
	
	public void setStoredNbt(String s) {
		this.dataTracker.set(STORED_NBT_STRING, s);
	}
	
	public EntityType<?> getStoredEntity() {
		String thisType = this.dataTracker.get(STORED_ENTITY);
		for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
			if (Objects.equals(entityType.getTranslationKey(), thisType)) {
				return entityType;
			}
		}
		return EntityType.SQUID;
	}
	
	public void setStoredEntity(EntityType<?> eType) {
		this.dataTracker.set(STORED_ENTITY, eType.getTranslationKey());
	}
}