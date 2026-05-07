package revilo.multihelper.recipe;

import static gregapi.data.CS.F;
import static gregapi.data.CS.T;

import java.util.Collection;

import gregapi.recipes.Recipe;
import gregapi.recipes.Recipe.RecipeMap;

/*
 * To ease the creation of RecipeMaps
 */
public class RecipeMapBuilder {

    public static Builder builder(String aNameInternal, String aNameLocal) {
        return new Builder(aNameInternal, aNameLocal);
    }

    public static class Builder {

        // Basic Recipe Config
        private Collection<Recipe> aRecipeList = null;
        private String aNameInternal, aNameLocal, aNameNEI = null, aNEIGUIPath;
        private long aProgressBarDirection = 0, aProgressBarAmount = 1;

        // Input, Output, Minimal //
        private Long[] items = { 0L, 0L, 0L };
        private Long[] fluids = { 0L, 0L, 0L };
        // All together the minimal inputs. Items + Fluids.
        private long minimalInputs;

        // Amperage - 1 is default
        private long aPower = 1;

        // For use with special requirements. E.g Light requirement for Fusion reactor. ("Start: ", 1, " LU")
        private String aNEISpecialValuePre = "";
        long aNEISpecialValueMultiplier = 1;
        private String aNEISpecialValuePost = "";

        // Extra Config
        private boolean aFuelMap = F, aShowVoltageAmperageInNEI = T, aNEIAllowed = T, aConfigAllowed = T,
            aNeedsOutputs = T, aCombinePower = F, aUseBucketSizeIn = T, aUseBucketSizeOut = T;

        public Builder(String aNameInternal, String aNameLocal) {
            this.aNameInternal = aNameInternal;
            this.aNameLocal = aNameLocal;
        }

        // If the progress bar is weird
        public Builder setProgressBar(long direction, long amount) {
            this.aProgressBarDirection = direction;
            this.aProgressBarAmount = amount;
            return this;
        }

        public Builder itemIO(long in, long out, long minimum) {
            this.items = new Long[] { in, out, minimum };
            return this;
        }

        public Builder fluidIO(long in, long out, long minimum) {
            this.fluids = new Long[] { in, out, minimum };
            return this;
        }

        public Builder minimumInputs(long minimum) {
            this.minimalInputs = minimum;
            return this;
        }

        public Builder setGuiPath(String aPath) {
            this.aNEIGUIPath = aPath;
            return this;
        }

        public Builder amp(long amperage) {
            this.aPower = amperage;
            return this;
        }

        public Builder fuelMap(boolean isFuelMap) {
            this.aFuelMap = isFuelMap;
            return this;
        }

        public Builder useBucketsIn(boolean useBucketsIn) {
            this.aUseBucketSizeIn = useBucketsIn;
            return this;
        }

        public Builder useBucketsOut(boolean useBucketsOut) {
            this.aUseBucketSizeOut = useBucketsOut;
            return this;
        }

        public Builder useBuckets(boolean useBuckets) {
            this.aUseBucketSizeIn = useBuckets;
            this.aUseBucketSizeOut = useBuckets;
            return this;
        }

        // Extra config for use with special Maps.
        public Builder extraConfig(boolean aShowVoltageAmperageInNEI, boolean aNEIAllowed, boolean aConfigAllowed,
            boolean aNeedsOutputs, boolean aCombinePower) {
            this.aShowVoltageAmperageInNEI = aShowVoltageAmperageInNEI;
            this.aNEIAllowed = aNEIAllowed;
            this.aConfigAllowed = aConfigAllowed;
            this.aNeedsOutputs = aNeedsOutputs;
            this.aCombinePower = aCombinePower;
            return this;
        }

        public Builder NEISpecial(String aNEISpecialValuePre, long aNEISpecialValueMultiplier,
            String aNEISpecialValuePost) {
            this.aNEISpecialValuePre = aNEISpecialValuePre;
            this.aNEISpecialValueMultiplier = aNEISpecialValueMultiplier;
            this.aNEISpecialValuePost = aNEISpecialValuePost;
            return this;
        }

        public RecipeMap build() {
            return new RecipeMap(
                aRecipeList,
                aNameInternal,
                aNameLocal,
                aNameNEI,
                aProgressBarDirection,
                aProgressBarAmount,
                aNEIGUIPath,
                items[0],
                items[1],
                items[2],
                fluids[0],
                fluids[1],
                fluids[2],
                minimalInputs,
                aPower,
                aNEISpecialValuePre,
                aNEISpecialValueMultiplier,
                aNEISpecialValuePost,
                aFuelMap,
                aShowVoltageAmperageInNEI,
                aNEIAllowed,
                aConfigAllowed,
                aNeedsOutputs,
                aCombinePower,
                aUseBucketSizeIn,
                aUseBucketSizeOut);
        }
    }

}
