package theDragonkin.cards.Deathspeaker;

import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.CardMods.Spellcaster;
import theDragonkin.powers.Deathspeaker.ManaPower;

import java.util.ArrayList;
import java.util.List;

import static theDragonkin.DragonkinMod.Spell_Orb_1024;
import static theDragonkin.DragonkinMod.Spell_Orb_512;

public abstract class AbstractSpellCard extends AbstractDeathspeakerCard {


    public AbstractSpellCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
        CardModifierManager.addModifier(this, new Spellcaster(this.costForTurn));
        setOrbTexture(Spell_Orb_512,Spell_Orb_1024);
    }
    public UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("SpellCard");
    @Override
    public void upgrade() {

    }
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> retVal = new ArrayList<>();
        retVal.add(new TooltipInfo(BaseMod.getKeywordTitle("thedragonkin:Spell"),BaseMod.getKeywordDescription("thedragonkin:Spell")));
        return retVal;
    }
    @Override
    public List<String> getCardDescriptors() {
        List<String> tags = new ArrayList<>();
        tags.add(BaseMod.getKeywordTitle("thedragonkin:Spell"));
        tags.addAll(super.getCardDescriptors());
        return tags;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractPower mana = p.getPower(ManaPower.POWER_ID);
        if ((mana == null || (mana.amount < this.costForTurn))) {
            cantUseMessage = uiStrings.TEXT[1];
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
