package theDragonkin.potions.Gremory;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theDragonkin.CustomTags;
import theDragonkin.DefaultMod;
import theDragonkin.actions.CustomDiscoveryAction;
import theDragonkin.characters.TheGremory;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class ShadowofZahras extends AbstractPotion {


    public static final String POTION_ID = theDragonkin.DefaultMod.makeID("ShadowofZahras");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private static CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public ShadowofZahras () {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SNECKO, PotionColor.SNECKO);
        this.labOutlineColor = DefaultMod.GREMORY_PURPLE;
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        group.group = (ArrayList<AbstractCard>) CardLibrary.getAllCards()
                .stream()
                .filter(c -> c.color == TheGremory.Enums.Gremory_Purple_Color)
                .filter(c -> c.hasTag(CustomTags.Dark))
                .filter(c -> c.hasTag(CustomTags.Light))
                .filter(c -> !c.rarity.equals(AbstractCard.CardRarity.BASIC))
                .collect(Collectors.toList());
        for (AbstractCard c : group.group){
            if (c.hasTag(CustomTags.Light) && c instanceof BranchingUpgradesCard){
                group.removeCard(c);
                ((BranchingUpgradesCard) c).isBranchUpgrade();
                c.upgrade();
                group.addToBottom(c);
            }
        }
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new CustomDiscoveryAction(group,4,false, card -> {
                addToBot(new AddCardToDeckAction(card));
                addToBot(new MakeTempCardInHandAction(card));
            }));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ShadowofZahras();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }

    public void upgradePotion()
    {
        potency += 1;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}
