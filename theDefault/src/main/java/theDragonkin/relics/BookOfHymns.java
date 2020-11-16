package theDragonkin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseBlockRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theDragonkin.DefaultMod;
import theDragonkin.powers.DivineConvictionpower;
import theDragonkin.util.TextureLoader;

import static com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;
import static theDragonkin.DefaultMod.makeRelicOutlinePath;
import static theDragonkin.DefaultMod.makeRelicPath;

public class BookOfHymns extends CustomRelic implements OnLoseBlockRelic , OnLoseTempHpRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("BookOfHymns");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.

    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.
    private  boolean used = false;

    public BookOfHymns() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.HEAVY);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atPreBattle() {
    }

    @Override
    public void onCardDraw(AbstractCard card){
    }

    @Override
    public void onUseCard(final AbstractCard c , final UseCardAction ca){
    }


    @Override
    public void atTurnStart(){
    }

    @Override
    public void onPlayerEndTurn() {
    }
    @Override
    public void onVictory() {
        used = false;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        if (!used) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                this.flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DivineConvictionpower(AbstractDungeon.player, AbstractDungeon.player, 2), 2));
                used = true;
            }
        }
        return 0;
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        if (!used) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                this.flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DivineConvictionpower(AbstractDungeon.player, AbstractDungeon.player, 2), 2));
                used = true;
            }
        }
        return 0;
    }
}
