package theDragonkin.powers.WindWalker;

import IconsAddon.blockModifiers.AbstractBlockModifier;
import IconsAddon.damageModifiers.AbstractDamageModifier;
import IconsAddon.icons.DrainIcon;
import IconsAddon.powers.OnCreateBlockContainerPower;
import IconsAddon.relics.DamageModApplyingRelic;
import IconsAddon.util.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theDragonkin.CustomTags;
import theDragonkin.DamageModifiers.SpiritDamage;
import theDragonkin.DamageModifiers.BlockModifiers.SpiritBlock;
import theDragonkin.DragonkinMod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SpiritChakraPower extends AbstractChakraPower implements OnCreateBlockContainerPower, DamageModApplyingRelic {
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
        this.region128 = DrainIcon.get().getAtlasTexture();
        this.region48 = DrainIcon.get().getAtlasTexture();
        type = PowerType.BUFF;
        isTurnBased = false;
        // We load those txtures here.

        updateDescription();
    }
    public AbstractDamageModifier GetChakraType(){
        return new SpiritDamage(false);
    }
    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0];

    }
    @Override
    public void onCreateBlockContainer(HashSet<AbstractBlockModifier> hashSet, AbstractCard card) {
        if (card != null && (card.hasTag(AbstractCard.CardTags.STARTER_DEFEND) || card.hasTag(CustomTags.Defend) && hashSet.isEmpty())) {
            hashSet.add(new SpiritBlock(true));
        }
    }


    @Override
    public void onAddedDamageModsToDamageInfo(DamageInfo damageInfo, AbstractCard abstractCard) {

    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, AbstractCard abstractCard, List<AbstractDamageModifier> list) {
        return false;
    }

    @Override
    public ArrayList<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, AbstractCard abstractCard, List<AbstractDamageModifier> list) {
        return null;
    }
}
