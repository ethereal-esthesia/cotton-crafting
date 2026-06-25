// SPDX-License-Identifier: GPL-3.0-only

package dev.shane.minecraft.cottoncrafting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class CottonCraftingPlugin extends JavaPlugin {

    private NamespacedKey cottonFromWhiteWoolKey;

    @Override
    public void onEnable() {
        cottonFromWhiteWoolKey = new NamespacedKey(this, "cotton_from_white_wool");
        registerRecipes();
        getLogger().info("Enabled. Registered cotton crafting recipes.");
    }

    @Override
    public void onDisable() {
        if (cottonFromWhiteWoolKey != null) {
            getServer().removeRecipe(cottonFromWhiteWoolKey);
        }
        getLogger().info("Disabled.");
    }

    private void registerRecipes() {
        getServer().removeRecipe(cottonFromWhiteWoolKey);

        ItemStack stringResult = new ItemStack(Material.STRING, 4);
        ShapelessRecipe cottonFromWhiteWool = new ShapelessRecipe(cottonFromWhiteWoolKey, stringResult);
        cottonFromWhiteWool.addIngredient(Material.WHITE_WOOL);

        getServer().addRecipe(cottonFromWhiteWool);
    }
}
