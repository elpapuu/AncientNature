package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public class SearchForItemsFromTagsGoal extends Goal {

    private final Mob mob;

    private final boolean canMove;

    private final TagKey<Item> tag;

    private final double search;


        public SearchForItemsFromTagsGoal(PathfinderMob pRemoverMob, boolean canMove, TagKey<Item> pTag, double searchRadius) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.mob = pRemoverMob;
        this.canMove = canMove;
        this.tag = pTag;
        this.search = searchRadius;
    }

   @Override
    public boolean canUse() {
        if (!mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {

        //    System.out.println("false: hand is not empty");
            return false;
        } else if (mob.getTarget() != null || mob.getLastHurtByMob() != null || !canMove) {
       //     System.out.println("false: has either get target, was hurt, or can move is false");
            return false;
        } else {

            List<ItemEntity> list = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(search, search, search), entity -> {
                ItemStack stack = entity.getItem();
          //      System.out.println("found item:" + stack);
         //       System.out.println("this can be used!!!");
                return !stack.isEmpty() && stack.is(tag);
            });
            return !list.isEmpty() && mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
        }
    }


    @Override
    public void tick() {
        super.tick();
        System.out.println("TICK");
        List<ItemEntity> list = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(search, search, search), itemEntity -> {
            ItemStack stack = itemEntity.getItem();
             System.out.println("found item:" + stack);
            return !stack.isEmpty() && stack.is(tag);
        });
        ItemStack handItem = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        System.out.println("hand item:" + handItem);
        System.out.println("list item:" + list);
        if (handItem.isEmpty() && !list.isEmpty()) {
            this.mob.getNavigation().moveTo((double)((float)list.get(0).getX())
                    , (double)(list.get(0).getY()), (double)((float)list.get(0).getZ())
                    , 1.2F);
        }
        ItemEntity closestItem = list.get(0);
        if (mob.distanceTo(closestItem) <= 2.3) {
            this.mob.setItemInHand(InteractionHand.MAIN_HAND, closestItem.getItem());
            closestItem.discard();
        }



    }



}
