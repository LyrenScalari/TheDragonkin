package theDragonkin.patches;
/*import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theDragonkin.cards.Gremory.Attacks.Magic.*;
import theDragonkin.cards.Gremory.Choices.*;
import theDragonkin.CustomTags;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static theDragonkin.characters.TheGremory.Enums.THE_GREMORY;

@SpirePatch(clz = NeowEvent.class, method = "update")
public class GremoryMagicSchoolSelections {
    static int choicecount = 0;
    static boolean coderunning = true;
    static String firstChoice = "";
    static String secondChoice = "";

    public static void Prefix(AbstractEvent __instance) {
        CardGroup SchoolChoices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        SchoolChoices.group = (ArrayList<AbstractCard>) CardLibrary.getAllCards()
                .stream()
                .filter(c -> c.color == AbstractCard.CardColor.COLORLESS)
                .filter(c -> c.hasTag(CustomTags.School))
                .filter(c -> c.rarity.equals(AbstractCard.CardRarity.SPECIAL))
                .collect(Collectors.toList());
        if (AbstractDungeon.player.chosenClass.equals(THE_GREMORY)) {
            if (choicecount == 0) {
                if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    if (choicecount >= 2) {
                        coderunning = false;
                    }
                    if (coderunning) {
                        if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID.equals(SchoolFire.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            firstChoice = "fire";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID.equals(SchoolIce.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            firstChoice = "ice";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID.equals(SchoolThunder.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            firstChoice = "thunder";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID.equals(SchoolWind.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            firstChoice = "wind";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID.equals(SchoolDark.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            firstChoice = "dark";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID.equals(SchoolLight.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            firstChoice = "light";
                            choicecount += 1;
                        }
                        if (AbstractDungeon.gridSelectScreen.selectedCards.get(1).cardID.equals(SchoolFire.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            secondChoice = "fire";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(1).cardID.equals(SchoolIce.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            secondChoice = "ice";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(1).cardID.equals(SchoolThunder.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            secondChoice = "thunder";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(1).cardID.equals(SchoolWind.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            secondChoice = "wind";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(1).cardID.equals(SchoolDark.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            secondChoice = "dark";
                            choicecount += 1;
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(1).cardID.equals(SchoolLight.ID)) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            secondChoice = "light";
                            choicecount += 1;
                        }
                        if (AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID.equals(SchoolDouble.ID)) {
                            if (secondChoice.equals("fire")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                firstChoice = "fire";
                                choicecount += 1;
                            } else if (secondChoice.equals("light")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                firstChoice = "light";
                                choicecount += 1;
                            } else if (secondChoice.equals("dark")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                firstChoice = "dark";
                                choicecount += 1;
                            } else if (secondChoice.equals("ice")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                firstChoice = "ice";
                                choicecount += 1;
                            } else if (secondChoice.equals("wind")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                secondChoice = "wind";
                                choicecount += 1;
                            } else if (secondChoice.equals("thunder")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                firstChoice = "thunder";
                                choicecount += 1;
                            }
                        } else if (AbstractDungeon.gridSelectScreen.selectedCards.get(1).cardID.equals(SchoolDouble.ID)) {
                            if (firstChoice.equals("fire")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Fire(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                secondChoice = "fire";
                                choicecount += 1;
                            } else if (firstChoice.equals("light")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Nosferatu(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                secondChoice = "light";
                                choicecount += 1;
                            } else if (firstChoice.equals("dark")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Miasma(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                secondChoice = "dark";
                                choicecount += 1;
                            } else if (firstChoice.equals("ice")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Icicle(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                secondChoice = "ice";
                                choicecount += 1;
                            } else if (firstChoice.equals("wind")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Wind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                secondChoice = "wind";
                                choicecount += 1;
                            } else if (firstChoice.equals("thunder")) {
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Thunder(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                                secondChoice = "thunder";
                                choicecount += 1;
                            }
                        }
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    }
                }
            }
        }
  */
