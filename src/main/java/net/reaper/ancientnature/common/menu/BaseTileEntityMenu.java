package net.reaper.ancientnature.common.menu;


import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import java.util.Objects;

public abstract class BaseTileEntityMenu<T extends BlockEntity & Container> extends UtilMenu {

	protected final T tileEntity;

	/**
	 * normal constructor will be called by client and server, server will call it directly and the cleint via the other constructor
	 */
	public BaseTileEntityMenu(MenuType<?> type, int id, Inventory inv, T tileEntity) {
		super(type, id, inv);
		this.tileEntity = tileEntity;
		init();
	}

	/**
	 * @param type the type this container is associated to
	 * @param id the window id normally get provided from open {@link MenuProvider#createMenu(int, Inventory, Player)}
	 * @param inv the player inventory which should be displayed in the gui
	 * @param buffer the buffer where the client values will be synced the pos of the TileEntity has be written here, can be done in {@link net.minecraftforge.network.NetworkHooks#openScreen(ServerPlayer, MenuProvider, BlockPos)}
	 */
	public BaseTileEntityMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf buffer) {
		this(type, id, inv, getClientTileEntity(inv, buffer));
	}

	/**
	 * this will be called at the end of every constructor to do the slots
	 */
	public abstract void init();


	/**
	 * remember to write the block pos of the te on the buffer in the {@link net.minecraftforge.network.NetworkHooks#openScreen(ServerPlayer, MenuProvider, BlockPos)}
	 *
	 */
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	protected static <X extends BlockEntity> X getClientTileEntity(final Inventory inventory,
															 final FriendlyByteBuf buffer) {
		Objects.requireNonNull(inventory, "the inventory must not be null");
		Objects.requireNonNull(buffer, "the buffer must not be null");
		final BlockEntity tileEntity = inventory.player.level().getBlockEntity(buffer.readBlockPos());
		return (X) tileEntity;
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return this.tileEntity.stillValid(pPlayer);
	}

	public T getTileEntity() {
		return tileEntity;
	}

	public static class FuelSlot extends Slot{

		protected final RecipeType<?> recipeType;

		public FuelSlot(Container inventoryIn, RecipeType<?> recipeType, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
			this.recipeType = recipeType;
		}

		public FuelSlot(Container pContainer, int pSlot, int pX, int pY) {
			this(pContainer,null, pSlot, pX, pY);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return ForgeHooks.getBurnTime(stack, null) > 0;
		}
		
	}

}
