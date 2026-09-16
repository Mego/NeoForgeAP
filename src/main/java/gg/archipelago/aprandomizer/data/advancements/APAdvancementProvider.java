package gg.archipelago.aprandomizer.data.advancements;

import gg.archipelago.aprandomizer.APRandomizer;
import gg.archipelago.aprandomizer.tags.APDamageTypeTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.predicates.*;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.advancements.predicates.entity.PlayerPredicate;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

public class APAdvancementProvider extends AdvancementSubProvider {

    public APAdvancementProvider(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        HolderGetter<Item> items = output.lookup(Registries.ITEM);
        HolderGetter<Identifier> customStats = output.lookup(Registries.CUSTOM_STAT);
        HolderGetter<EntityType<?>> entityTypes = output.lookup(Registries.ENTITY_TYPE);
        HolderGetter<Recipe<?>> recipes = output.lookup(Registries.RECIPE);

        AdvancementHolder root = Advancement.Builder.recipeAdvancement()
                .rootDisplay(
                        Items.ENDER_PEARL,
                        Component.literal("Archipelago"),
                        Component.literal("Welcome to the AP Randomizer"),
                        Identifier.withDefaultNamespace("gui/advancements/backgrounds/end"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false)
                .addCriterion("auto", PlayerTrigger.TriggerInstance.tick())
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/root"));

        AdvancementHolder bread = Advancement.Builder.recipeAdvancement()
                .parent(root)
                .display(
                        Items.BREAD,
                        Component.literal("Bake Bread"),
                        Component.literal("Turn wheat into bread"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("get_bread", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(recipes.getOrThrow(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(Items.BREAD))))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/bake_bread"));

        AdvancementHolder leather = Advancement.Builder.recipeAdvancement()
                .parent(root)
                .display(
                        Items.LEATHER,
                        Component.literal("Cow Tipper"),
                        Component.literal("Harvest some leather"),
                        AdvancementType.TASK,
                        true,
                        false,
                        false)
                .addCriterion("get_leather", InventoryChangeTrigger.TriggerInstance.hasItems(Items.LEATHER))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/cow_tipper"));

        AdvancementHolder wood = Advancement.Builder.recipeAdvancement()
                .parent(root)
                .display(
                        Items.OAK_LOG,
                        Component.literal("Getting Wood"),
                        Component.literal("Attack a tree until a block of wood pops out."),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("get_logs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, ItemTags.LOGS)))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/get_wood"));

        AdvancementHolder sword = Advancement.Builder.recipeAdvancement()
                .parent(wood)
                .display(
                        Items.WOODEN_SWORD,
                        Component.literal("Time to Strike!"),
                        Component.literal("Use planks and sticks to make a sword"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("get_sword", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(recipes.getOrThrow(ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(Items.WOODEN_SWORD))))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/craft_sword"));

        AdvancementHolder pickaxe = Advancement.Builder.recipeAdvancement()
                .parent(wood)
                .display(
                        Items.WOODEN_PICKAXE,
                        Component.literal("Time to Mine!"),
                        Component.literal("Use planks and sticks to make a pickaxe"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("get_pick", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(recipes.getOrThrow(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(Items.WOODEN_PICKAXE))))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/get_pickaxe"));

        AdvancementHolder furnace = Advancement.Builder.recipeAdvancement()
                .parent(pickaxe)
                .display(
                        Items.FURNACE,
                        Component.literal("Hot Topic"),
                        Component.literal("Construct a furnace out of eight cobblestone blocks."),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("get_furnace", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(recipes.getOrThrow(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(Items.FURNACE))))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/hot_topic"));

        AdvancementHolder bookshelf = Advancement.Builder.recipeAdvancement()
                .parent(leather)
                .display(
                        Items.BOOKSHELF,
                        Component.literal("Librarian"),
                        Component.literal("Build a bookshelf to improve your enchantment table."),
                        AdvancementType.TASK,
                        true,
                        false,
                        false)
                .addCriterion("get_bookshelf", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(recipes.getOrThrow(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(Items.BOOKSHELF))))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/obtain_bookshelf"));

        AdvancementHolder overkill = Advancement.Builder.recipeAdvancement()
                .parent(sword)
                .display(
                        Items.NETHERITE_SWORD,
                        Component.literal("Overkill"),
                        Component.literal("Deal nine hearts of melee damage in a single hit."),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false)
                .addCriterion("overkill", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntityWithDamage(DamagePredicate.Builder.damageInstance()
                        .dealtDamage(MinMaxBounds.Doubles.atLeast(18))
                        .type(DamageSourcePredicate.Builder.damageType()
                                .tag(TagPredicate.isNot(damageTypes, APDamageTypeTags.FIREBALL)))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/overkill"));

        AdvancementHolder overpowered = Advancement.Builder.recipeAdvancement()
                .parent(bread)
                .display(
                        Items.ENCHANTED_GOLDEN_APPLE,
                        Component.literal("Overpowered"),
                        Component.literal("Eat an enchanted golden apple."),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("consumed_item", ConsumeItemTrigger.TriggerInstance.usedItem(items, Items.ENCHANTED_GOLDEN_APPLE))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/overpowered"));

        AdvancementHolder onARail = Advancement.Builder.recipeAdvancement()
                .parent(root)
                .display(
                        Items.MINECART,
                        Component.literal("On A Rail"),
                        Component.literal("Reach 1km by minecart in your statistics."),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false)
                .addCriterion("ride_1km", CriteriaTriggers.TICK.createCriterion(new PlayerTrigger.TriggerInstance(Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity()
                        .player(PlayerPredicate.Builder.player()
                                .addStat(Stats.CUSTOM, customStats.getOrThrow(ResourceKey.create(Registries.CUSTOM_STAT, Stats.MINECART_ONE_CM)), MinMaxBounds.Ints.atLeast(100000))
                                .build()))))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/ride_minecart"));

        AdvancementHolder ridePig = Advancement.Builder.recipeAdvancement()
                .parent(leather)
                .display(
                        Items.SADDLE,
                        Component.literal("When Pigs Fly"),
                        Component.literal("Fly a pig off a cliff of at least 5 blocks"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false)
                .addCriterion("fall_riding_pig", CriteriaTriggers.ENTITY_HURT_PLAYER.createCriterion(new EntityHurtPlayerTrigger.TriggerInstance(Optional.of(
                        EntityPredicate.wrap(EntityPredicate.Builder.entity()
                                .vehicle(EntityPredicate.Builder.entity()
                                        .entityType(EntityTypePredicate.of(entityTypes, EntityTypes.PIG))))),
                        Optional.of(DamagePredicate.Builder.damageInstance()
                                .type(DamageSourcePredicate.Builder.damageType()
                                        .tag(TagPredicate.is(damageTypes, APDamageTypeTags.FALL)))
                                .build()))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/ride_pig"));

        AdvancementHolder cake = Advancement.Builder.recipeAdvancement()
                .parent(bread)
                .display(
                        Items.CAKE,
                        Component.literal("The Lie"),
                        Component.literal("Bake cake using wheat, sugar, milk, and eggs!"),
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false)
                .addCriterion("bake_cake", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(recipes.getOrThrow(RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(Items.CAKE))))))
                .requirements(AdvancementRequirements.Strategy.AND)
                .save(output, Identifier.fromNamespaceAndPath(APRandomizer.MODID, "archipelago/the_lie"));

    }
}