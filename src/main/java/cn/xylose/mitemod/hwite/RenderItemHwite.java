package cn.xylose.mitemod.hwite;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;

public class RenderItemHwite extends Render {

    private RenderBlocks itemRenderBlocks = new RenderBlocks();
    public boolean renderWithColor = true;
    public float zLevel;

    public void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5) {
        int var6 = par3ItemStack.itemID;
        int var7 = par3ItemStack.getItemSubtype();
        Object var8 = par3ItemStack.getIconIndex();
        float var17;
        int var18;
        float var12;
        float var13;
        if (par3ItemStack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.blocksList[var6].getRenderType())) {
            par2TextureManager.bindTexture(TextureMap.locationBlocksTexture);
            Block var15 = Block.blocksList[var6];
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(par4 - 2), (float)(par5 + 3), -3.0F + this.zLevel);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1.0F);
            GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            var18 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, 0);
            var17 = (float)(var18 >> 16 & 255) / 255.0F;
            var12 = (float)(var18 >> 8 & 255) / 255.0F;
            var13 = (float)(var18 & 255) / 255.0F;
            if (this.renderWithColor) {
                GL11.glColor4f(var17, var12, var13, 1.0F);
            }

            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            this.itemRenderBlocks.useInventoryTint = this.renderWithColor;
            this.itemRenderBlocks.renderBlockAsItem(var15, var7, 1.0F);
            this.itemRenderBlocks.useInventoryTint = true;
            GL11.glPopMatrix();
        } else if (Item.itemsList[var6].requiresMultipleRenderPasses()) {
            par2TextureManager.bindTexture(TextureMap.locationItemsTexture);

            for(int var9 = 0; var9 <= 1; ++var9) {
                Icon var10 = Item.itemsList[var6].getIconFromSubtypeForRenderPass(var7, var9);
                int var11 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, var9);
                var12 = (float)(var11 >> 16 & 255) / 255.0F;
                var13 = (float)(var11 >> 8 & 255) / 255.0F;
                float var14 = (float)(var11 & 255) / 255.0F;
                if (this.renderWithColor) {
                    GL11.glColor4f(var12, var13, var14, 1.0F);
                }

                this.renderIcon(par4, par5, var10, 16, 16);
            }

        } else {
            ResourceLocation var16 = par2TextureManager.getResourceLocation(par3ItemStack.getItemSpriteNumber());
            par2TextureManager.bindTexture(var16);
            if (var8 == null) {
                var8 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(var16)).getAtlasSprite("missingno");
            }

            var18 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, 0);
            var17 = (float)(var18 >> 16 & 255) / 255.0F;
            var12 = (float)(var18 >> 8 & 255) / 255.0F;
            var13 = (float)(var18 & 255) / 255.0F;
            if (this.renderWithColor) {
                GL11.glColor4f(var17, var12, var13, 1.0F);
            }

            this.renderIcon(par4, par5, (Icon)var8, 16, 16);
        }

    }

    public void renderItemAndEffectIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5) {
        if (par3ItemStack != null) {
            this.renderItemIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5);
            if (par3ItemStack.hasEffect()) {
                GL11.glDepthFunc(516);
                GL11.glDepthMask(false);
                this.zLevel -= 50.0F;
                GL11.glBlendFunc(774, 774);
                GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
                GL11.glDepthMask(true);
                this.zLevel += 50.0F;
                GL11.glDepthFunc(515);
            }
        }

    }

    public void renderIcon(int par1, int par2, Icon par3Icon, int par4, int par5) {
        Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV((double)(par1 + 0), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMaxV());
        var6.addVertexWithUV((double)(par1 + par4), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMaxV());
        var6.addVertexWithUV((double)(par1 + par4), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMinV());
        var6.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMinV());
        var6.draw();
    }

    @Override
    public void doRender(Entity entity, double d, double e, double f, float g, float h) {
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
