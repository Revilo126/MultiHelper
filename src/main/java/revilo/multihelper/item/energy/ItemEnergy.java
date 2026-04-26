package revilo.multihelper.item.energy;

import static gregapi.data.CS.*;

import java.util.Collection;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import gregapi.code.TagData;
import gregapi.data.TD;
import gregapi.item.IItemEnergy;
import gregapi.util.ST;
import gregapi.util.UT;

public abstract class ItemEnergy implements IItemEnergy {

    public ItemStack mEmptyItem, mHalfItem, mFullItem;

    /*
     * The Energy Type of the Item.
     */
    public abstract TagData getEnergyType();

    /*
     * The Capacity of the Item.
     */
    public abstract long getCapacity();

    /*
     * The tier of the item. E.g 3 (HV). Determines the Minimum, Recommended and Maximum input.
     */
    public abstract int getTier();

    /*
     * Can the item be charged.
     */
    public abstract boolean canCharge();

    /*
     * Can the item be decharged.
     */
    public abstract boolean canDecharge();

    private long getAmountMin() {
        return VMIN[getTier()];
    }

    private long getAmountRec() {
        return VREC[getTier()];
    }

    private long getAmountMax() {
        return VMAX[getTier()];
    }

    public ItemStack rechargeFromPlayer(TagData aEnergyType, ItemStack aStack, EntityLivingBase aPlayer,
        IInventory aInventory, World aWorld, int aX, int aY, int aZ) {
        if (COMPAT_EU_ITEM == null || !canCharge()
            || aPlayer == null
            || aPlayer.worldObj.isRemote
            || aEnergyType != getEnergyType()
            || aEnergyType != TD.Energy.EU) return aStack;
        long tMinInput = getEnergySizeInputMin(aEnergyType, aStack), tCapacity = getEnergyCapacity(aEnergyType, aStack);
        boolean temp = F;
        try {
            for (int i = 1; i < 5; i++) {
                long tContent = getEnergyStored(aEnergyType, aStack);
                if (tContent >= tCapacity) return aStack;
                ItemStack tArmor = aPlayer.getEquipmentInSlot(i);
                if (tArmor == aStack || ST.invalid(tArmor)
                    || !COMPAT_EU_ITEM.is(tArmor)
                    || VMAX[COMPAT_EU_ITEM.tier(tArmor)] < tMinInput
                    || !COMPAT_EU_ITEM.provider(tArmor)) continue;
                setEnergyStored(
                    aEnergyType,
                    aStack,
                    tContent + COMPAT_EU_ITEM.decharge(tArmor, tCapacity - tContent, T));
                temp = T;
            }
        } catch (Throwable e) {
            e.printStackTrace(ERR);
        }
        if (temp) ST.update(aPlayer);
        return aStack;
    }

    /**
     * You do not have to check for this Function, this is only for things like Slot insertion Conditions and similar.
     * 
     * @param aEnergyType The Type of Energy
     * @param aEmitting   if it is asked emit this Energy Type, otherwise it is asked to accept this Energy Type.
     * @return if this Item has anything to do with this Type of Energy, depending on insert or extract request. The
     *         returning Value must be constant for this Item.
     */
    @Override
    public boolean isEnergyType(TagData aEnergyType, ItemStack aStack, boolean aEmitting) {
        return aEnergyType == getEnergyType();
    }

    /**
     * Gets all the Types of Energy, which are relevant to this Item.
     * 
     * @return any Type of Energy that is related to this Item. Use java.util.Collections.EMPTY_LIST if you don't have
     *         any Energy Types on this Item/Meta/NBT.
     */
    @Override
    public Collection<TagData> getEnergyTypes(ItemStack aStack) {
        return TD.Energy.ALL_EU;
    }

    /**
     * Charges an Energy Storage. The Coordinates represent the position of wherever the Item is supposed to be.
     * 
     * @param aEnergyType The Type of Energy
     * @param aWorld      if the Coordinates passed are valid then this must be != null. if this is null, then the
     *                    Position is unknown.
     * @param aInventory  the Inventory the Item is in. May be null.
     * @param aDoInject   if this is supposed to increase the internal Energy. true = Yes, this is a normal Operation.
     *                    false = No, this is just a simulation.
     * @return amount of used aAmount.
     */
    @Override
    public long doEnergyInjection(TagData aEnergyType, ItemStack aStack, long aSize, long aAmount,
        IInventory aInventory, World aWorld, int aX, int aY, int aZ, boolean aDoInject) {
        if (aAmount < 1 || getAmountMax() < 1) return 0;
        if (!canEnergyInjection(aEnergyType, aStack, aSize = Math.abs(aSize))) return 0;
        long tStored = getEnergyStored(getEnergyType(), aStack);
        if (tStored >= getCapacity()) return 0;
        long rAmount = Math.min(getAmountMax(), aAmount);
        while (rAmount > 1 && tStored + rAmount * aSize > getCapacity()) rAmount--;
        if (aDoInject) setEnergyStored(getEnergyType(), aStack, tStored + rAmount * aSize);
        return rAmount;
    }

    public boolean canEnergyInjection(TagData aEnergyType, ItemStack aStack, long aSize) {
        return canCharge() || aEnergyType == getEnergyType();
    }

    /**
     * Decharges an Energy Storage. The Coordinates represent the position of wherever the Item is supposed to be.
     * 
     * @param aEnergyType The Type of Energy
     * @param aWorld      if the Coordinates passed are valid then this must be != null. if this is null, then the
     *                    Position is unknown.
     * @param aDoExtract  if this is supposed to decrease the internal Energy. true = Yes, this is a normal Operation.
     *                    false = No, this is just a simulation.
     * @return amount of taken aAmount.
     */
    @Override
    public long doEnergyExtraction(TagData aEnergyType, ItemStack aStack, long aSize, long aAmount,
        IInventory aInventory, World aWorld, int aX, int aY, int aZ, boolean aDoExtract) {
        if (aAmount < 1 || getAmountMax() < 1) return 0;
        if (!canEnergyExtraction(aEnergyType, aStack, aSize = Math.abs(aSize))) return 0;
        long tStored = getEnergyStored(getEnergyType(), aStack);
        if (tStored < aSize) return 0;
        long rAmount = Math.min(getAmountMax(), aAmount);
        while (rAmount > 1 && tStored - rAmount * aSize < 0) rAmount--;
        if (aDoExtract) setEnergyStored(getEnergyType(), aStack, tStored - rAmount * aSize);
        return rAmount;
    }

    @Override
    public boolean canEnergyExtraction(TagData aEnergyType, ItemStack aStack, long aSize) {
        return canDecharge() || aEnergyType == getEnergyType();
    }

    /**
     * "canUse" and "use" together in one Function by using a Simulate Parameter.
     * 
     * @param aEnergyType The Type of Energy
     * @param aWorld      if the Coordinates passed are valid then this must be != null. if this is null, then the
     *                    Position is unknown.
     * @param aDoUse      if this is supposed to decrease the internal Energy. true = Yes, this is a normal Operation.
     *                    false = No, this is just a simulation.
     * @return true if this can use that much Energy.
     */
    @Override
    public boolean useEnergy(TagData aEnergyType, ItemStack aStack, long aEnergyAmount, EntityLivingBase aPlayer,
        IInventory aInventory, World aWorld, int aX, int aY, int aZ, boolean aDoUse) {
        if (aPlayer instanceof EntityPlayer && ((EntityPlayer) aPlayer).capabilities.isCreativeMode) return T;
        if (aEnergyType != getEnergyType() && aEnergyType != null) return F;
        rechargeFromPlayer(getEnergyType(), aStack, aPlayer, aInventory, aWorld, aX, aY, aZ);
        long tStored = getEnergyStored(getEnergyType(), aStack);
        if (tStored >= aEnergyAmount) {
            if (aDoUse) {
                setEnergyStored(getEnergyType(), aStack, tStored - aEnergyAmount);
                rechargeFromPlayer(getEnergyType(), aStack, aPlayer, aInventory, aWorld, aX, aY, aZ);
            }
            return T;
        }
        if (aDoUse) {
            setEnergyStored(getEnergyType(), aStack, 0);
            rechargeFromPlayer(getEnergyType(), aStack, aPlayer, aInventory, aWorld, aX, aY, aZ);
        }
        return F;
    }

    /**
     * @param aEnergyType The Type of Energy.
     * @return the ItemStack you used as a Parameter. This is for convenience.
     */
    @Override
    public ItemStack setEnergyStored(TagData aEnergyType, ItemStack aStack, long aAmount) {
        if ((aEnergyType != getEnergyType() && aEnergyType != null) || ST.size(aStack) <= 0) return aStack;

        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) tNBT = UT.NBT.make();
        else tNBT.removeTag(NBT_ENERGY);

        if (aAmount > 0) {
            if (aAmount >= getCapacity()) {
                if (mFullItem != null) ST.set(aStack, mFullItem, F, F);
            } else {
                if (mHalfItem != null) ST.set(aStack, mHalfItem, F, F);
            }
            UT.NBT.setNumber(tNBT, NBT_ENERGY, aAmount);
        } else {
            if (mEmptyItem == null) {
                aStack.stackSize--;
            } else {
                ST.set(aStack, mEmptyItem, F, F);
            }
        }
        UT.NBT.set(aStack, tNBT);
        return ST.update_(aStack);
    }

    /**
     * @param aEnergyType The Type of Energy
     * @return amount of Energy stored.
     */
    @Override
    public long getEnergyStored(TagData aEnergyType, ItemStack aStack) {
        if (aEnergyType != getEnergyType() && aEnergyType != null) return 0;
        NBTTagCompound tNBT = aStack.getTagCompound();
        return tNBT == null ? 0 : tNBT.getLong(NBT_ENERGY);
    }

    @Override
    public long getEnergyCapacity(TagData aEnergyType, ItemStack aStack) {
        return aEnergyType == getEnergyType() || aEnergyType == null ? getCapacity() : 0;
    }

    @Override
    public long getEnergySizeInputMin(TagData aEnergyType, ItemStack aStack) {
        return aEnergyType == getEnergyType() || aEnergyType == null ? getAmountMin() : 0;
    }

    @Override
    public long getEnergySizeOutputMin(TagData aEnergyType, ItemStack aStack) {
        return aEnergyType == getEnergyType() || aEnergyType == null ? getAmountMin() : 0;
    }

    @Override
    public long getEnergySizeInputRecommended(TagData aEnergyType, ItemStack aStack) {
        return aEnergyType == getEnergyType() || aEnergyType == null ? getAmountRec() : 0;
    }

    @Override
    public long getEnergySizeOutputRecommended(TagData aEnergyType, ItemStack aStack) {
        return aEnergyType == getEnergyType() || aEnergyType == null ? getAmountRec() : 0;
    }

    @Override
    public long getEnergySizeInputMax(TagData aEnergyType, ItemStack aStack) {
        return aEnergyType == getEnergyType() || aEnergyType == null ? getAmountMax() : 0;
    }

    @Override
    public long getEnergySizeOutputMax(TagData aEnergyType, ItemStack aStack) {
        return aEnergyType == getEnergyType() || aEnergyType == null ? getAmountMax() : 0;
    }

}
