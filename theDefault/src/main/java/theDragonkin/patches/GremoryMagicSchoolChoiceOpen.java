package theDragonkin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theDragonkin.CustomTags;
import theDragonkin.cards.Gremory.*;
import theDragonkin.characters.TheGremory;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static theDragonkin.characters.TheGremory.Enums.THE_GREMORY;

@SpirePatch(clz = NeowEvent.class, method = "blessing")
@SpirePatch(clz = NeowEvent.class, method = "miniBlessing")
public class GremoryMagicSchoolChoiceOpen {
    public static void Prefix(AbstractEvent __instance) {
        CardGroup SchoolChoices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        SchoolChoices.group = (ArrayList<AbstractCard>) CardLibrary.getAllCards()
                .stream()
                .filter(c -> c.color == AbstractCard.CardColor.COLORLESS)
                .filter(c -> c.hasTag(CustomTags.School))
                .filter(c -> c.rarity.equals(AbstractCard.CardRarity.SPECIAL))
                .collect(Collectors.toList());
        if (AbstractDungeon.player.chosenClass.equals(THE_GREMORY)) {
            GremoryMagicSchoolSelections.coderunning = true;
            GremoryMagicSchoolSelections.firstChoice = "";
            GremoryMagicSchoolSelections.secondChoice = "";
            GremoryMagicSchoolSelections.choicecount = 0;
            AbstractDungeon.gridSelectScreen.open(SchoolChoices, 2, TheGremory.TEXT[3], false);
        }
    }
}
