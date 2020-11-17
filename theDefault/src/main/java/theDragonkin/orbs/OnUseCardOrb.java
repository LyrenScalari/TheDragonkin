package theDragonkin.orbs;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnUseCardOrb {
    void onUseCardOrb(AbstractCard card, UseCardAction action);
}
