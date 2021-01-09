package com.mush.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mush.client.renderer.entity.layers.SledWolfCollarLayer;
import com.mush.client.renderer.entity.layers.SledWolfHarnessLayer;
import com.mush.entity.SledWolfEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class SledWolfRenderer extends MobRenderer<SledWolfEntity, WolfModel<SledWolfEntity>> {
	
	private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static final ResourceLocation TAMED_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
	private static final ResourceLocation ANGRY_WOLF_TEXTURES = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
	
	public SledWolfRenderer(EntityRendererManager manager) {
		super(manager, new WolfModel<>(), 0.5F);
		this.addLayer(new SledWolfCollarLayer(this));
		this.addLayer(new SledWolfHarnessLayer(this));
		
	}
	
	protected float handleRotationFloat(SledWolfEntity livingBase, float partialTicks) {
		
		return livingBase.getTailRotation();
		
	}
	
	public void render(SledWolfEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		
		if (entityIn.isWolfWet()) {
			
			float f = entityIn.getShadingWhileWet(partialTicks);
			this.entityModel.setTint(f, f, f);
			
		}
		
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		
		if (entityIn.isWolfWet()) {
			
			this.entityModel.setTint(1.0F, 1.0F, 1.0F);
			
		}
		
	}
	
	@Override
	public ResourceLocation getEntityTexture(SledWolfEntity entity) {
		
		if (entity.isTamed()) {
			
			return TAMED_WOLF_TEXTURES;
			
		} else {
			
			return entity.isAggressive() ? ANGRY_WOLF_TEXTURES : WOLF_TEXTURES;
			
		}
		
	}
	
	public static class RenderFactory implements IRenderFactory<SledWolfEntity> {
		
		@Override
		public EntityRenderer<? super SledWolfEntity> createRenderFor(EntityRendererManager manager) {
			
			return new SledWolfRenderer(manager);
			
		}
		
	}
	
}