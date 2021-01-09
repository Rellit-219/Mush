package com.mush.item;

import com.mush.entity.item.SledEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class SledItem extends Item {
	
	public SledItem(Properties properties) {
		super(properties);
		
	}

	public ActionResultType onItemUse(ItemUseContext context) {
		
		ItemStack stack = context.getItem();
		
		if (!context.getWorld().isRemote()) {
			
			SledEntity sled = new SledEntity(context.getWorld());
			
			sled.setPosition(context.getHitVec().getX(), context.getHitVec().getY(), context.getHitVec().getZ());
			
			sled.rotationYaw = context.getPlayer().rotationYaw;
			sled.prevRotationYaw = sled.rotationYaw;
			sled.rotationPitch = context.getPlayer().rotationPitch * 0.5F;
			sled.publicSetRotation(sled.rotationYaw, sled.rotationPitch);
			sled.renderYawOffset = sled.rotationYaw;
			sled.rotationYawHead = sled.renderYawOffset;
			
			context.getWorld().addEntity(sled);
			
			if (!context.getPlayer().isCreative()) {
				
				stack.shrink(1);
				
			}
			
		}
		
		return ActionResultType.func_233537_a_(context.getWorld().isRemote);
		
	}
	
}