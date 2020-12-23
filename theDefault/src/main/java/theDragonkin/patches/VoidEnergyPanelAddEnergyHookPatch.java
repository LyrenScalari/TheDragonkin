package theDragonkin.patches;

        import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
        import com.megacrit.cardcrawl.characters.AbstractPlayer;
        import theDragonkin.DefaultMod;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "loseEnergy"
)
public class VoidEnergyPanelAddEnergyHookPatch {
    public VoidEnergyPanelAddEnergyHookPatch(AbstractPlayer _instance,int e) {
    }
    public static void Prefix(AbstractPlayer _instance, int e) {
        DefaultMod.receiveEnergyChanged(e);
    }
}
