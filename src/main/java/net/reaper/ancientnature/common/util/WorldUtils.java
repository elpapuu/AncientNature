package net.reaper.ancientnature.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorldUtils {



    /**
     * checks whether the Position is loaded or not
     */
    public static boolean isBlockLoaded(@Nullable BlockGetter world, @Nonnull BlockPos pos) {
        if (world == null || !Level.isInSpawnableBounds(pos)) {
            return false;
        } else if (world instanceof LevelAccessor) {
            //Note: We don't bother checking if it is a world and then isBlockPresent because
            // all that does is also validate the y value is in bounds, and we already check to make
            // sure the position is valid both in the y and xz directions
            return ((LevelAccessor) world).isAreaLoaded(pos, 0);
        }
        return true;
    }


    /**
     * gets the TileEntity when the chunk is loaded
     */

    @Nullable
    public static BlockEntity getTileEntity(BlockGetter world, BlockPos pos) {
        if(!isBlockLoaded(world, pos)) {
            return null;
        }
        return world.getBlockEntity(pos);
    }
    /**
     * gets the TileEntity when the chunk is loaded
     * @param <T> - the Type of the TileEntity
     * @param clazz - the class the TileEntity at this position should have
     * @param world - the world we are in
     * @param pos - the position we are searching for the Tile Entity
     * @return the TileEntity or null if there isnt one or it cant be cast
     */
    @Nullable
    public static <T extends BlockEntity> T getTileEntity(@Nonnull Class<T> clazz, @Nullable BlockGetter world, @Nonnull BlockPos pos) {
        BlockEntity te = getTileEntity(world, pos);
        if(te == null)
            return null;
        else if(clazz.isInstance(te)) {
            return clazz.cast(te);
        }
        return null;
    }

    /**
     *
     * @param clazz - the class u wanna cast the entity to
     * @param world - the world we are curently at
     * @param entityId - the id of the entity
     * @param <T> - the Entity class
     * @return the Entity from the world with the specific id and the specific class, can be null when it cant find entity or cant cast it
     */
    @Nullable
    public static <T extends Entity> T getEntity(@Nonnull Class<T> clazz, @Nullable Level world, int entityId){
        Entity e = world.getEntity(entityId);
        if (e == null)
            return null;
        else if(clazz.isInstance(e))
            return clazz.cast(e);
        return null;
    }
}
