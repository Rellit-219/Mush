package com.mush.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mush.Mush;
import com.mush.entity.SledWolfEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SledWolfHarnessLayer extends LayerRenderer<SledWolfEntity, WolfModel<SledWolfEntity>> {
	
	private static final ResourceLocation WOLF_HARNESS = new ResourceLocation(Mush.MODID, "textures/entity/sled_wolf/harness.png");
	
	public SledWolfHarnessLayer(IEntityRenderer<SledWolfEntity, WolfModel<SledWolfEntity>> p_i50926_1_) {
		super(p_i50926_1_);
		
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SledWolfEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		
		if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible()) {
			
			renderCutoutModel(this.getEntityModel(), WOLF_HARNESS, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
			
		}
		
	}
	
}