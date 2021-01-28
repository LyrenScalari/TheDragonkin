package theDragonkin.powers.Dragonkin.DragonBreaths;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ShineSparkleEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import theDragonkin.CardMods.RetainCardMod;
import theDragonkin.DefaultMod;

public class MeteorStormEffect extends AbstractDragonBreathPower{
    public static final String POWER_ID = DefaultMod.makeID("MeteorStorm");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public MeteorStormEffect (int Damage, int Block, AbstractCard source){
        sourcecard = source;
        name = NAME;
        ID = POWER_ID;
        amount = Damage + ((BreathCount -1) * 2);
        amount3 = Block + ((BreathCount -1));
    }

    @Override
    public void onBreath(){
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!m.isDeadOrEscaped()) {
                addToBot(new VFXAction(new VerticalImpactEffect(m.drawX, m.drawY)));
                addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount,
                        DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addToBot(new WaitAction((float) 0.02));
            }
        }
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!m.isDeadOrEscaped()) {
                addToBot(new VFXAction(new VerticalImpactEffect(m.drawX, m.drawY)));
                addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount,
                        DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addToBot(new WaitAction((float) 0.02));
            }
        }
        addToBot(new GainBlockAction(owner,amount3));
        addToBot(new GainBlockAction(owner,amount3));
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c.uuid.equals(sourcecard.uuid)){
                addToBot(new AbstractGameAction() {
                    public void update() {
                        CardModifierManager.addModifier(c,new RetainCardMod(1));
                        isDone = true;
                    }
                });
                this.addToBot(new DiscardToHandAction(c));
            }
        }
    }
}