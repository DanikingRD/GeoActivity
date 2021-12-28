package daniking.geoactivity.api.item;

import daniking.geoactivity.common.recipe.AutoBlockSmeltingRecipe;
import daniking.geoactivity.common.registry.GARecipeTypes;
import daniking.geoactivity.common.util.GAInventory;
import daniking.geoactivity.common.util.RechargeUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BuilderItem extends Item implements Rechargeable {

    public BuilderItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        GAInventory inventory = GAInventory.create(stack, this.inventorySize());
        if (this.canSmelt(stack, inventory)) {
            tooltip.add(new TranslatableText("geoactivity.tooltip.blocks", inventory.getStack(1)).formatted(Formatting.GRAY));
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context == null) {
            return ActionResult.PASS;
        } else if (context.getWorld() == null) {
            return ActionResult.PASS;
        } else if (context.getPlayer() == null) {
            return ActionResult.PASS;
        } else {
            final ItemStack stack = context.getStack();
            RechargeUtil.initDestroyedNbt(context.getPlayer(), stack);
            GAInventory inventory = GAInventory.create(stack, this.inventorySize());
            if (canSmelt(stack, inventory)) {
                if (smeltItem(context, inventory)) {
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.useOnBlock(context);
    }

    public boolean canSmelt(ItemStack stack, Inventory inventory) {
        if (inventory.isEmpty()) {
            return false;
        }
        final ItemStack input = inventory.getStack(1);
        final ItemStack output = inventory.getStack(2);

        if (input.isEmpty()) {
            return false;
        } else if (output.isEmpty()) {
            return false;
        } else if (input.getItem() == output.getItem()) {
            return false;
        } else {
            return !RechargeUtil.isFatigued(stack);
        }
    }

    public boolean smeltItem(ItemUsageContext context, Inventory inventory) {

        if (context.getHand() == Hand.MAIN_HAND) {
            if (this.canSmelt(context.getStack(), inventory)) {
                final World world = context.getWorld();
                final BlockPos originPos = context.getBlockPos();
                final BlockState originState = world.getBlockState(originPos);
                final Direction side = context.getSide();
                final ItemStack stack = context.getStack();
                final ItemStack input = inventory.getStack(1);
                final ItemStack output = inventory.getStack(2);
                final PlayerEntity player = context.getPlayer();
                if (player == null) {
                    return false;
                }
                final AutoBlockSmeltingRecipe type = world
                        .getRecipeManager()
                        .listAllOfType(GARecipeTypes.AUTO_BLOCK_SMELTING_RECIPE_TYPE)
                        .stream()
                        .filter(recipe -> recipe.input() == input.getItem() && recipe.output().getItem() == output.getItem() && recipe.builder() == this)
                        .findFirst()
                        .orElse(null);
                if (type == null) {
                    context.getPlayer().sendMessage(new TranslatableText("geoactivity.message.info.wrong_ingredients").append("!").formatted(Formatting.GOLD), true);
                    return false;
                }

                if (!world.isClient) {
                    final int inputCount = input.getCount();
                    final int outputCount = output.getCount();
                    final ItemStack result = type.getOutput();
                    if ((inputCount > 0 && outputCount > 0) || player.isCreative()) {
                        final Block outputBlock = Block.getBlockFromItem(result.getItem());
                        if (outputBlock != Blocks.AIR) {
                            final BlockState outputState = outputBlock.getDefaultState();
                            if (outputState != null && !outputState.isAir()) {
                                boolean placed = false;
                                /*
                                 * Let's first check if we are facing a replaceable block.
                                 */
                                if (canPlaceAt(world, outputState, originPos) && originState.getMaterial().isReplaceable()) {
                                    world.setBlockState(originPos, outputState);
                                    placed = true;
                                } else {
                                    //Otherwise this is a full block
                                    //so lets look for the best side
                                    final BlockPos offsetPos = originPos.offset(side);
                                    final BlockState offsetState = world.getBlockState(offsetPos);
                                    //we don't want to replace an existing block
                                    if (offsetState.isAir()) {
                                        if (canPlaceAt(world, outputState, offsetPos)) {
                                            world.setBlockState(offsetPos, outputState);
                                            placed = true;
                                        }
                                    }
                                    //done to place blocks on fluids
                                    if (!placed) {
                                        if (!offsetState.getFluidState().isEmpty()) {
                                            world.setBlockState(offsetPos, outputState);
                                            placed = true;
                                        }
                                    }
                                }
                                if (placed) {
                                    if (!player.isCreative()) {
                                        stack.damage(2, player, (user -> user.sendToolBreakStatus(context.getHand())));
                                        inventory.removeStack(1, 1);
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean canPlaceAt(final @NotNull World world, final BlockState state, final BlockPos at) {
        return world.canPlace(state, at, ShapeContext.absent());
    }
}
