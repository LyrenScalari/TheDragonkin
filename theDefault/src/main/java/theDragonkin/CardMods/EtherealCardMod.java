package theDragonkin.CardMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class EtherealCardMod extends AbstractCardModifier {
private int duration;
private boolean wasExhaust = false;
private UIStrings uiStrings =  CardCrawlGame.languagePack.getUIString("theDragonkin:CardmodStrings");
public EtherealCardMod (int length) {
        duration = length;
        }

@Override
public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
        }
@Override
public String modifyDescription(String rawDescription, AbstractCard card) {
        return  uiStrings.TEXT[1] + rawDescription;
        }

@Override
public AbstractCardModifier makeCopy() {
        return new  EtherealCardMod(duration);
        }
}