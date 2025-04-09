package com.rutgervos.extrathings.screen;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.screen.custom.ExtraChamberMenu;
import com.rutgervos.extrathings.screen.custom.PedestalMenu;

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



    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

}
