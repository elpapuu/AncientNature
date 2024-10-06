package net.reaper.ancientnature.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.menu.RevivalStandMenu;
import net.reaper.ancientnature.common.util.ScreenUtils;

public class RevivalStandScreen extends AbstractContainerScreen<RevivalStandMenu> {

    public static final ResourceLocation TEXTURE = AncientNature.modLoc("textures/gui/revivalstand_gui.png");

    public RevivalStandScreen(RevivalStandMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY - 17, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 12, 4210752, false);
    }



    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        //main gui
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos - 17, 0, 0, this.imageWidth, 196);

        //amber progress
        ScreenUtils.renderImageAnimationBottomTop(pGuiGraphics, TEXTURE,
                this.leftPos + 85, this.topPos + 20,
                180, 7, 5, 11,
                this.menu.getTileEntity().getAmberProgress(),
                this.menu.getTileEntity().getMaxAmberProgress());

        //fuel progress
        ScreenUtils.renderImageAnimationBottomTop(pGuiGraphics, TEXTURE,
                this.leftPos + 82, this.topPos + 53,
                176, 21, 11, 15,
                this.menu.getTileEntity().getFuel(),
                this.menu.getTileEntity().getMaxFuel());

        //fossil progress
        int fossilScale = ScreenUtils.getScaledInt(
                this.menu.getTileEntity().getFossilProgress(),
                this.menu.getTileEntity().getMaxFossilProgress(), 25);
        pGuiGraphics.blit(TEXTURE,
                this.leftPos + 60, this.topPos + 23,
                176, 1, fossilScale,4 );
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
