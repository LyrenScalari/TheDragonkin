package theDragonkin.util;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import basemod.AutoAdd;
import theDragonkin.DragonkinMod;

public class AugmentHelper {
    public static void register() {
        new AutoAdd("DragonkinMod")
                .packageFilter("theDragonkin.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment);});
    }
}
