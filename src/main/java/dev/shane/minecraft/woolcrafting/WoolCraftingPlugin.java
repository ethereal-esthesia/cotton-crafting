// SPDX-License-Identifier: GPL-3.0-only

package dev.shane.minecraft.woolcrafting;

import com.google.common.collect.ImmutableMultimap;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class WoolCraftingPlugin extends JavaPlugin {

    private final List<NamespacedKey> recipeKeys = new ArrayList<>();
    private NamespacedKey woolWearPieceKey;
    private NamespacedKey woolWearColorKey;

    @Override
    public void onEnable() {
        woolWearPieceKey = new NamespacedKey(this, "wool_wear_piece");
        woolWearColorKey = new NamespacedKey(this, "wool_wear_color");

        registerRecipes();
        getLogger().info("Enabled. Registered wool wear crafting recipes.");
    }

    @Override
    public void onDisable() {
        removeRecipes();
        getLogger().info("Disabled.");
    }

    private void registerRecipes() {
        removeRecipes();

        for (WoolColor color : WoolColor.values()) {
            for (WoolWearPiece piece : WoolWearPiece.values()) {
                NamespacedKey key = new NamespacedKey(this, color.keyPrefix() + "_" + piece.keySuffix());
                ShapedRecipe recipe = new ShapedRecipe(key, createWoolWear(piece, color));
                recipe.shape(piece.shape());
                recipe.setIngredient('W', color.woolMaterial());
                getServer().addRecipe(recipe);
                recipeKeys.add(key);
            }
        }
    }

    private void removeRecipes() {
        for (NamespacedKey key : recipeKeys) {
            getServer().removeRecipe(key);
        }
        recipeKeys.clear();
    }

    @SuppressWarnings("deprecation")
    private ItemStack createWoolWear(WoolWearPiece piece, WoolColor color) {
        ItemStack item = new ItemStack(piece.material());
        ItemMeta baseMeta = item.getItemMeta();
        if (!(baseMeta instanceof LeatherArmorMeta meta)) {
            throw new IllegalStateException(piece.material() + " did not provide leather armor metadata");
        }

        meta.setColor(color.dyeColor().getColor());
        meta.itemName(Component.text(piece.displayName()));
        meta.setUnbreakable(true);
        meta.setAttributeModifiers(ImmutableMultimap.<Attribute, AttributeModifier>of());
        meta.addItemFlags(
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_UNBREAKABLE,
            ItemFlag.HIDE_DYE,
            ItemFlag.HIDE_ADDITIONAL_TOOLTIP
        );
        meta.getPersistentDataContainer().set(woolWearPieceKey, PersistentDataType.STRING, piece.keySuffix());
        meta.getPersistentDataContainer().set(woolWearColorKey, PersistentDataType.STRING, color.keyPrefix());

        item.setItemMeta(meta);
        return item;
    }

    private enum WoolWearPiece {
        CAP("cap", "Wool Cap", Material.LEATHER_HELMET, new String[] {
            "WWW",
            "W W"
        }),
        JACKET("jacket", "Wool Jacket", Material.LEATHER_CHESTPLATE, new String[] {
            "W W",
            "WWW",
            "WWW"
        }),
        TROUSERS("trousers", "Wool Trousers", Material.LEATHER_LEGGINGS, new String[] {
            "WWW",
            "W W",
            "W W"
        }),
        BOOTS("boots", "Wool Boots", Material.LEATHER_BOOTS, new String[] {
            "W W",
            "W W"
        });

        private final String keySuffix;
        private final String displayName;
        private final Material material;
        private final String[] shape;

        WoolWearPiece(String keySuffix, String displayName, Material material, String[] shape) {
            this.keySuffix = keySuffix;
            this.displayName = displayName;
            this.material = material;
            this.shape = shape;
        }

        private String keySuffix() {
            return keySuffix;
        }

        private String displayName() {
            return displayName;
        }

        private Material material() {
            return material;
        }

        private String[] shape() {
            return shape.clone();
        }
    }

    private enum WoolColor {
        WHITE("white", Material.WHITE_WOOL, DyeColor.WHITE),
        ORANGE("orange", Material.ORANGE_WOOL, DyeColor.ORANGE),
        MAGENTA("magenta", Material.MAGENTA_WOOL, DyeColor.MAGENTA),
        LIGHT_BLUE("light_blue", Material.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE),
        YELLOW("yellow", Material.YELLOW_WOOL, DyeColor.YELLOW),
        LIME("lime", Material.LIME_WOOL, DyeColor.LIME),
        PINK("pink", Material.PINK_WOOL, DyeColor.PINK),
        GRAY("gray", Material.GRAY_WOOL, DyeColor.GRAY),
        LIGHT_GRAY("light_gray", Material.LIGHT_GRAY_WOOL, DyeColor.LIGHT_GRAY),
        CYAN("cyan", Material.CYAN_WOOL, DyeColor.CYAN),
        PURPLE("purple", Material.PURPLE_WOOL, DyeColor.PURPLE),
        BLUE("blue", Material.BLUE_WOOL, DyeColor.BLUE),
        BROWN("brown", Material.BROWN_WOOL, DyeColor.BROWN),
        GREEN("green", Material.GREEN_WOOL, DyeColor.GREEN),
        RED("red", Material.RED_WOOL, DyeColor.RED),
        BLACK("black", Material.BLACK_WOOL, DyeColor.BLACK);

        private final DyeColor dyeColor;
        private final Material woolMaterial;
        private final String keyPrefix;

        WoolColor(String keyPrefix, Material woolMaterial, DyeColor dyeColor) {
            this.dyeColor = dyeColor;
            this.keyPrefix = keyPrefix;
            this.woolMaterial = woolMaterial;
        }

        private DyeColor dyeColor() {
            return dyeColor;
        }

        private Material woolMaterial() {
            return woolMaterial;
        }

        private String keyPrefix() {
            return keyPrefix;
        }
    }
}
