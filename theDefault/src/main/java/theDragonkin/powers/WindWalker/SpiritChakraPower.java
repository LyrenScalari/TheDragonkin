package theDragonkin.powers.WindWalker;


import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.relics.DamageModApplyingRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnCreateBlockInstanceRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.Icons.SpiritIcon;
import theDragonkin.DamageModifiers.SpiritDamage;
import theDragonkin.DamageModifiers.BlockModifiers.SpiritBlock;
import theDragonkin.DragonkinMod;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SpiritChakraPower extends AbstractChakraPower implements OnCreateBlockInstanceRelic, DamageModApplyingRelic {
    public AbstractCreature source;

    public static final String POWER_ID = DragonkinMod.makeID("SpiritChakra");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public SpiritChakraPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.source = source;
        this.region128 = SpiritIcon.get().region;
        this.region48 = SpiritIcon.get().region;
        type = PowerType.BUFF;
        isTurnBased = false;
        // We load those txtures here.

        updateDescription();
    }
    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];

    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return false;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return null;
    }

    @Override
    public void onCreateBlockInstance(HashSet<AbstractBlockModifier> hashSet, Object o) {
       /* if (card != null && (card.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || card.hasTag(CustomTags.Defend) && hashSet.isEmpty())) {
            hashSet.add(new SpiritBlock(true));
        }
*/
    }
}
