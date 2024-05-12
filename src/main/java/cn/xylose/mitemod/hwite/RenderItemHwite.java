package cn.xylose.mitemod.hwite;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;

public class RenderItemHwite extends Render {

    private RenderBlocks itemRenderBlocks = new RenderBlocks();
    public boolean renderWithColor = true;
    public float zLevel;

    public void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, Block par3Block, int par4, int par5) {
        int var6 = par3Block.blockID;
        int block_subtype = par3Block.getBlockSubtype(0);
        int item_subtype = par3Block.getItemSubtype(0);
        if (!(par3Block instanceof BlockSlab)) {
            Icon var8 = par3Block.getIcon(1, 2);
            if (RenderBlocks.renderItemIn3d(Block.blocksList[var6].getRenderType())) {
                par2TextureManager.bindTexture(TextureMap.locationBlocksTexture);
                Block var15 = Block.blocksList[var6];
                GL11.glPushMatrix();
                GL11.glTranslatef((float) (par4 - 2), (float) (par5 + 3), -3.0F + this.zLevel);
                GL11.glScalef(10.0F, 10.0F, 10.0F);
                GL11.glTranslatef(1.0F, 0.5F, 1.0F);
                GL11.glScalef(1.0F, 1.0F, -1.0F);
                GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                this.itemRenderBlocks.useInventoryTint = this.renderWithColor;
                this.itemRenderBlocks.renderBlockAsItem(var15, block_subtype, 1.0F);
                this.itemRenderBlocks.useInventoryTint = true;
                GL11.glPopMatrix();
            } else if (Item.itemsList[var6].requiresMultipleRenderPasses()) {
                par2TextureManager.bindTexture(TextureMap.locationItemsTexture);
                for(int var9 = 0; var9 <= 1; ++var9) {
                    Icon var10 = Item.itemsList[var6].getIconFromSubtypeForRenderPass(item_subtype, var9);
                    this.renderIcon(par4, par5, var10, 16, 16);
                }
            } else {
                this.renderIcon(par4, par5, var8, 16, 16);
            }
        }
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

    @Override
    public void doRender(Entity entity, double d, double e, double f, float g, float h) {
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
