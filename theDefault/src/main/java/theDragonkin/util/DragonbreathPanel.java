package theDragonkin.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theDragonkin.cards.Dragonkin.*;
import theDragonkin.powers.Dragonkin.DragonBreaths.*;
import theDragonkin.powers.Dragonkin.DragonBreaths.FlameBreath;

public class DragonbreathPanel extends EasyInfoDisplayPanel {

    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theDragonkin:DragonbreathDisplay");
    private static UIStrings breathtext = CardCrawlGame.languagePack.getUIString("theDragonkin:UIText");

    public DragonbreathPanel() {
        super(0, 800, 300);
    } // NOTE: X, Y, Width are all multipled by settings.scale on constructor, so use values like this.

    @Override
    public String getTitle() {
        int remainingDelay = 1000000000;
        for (AbstractPower p : AbstractDungeon.player.powers){
            if (p instanceof AbstractDragonBreathPower){
                if (((AbstractDragonBreathPower) p).amount2 < remainingDelay){
                    remainingDelay = ((AbstractDragonBreathPower) p).amount2;
                }
            }
        }
        return uiStrings.TEXT[0] + remainingDelay;
    }

    @Override
    public String getDescription() {
        String s = "";
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractDragonBreathPower) {
                CardStrings strings = CardCrawlGame.languagePack.getCardStrings(((AbstractDragonBreathPower)p).sourcecard.cardID);
                if (s !=""){
                   s += " NL " + strings.NAME;
                    if ( p instanceof DivineWindEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];

                    } else if ( p instanceof FlameBreath){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];

                    } else if ( p instanceof CorrosiveBreathEffect || p instanceof MiasmicBreathEffect){
                        s+= breathtext.TEXT[7] +((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[9];

                    } else if (p instanceof BlazingBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount +  breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[1];

                    } else if ( p instanceof AcidBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];

                    } else if ( p instanceof AshBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];

                    } else if ( p instanceof DivineBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount5 + breathtext.TEXT[6];

                    } else if ( p instanceof MeteorStormEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[10];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[11];

                    } else if ( p instanceof PrismaticBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[3];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount5 + breathtext.TEXT[6];

                    } else if ( p instanceof RevelationBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[3];

                    } else if ( p instanceof SandBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[3];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];

                    } else if ( p instanceof TwinBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[1];

                    } else if ( p instanceof AkatirnsDecreeEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[5];

                    } else if ( p instanceof MoltenBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[1];

                    } else if ( p instanceof SmaugsSmogEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[5];

                    } else if ( p instanceof VoiceofOrderEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[5];
                    }
                } else{
                    s += strings.NAME;
                    if ( p instanceof DivineWindEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];

                    } else if ( p instanceof FlameBreath){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];

                    } else if ( p instanceof CorrosiveBreathEffect || p instanceof MiasmicBreathEffect){
                        s+= breathtext.TEXT[7] +((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[9];

                    } else if (p instanceof BlazingBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount +  breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[1];

                    } else if ( p instanceof AcidBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];

                    } else if ( p instanceof AshBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];

                    } else if ( p instanceof DivineBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount5 + breathtext.TEXT[6];

                    } else if ( p instanceof MeteorStormEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[10];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[11];

                    } else if ( p instanceof PrismaticBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[3];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount5 + breathtext.TEXT[6];

                    } else if ( p instanceof RevelationBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[3];

                    } else if ( p instanceof SandBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[3];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];

                    } else if ( p instanceof TwinBreathEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[1];

                    } else if ( p instanceof AkatirnsDecreeEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[5];

                    } else if ( p instanceof MoltenBreathEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[1];

                    } else if ( p instanceof SmaugsSmogEffect){
                        s+= breathtext.TEXT[7] + p.amount + breathtext.TEXT[0];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[5];

                    } else if ( p instanceof VoiceofOrderEffect){
                        s+= breathtext.TEXT[7] + ((AbstractDragonBreathPower) p).amount3 + breathtext.TEXT[2];
                        s+= " NL " + breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[4];
                        s+= breathtext.TEXT[8] + ((AbstractDragonBreathPower) p).amount4 + breathtext.TEXT[5];
                    }
                }
            }
        }
        if (s == "") {
            return "NORENDER";
        }
        return s;
    }

    @Override
    public RENDER_TIMING getTiming() {
        return RENDER_TIMING.TIMING_PLAYER_RENDER;
    }
}
