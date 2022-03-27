package theDragonknight.CardMods;

import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AddIconToDescriptionMod extends AbstractCardModifier {
    public static final String DAMAGE = "!D!";
    public static final String BLOCK = "!B!";
    public static final String MAGIC = "!M!";

    final String searchString;
    final String iconCode;

    public AddIconToDescriptionMod(String searchString, AbstractCustomIcon icon) {
        this.searchString = searchString;
        this.iconCode = icon.cardCode();
    }

    public AddIconToDescriptionMod(String searchString, String iconCode) {
        this.searchString = searchString;
        this.iconCode = iconCode;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription.replace(searchString, searchString+" "+iconCode);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new AddIconToDescriptionMod(searchString, iconCode);
    }
}