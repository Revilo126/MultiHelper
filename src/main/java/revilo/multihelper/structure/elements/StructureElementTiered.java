package revilo.multihelper.structure.elements;

import static gregapi.data.CS.*;

import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;

public class StructureElementTiered<T extends ITileEntityMultiBlockController> implements IStructureElement<T> {

    private final Map<IStructureElement<T>, Integer> tierMap;
    private final BiConsumer<T, Integer> setter;

    public StructureElementTiered(Map<IStructureElement<T>, Integer> tierMap, BiConsumer<T, Integer> setter) {
        this.tierMap = tierMap;
        this.setter = setter;
    }

    @Override
    public boolean check(T t, ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory, int tX, int tY,
        int tZ) {
        for (Map.Entry<IStructureElement<T>, Integer> entry : tierMap.entrySet()) {

            if (entry.getKey()
                .check((T) t, aCoordinates, aPlayer, aInventory, tX, tY, tZ)) {
                setter.accept((T) t, entry.getValue());
                return T;
            }
        }
        return F;
    }
}
