package theDragonkin.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import theDragonkin.patches.Phlyactery.PhlyacteryField;
import theDragonkin.powers.Deathspeaker.SoulShield;
import theDragonkin.util.RuneTextEffect;

public class SiphonAction extends AbstractGameAction {
    private int anInt;

    public SiphonAction(AbstractMonster m, int amount) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.anInt = amount;
        this.target = m;
        this.source = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            addToTop(new GainBloodPointsAction(source,source,anInt));
            addToTop(new LoseHPAction(target,source,anInt));
            AbstractDungeon.effectsQueue.add(new RuneTextEffect(target.drawX,target.drawY + target.hb_h/2,"Sosaal"));
        }

        this.tickDuration();
    }
}