package me.ez.orebushes.Datagen;

import me.ez.orebushes.Init;
import me.ez.orebushes.Main;
import net.minecraft.data.DataGenerator;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, Main.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(Init.COAL_BUSH_ITEM.get(), "Coal Seed");
        add(Init.IRON_BUSH_ITEM.get(), "Iron Seed");
        add(Init.GOLD_BUSH_ITEM.get(), "Gold Seed");
        add(Init.EMERALD_BUSH_ITEM.get(), "Emerald Seed");
        add(Init.LAPIS_BUSH_ITEM.get(), "Lapis Lazuli Seed");
        add(Init.REDSTONE_BUSH_ITEM.get(), "Redstone Seed");
        add(Init.DIAMOND_BUSH_ITEM.get(), "Diamond Seed");
        add(Init.QUARTZ_BUSH_ITEM.get(), "Quartz Seed");
        add(Init.GLOWSTONE_BUSH_ITEM.get(), "Glowstone Seed");

        add(Init.EMERALD_NUGGET.get(), "Emerald Nugget");
        add(Init.DIAMOND_NUGGET.get(), "Diamond Nugget");

    }
}
