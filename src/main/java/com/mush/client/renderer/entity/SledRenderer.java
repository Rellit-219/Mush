package com.mush.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mush.Mush;
import com.mush.client.renderer.entity.model.SledModel;
import com.mush.entity.item.SledEntity;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SledRenderer extends MobRenderer<SledEntity, SledModel> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mush.MODID, "textures/entity/sled/ash_sled.png");
	
	public SledRenderer(EntityRendererManager manager) {
		super(manager, new SledModel(), 0.8F);
		
	}
	
	@Override
	public void render(SledEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		
		if (entityIn.getEntityWorld().getBlockState(entityIn.getPosition()).getBlock() == Blocks.SNOW) {
			
			matrixStackIn.push();
			matrixStackIn.translate(0.0D, 0.1D, 0.0D);
			matrixStackIn.pop();
			
		}
		
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		
	}
	
	@Override
	public ResourceLocation getEntityTexture(SledEntity entity) {
		
		return TEXTURE;
		
	}
	
	public static class RenderFactory implements IRenderFactory<SledEntity> {
		
		@Override
		public SledRenderer createRenderFor(EntityRendererManager manager) {
			
			return new SledRenderer(manager);
			
		}
		
	}
	
}