package com.rutgervos.extrathings.screen;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.screen.custom.ExtraChamberMenu;
import com.rutgervos.extrathings.screen.custom.ExtraFuseBlockMenu;
import com.rutgervos.extrathings.screen.custom.PedestalMenu;
import com.rutgervos.extrathings.screen.custom.PotionMixerMenu;
import com.rutgervos.extrathings.screen.custom.PotionPouchMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

      public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ExtraThings.MODID);

    public static final RegistryObject<MenuType<PedestalMenu>> PEDESTAL_MENU =
            MENUS.register("pedestal_name", () -> IForgeMenuType.create(PedestalMenu::new));

    public static final RegistryObject<MenuType<ExtraChamberMenu>> EXTRA_CHAMBER_MENU =
            MENUS.register("extra_chamber_menu", () -> IForgeMenuType.create(ExtraChamberMenu::new));

    public static final RegistryObject<MenuType<ExtraFuseBlockMenu>> EXTRA_FUSE_BLOCK_MENU = MENUS.register("extra_fuse_block_menu", () -> IForgeMenuType.create(ExtraFuseBlockMenu::new));
    public static final RegistryObject<MenuType<PotionMixerMenu>> POTION_MIXER =
            MENUS.register("potion_mixer_block_menu", () -> IForgeMenuType.create(PotionMixerMenu::new));
      public static final RegistryObject<MenuType<PotionPouchMenu>> POTION_POUCH = 
        MENUS.register("potion_pouch", () -> IForgeMenuType.create(PotionPouchMenu::new));



    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

}
