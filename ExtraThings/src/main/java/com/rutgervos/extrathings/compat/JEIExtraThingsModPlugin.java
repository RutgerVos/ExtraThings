package com.rutgervos.extrathings.compat;

import com.rutgervos.extrathings.ExtraThings;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIExtraThingsModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ExtraThings.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // TODO Auto-generated method stub
        IModPlugin.super.registerCategories(registration);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // TODO Auto-generated method stub
        IModPlugin.super.registerGuiHandlers(registration);
    }
}
