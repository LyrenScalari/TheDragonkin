package theDragonkin.powers.Dragonkin.DragonBreaths;

import basemod.devcommands.power.Power;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.InversionBeamEffect;
import theDragonkin.DefaultMod;

public class VoiceofOrderEffect extends AbstractDragonBreathPower{
    public int Block;
    public int Bonus;
    public static final String POWER_ID = DefaultMod.makeID("VoiceofOrder");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public VoiceofOrderEffect (int Block, int Debuff, AbstractCard source){
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount = Debuff;
        this.Block = Block;
    }

    @Override
    public void onBreath() {
        addToBot(new VFXAction(new InversionBeamEffect(owner.drawX)));
        addToBot(new GainBlockAction(owner,Block+(BreathCount)));
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(m, owner, new WeakPower(m,Bonus+BreathCount,false),Bonus+BreathCount));
            addToBot(new ApplyPowerAction(m, owner, new VulnerablePower(m,Bonus+BreathCount,false),Bonus+BreathCount));
        }
    }
}
