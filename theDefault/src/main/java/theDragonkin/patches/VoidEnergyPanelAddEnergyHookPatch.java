package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theDragonkin.DefaultMod;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "loseEnergy"
)
public class VoidEnergyPanelAddEnergyHookPatch {
        public VoidEnergyPanelAddEnergyHookPatch() {
        }

        public static void Prefix(int e) {
            DefaultMod.receiveEnergyChanged(e);
        }
    }
