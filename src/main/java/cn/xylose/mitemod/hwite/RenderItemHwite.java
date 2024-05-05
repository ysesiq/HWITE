package cn.xylose.mitemod.hwite;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class RenderItemHwite extends Render {

    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private RenderBlocks itemRenderBlocks = new RenderBlocks();
    private Random random = new Random();
    public boolean renderWithColor = true;
    public float zLevel;
    public static boolean renderInFrame;

    public void doRenderItem(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9) {
        this.bindEntityTexture(par1EntityItem);
        this.random.setSeed(187L);
        ItemStack var10 = par1EntityItem.getEntityItem();
        if (var10.getItem() != null) {
            GL11.glPushMatrix();
            float var11 = MathHelper.sin(((float)par1EntityItem.age + par9) / 10.0F + par1EntityItem.hoverStart) * 0.1F + 0.1F;
            float var12 = (((float)par1EntityItem.age + par9) / 20.0F + par1EntityItem.hoverStart) * 57.295776F;
            byte var13 = 1;
            if (par1EntityItem.getEntityItem().stackSize > 1) {
                var13 = 2;
            }

            if (par1EntityItem.getEntityItem().stackSize > 5) {
                var13 = 3;
            }

            if (par1EntityItem.getEntityItem().stackSize > 20) {
                var13 = 4;
            }

            if (par1EntityItem.getEntityItem().stackSize > 40) {
                var13 = 5;
            }

            GL11.glTranslatef((float)par2, (float)par4 + var11, (float)par6);
            GL11.glEnable(32826);
            float var19;
            float var18;
            float var20;
            int var26;
            int var15;
            if (var10.getItemSpriteNumber() == 0 && var10.itemID < Block.blocksList.length && Block.blocksList[var10.itemID] != null && RenderBlocks.renderItemIn3d(Block.blocksList[var10.itemID].getRenderType())) {
                Block var21 = Block.blocksList[var10.itemID];
                GL11.glRotatef(var12, 0.0F, 1.0F, 0.0F);
                if (renderInFrame) {
                    GL11.glScalef(1.25F, 1.25F, 1.25F);
                    GL11.glTranslatef(0.0F, 0.05F, 0.0F);
                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                float var25 = 0.25F;
                var15 = var21.getRenderType();
                if (var15 == 1 || var15 == 19 || var15 == 12 || var15 == 2) {
                    var25 = 0.5F;
                }

                GL11.glScalef(var25, var25, var25);

                for(var26 = 0; var26 < var13; ++var26) {
                    GL11.glPushMatrix();
                    if (var26 > 0) {
                        var18 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
                        var19 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
                        var20 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
                        GL11.glTranslatef(var18, var19, var20);
                    }

                    var18 = 1.0F;
                    this.itemRenderBlocks.renderBlockAsItem(var21, var10.getItemSubtype(), var18);
                    GL11.glPopMatrix();
                }
            } else {
                float var16;
                if (var10.getItemSpriteNumber() == 1 && var10.getItem().requiresMultipleRenderPasses()) {
                    if (renderInFrame) {
                        GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                        GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                    } else {
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                    }

                    for(int var23 = 0; var23 <= 1; ++var23) {
                        this.random.setSeed(187L);
                        Icon var22 = var10.getItem().getIconFromSubtypeForRenderPass(var10.getItemSubtype(), var23);
                        var16 = 1.0F;
                        if (this.renderWithColor) {
                            var26 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, var23);
                            var18 = (float)(var26 >> 16 & 255) / 255.0F;
                            var19 = (float)(var26 >> 8 & 255) / 255.0F;
                            var20 = (float)(var26 & 255) / 255.0F;
                            GL11.glColor4f(var18 * var16, var19 * var16, var20 * var16, 1.0F);
                            this.renderDroppedItem(par1EntityItem, var22, var13, par9, var18 * var16, var19 * var16, var20 * var16);
                        } else {
                            this.renderDroppedItem(par1EntityItem, var22, var13, par9, 1.0F, 1.0F, 1.0F);
                        }
                    }
                } else {
                    if (renderInFrame) {
                        GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                        GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                    } else {
                        GL11.glScalef(0.5F, 0.5F, 0.5F);
                    }

                    Icon var14 = var10.getIconIndex();
                    if (this.renderWithColor) {
                        var15 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, 0);
                        var16 = (float)(var15 >> 16 & 255) / 255.0F;
                        float var17 = (float)(var15 >> 8 & 255) / 255.0F;
                        var18 = (float)(var15 & 255) / 255.0F;
                        var19 = 1.0F;
                        this.renderDroppedItem(par1EntityItem, var14, var13, par9, var16 * var19, var17 * var19, var18 * var19);
                    } else {
                        this.renderDroppedItem(par1EntityItem, var14, var13, par9, 1.0F, 1.0F, 1.0F);
                    }
                }
            }

            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }

    }

    public void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, Block par3Block, int par4, int par5) {
        int var6 = par3Block.blockID;
        int var7 = par3Block.getBlockSubtype(0);
        Object var8 = par3Block.getIcon(1, 2);
        if (RenderBlocks.renderItemIn3d(Block.blocksList[var6].getRenderType())) {
            par2TextureManager.bindTexture(TextureMap.locationBlocksTexture);
            Block var15 = Block.blocksList[var6];
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(par4 - 2), (float)(par5 + 3), -3.0F + this.zLevel);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1.0F);
            GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            this.itemRenderBlocks.useInventoryTint = this.renderWithColor;
            this.itemRenderBlocks.renderBlockAsItem(var15, var7, 1.0F);
            this.itemRenderBlocks.useInventoryTint = true;
            GL11.glPopMatrix();
        } else if (Item.itemsList[var6].requiresMultipleRenderPasses()) {
            GL11.glDisable(2896);
            par2TextureManager.bindTexture(TextureMap.locationItemsTexture);

            for(int var9 = 0; var9 <= 1; ++var9) {
                Icon var10 = Item.itemsList[var6].getIconFromSubtypeForRenderPass(var7, var9);

                this.renderIcon(par4, par5, var10, 16, 16);
            }

            GL11.glEnable(2896);
        } else {
            GL11.glDisable(2896);

            this.renderIcon(par4, par5, (Icon)var8, 16, 16);
            GL11.glEnable(2896);
        }

        GL11.glEnable(2884);
    }

    public void renderItemAndEffectIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, Block par3Block, int par4, int par5) {
        if (par3Block != null) {
            this.renderItemIntoGUI(par1FontRenderer, par2TextureManager, par3Block, par4, par5);
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

    private void renderDroppedItem(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7) {
        Tessellator var8 = Tessellator.instance;
        if (par2Icon == null) {
            TextureManager var9 = Minecraft.getMinecraft().getTextureManager();
            ResourceLocation var10 = var9.getResourceLocation(par1EntityItem.getEntityItem().getItemSpriteNumber());
            par2Icon = ((TextureMap)var9.getTexture(var10)).getAtlasSprite("missingno");
        }

        float var25 = ((Icon)par2Icon).getMinU();
        float var26 = ((Icon)par2Icon).getMaxU();
        float var11 = ((Icon)par2Icon).getMinV();
        float var12 = ((Icon)par2Icon).getMaxV();
        float var13 = 1.0F;
        float var14 = 0.5F;
        float var15 = 0.25F;
        float var17;
        if (this.renderManager.options.isFancyGraphicsEnabled()) {
            GL11.glPushMatrix();
            if (renderInFrame) {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            } else {
                GL11.glRotatef((((float)par1EntityItem.age + par4) / 20.0F + par1EntityItem.hoverStart) * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            float var16 = 0.0625F;
            var17 = 0.021875F;
            ItemStack var18 = par1EntityItem.getEntityItem();
            int var19 = var18.stackSize;
            byte var24;
            if (var19 < 2) {
                var24 = 1;
            } else if (var19 < 16) {
                var24 = 2;
            } else if (var19 < 32) {
                var24 = 3;
            } else {
                var24 = 4;
            }

            GL11.glTranslatef(-var14, -var15, -((var16 + var17) * (float)var24 / 2.0F));

            for(int var20 = 0; var20 < var24; ++var20) {
                GL11.glTranslatef(0.0F, 0.0F, var16 + var17);
                if (var18.getItemSpriteNumber() == 0 && Block.blocksList[var18.itemID] != null) {
                    this.bindTexture(TextureMap.locationBlocksTexture);
                } else {
                    this.bindTexture(TextureMap.locationItemsTexture);
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                ItemRenderer.renderItemIn2D(var8, var26, var11, var25, var12, ((Icon)par2Icon).getIconWidth(), ((Icon)par2Icon).getIconHeight(), var16);
                if (var18.hasEffect()) {
                    GL11.glDepthFunc(514);
                    GL11.glDisable(2896);
                    this.renderManager.renderEngine.bindTexture(RES_ITEM_GLINT);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(768, 1);
                    float var21 = 0.76F;
                    GL11.glColor4f(0.5F * var21, 0.25F * var21, 0.8F * var21, 1.0F);
                    GL11.glMatrixMode(5890);
                    GL11.glPushMatrix();
                    float var22 = 0.125F;
                    GL11.glScalef(var22, var22, var22);
                    float var23 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(var23, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(var22, var22, var22);
                    var23 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-var23, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(5888);
                    GL11.glDisable(3042);
                    GL11.glEnable(2896);
                    GL11.glDepthFunc(515);
                }
            }

            GL11.glPopMatrix();
        } else {
            for(int var27 = 0; var27 < par3; ++var27) {
                GL11.glPushMatrix();
                if (var27 > 0) {
                    var17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float var29 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float var28 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(var17, var29, var28);
                }

                if (!renderInFrame) {
                    GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                }

                GL11.glColor4f(par5, par6, par7, 1.0F);
                var8.startDrawingQuads();
                var8.setNormal(0.0F, 1.0F, 0.0F);
                var8.addVertexWithUV((double)(0.0F - var14), (double)(0.0F - var15), 0.0, (double)var25, (double)var12);
                var8.addVertexWithUV((double)(var13 - var14), (double)(0.0F - var15), 0.0, (double)var26, (double)var12);
                var8.addVertexWithUV((double)(var13 - var14), (double)(1.0F - var15), 0.0, (double)var26, (double)var11);
                var8.addVertexWithUV((double)(0.0F - var14), (double)(1.0F - var15), 0.0, (double)var25, (double)var11);
                var8.draw();
                GL11.glPopMatrix();
            }
        }

    }

    @Override
    public void doRender(Entity entity, double d, double e, double f, float g, float h) {
        this.doRenderItem((EntityItem)entity, d, e, f, g, h);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
