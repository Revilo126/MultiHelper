package revilo.multihelper.client.tooltip;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiScreen;

import gregapi.data.LH;
import gregapi.data.LH.Chat;

public class Tooltip {

    private List<String> aTooltips;
    @Nullable
    private List<String> aStructure;

    public Tooltip(List<String> mTooltips, @Nullable List<String> mStructure) {
        aTooltips = mTooltips;
        aStructure = mStructure;
    }

    public void addInformation(List<String> aList) {
        addTooltip(aList);
        addStructureTooltip(aList);
    }

    public void addTooltip(List<String> tooltips) {
        tooltips.addAll(aTooltips);
    }

    public void addStructureTooltip(List<String> tooltips) {
        if (aStructure == null) return;

        if (GuiScreen.isShiftKeyDown()) {
            tooltips.add(Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
            tooltips.addAll(aStructure);
        } else {
            // TODO change to LH.HOLD_STRUCTURE
            tooltips.add(Chat.GRAY + "Hold [LSHIFT] for structure");
        }
    }

    public class Builder {

        private List<String> aTooltips = new ArrayList<>();
        @Nullable
        private List<String> aStructure = null;

        public Builder addInformation(String aInformation) {
            this.aTooltips.add(aInformation);
            return this;
        }

        public Builder addInformationKey(String aKey) {
            this.addInformation(LH.get(aKey));
            return this;
        }

        public Builder addStructureInformation(String mStructure) {
            if (aStructure == null) this.aStructure = new ArrayList<>();
            this.aStructure.add(mStructure);
            return this;
        }

        public Builder addStructureInformationKey(String aKey) {
            this.addStructureInformation(LH.get(aKey));
            return this;
        }

        public Tooltip build() {
            return new Tooltip(aTooltips, aStructure);
        }
    }
}
