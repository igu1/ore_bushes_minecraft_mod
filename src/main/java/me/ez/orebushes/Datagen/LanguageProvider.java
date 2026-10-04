package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Main;
import net.minecraft.data.PackOutput;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {

    public LanguageProvider(PackOutput output, String locale) {
        super(output, Main.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        for (me.ez.orebushes.Common.Bushes.ResourcePlantProfile profile : me.ez.orebushes.Common.Bushes.ResourcePlantProfile.values()) {
            add("item.orebushes." + profile.id + "_bush", profile.displayName + " Starter");
            add("block.orebushes." + profile.id + "_bush_stage", profile.displayName);
            add("subtitles.orebushes." + profile.id + "_harvest", profile.displayName + " harvested");
        }

        add(Init.BUSH_HARVESTER.get(), "Ore Harvester");


        add(Init.EMERALD_NUGGET.get(), "Emerald Nugget");
        add(Init.DIAMOND_NUGGET.get(), "Diamond Nugget");
        add(Init.NETHERITE_NUGGET.get(), "Netherite Nugget");
        add(Init.COPPER_NUGGET.get(), "Copper Nugget");



    }
}
