package theDragonkin.Monsters;

import actlikeit.RazIntent.CustomIntent;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theDragonkin.cards.Dragonkin.Suffering;
import theDragonkin.characters.TheDefault;
import theDragonkin.powers.CustomBoss.*;
import theDragonkin.powers.Dragonkin.Scorchpower;
import theDragonkin.ui.CurseAttack;
import theDragonkin.ui.CurseAttackEnum;
import theDragonkin.util.TranslateSpeech;

import java.util.HashMap;
import java.util.Map;

public class TheAcolyte extends AbstractBossMonster {
    public static final String ID = "TheAcolyte";
    private static MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TheDragonkin:TheAcolyte");
    public static final String NAME  = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final int HP = 456;
    public static final int A_2_HP = 480;
    private boolean firstTurn = true;
    private int TurnCoutner = 0;
    protected boolean damageInfoSet = false;
    private static int RitualThreshold = 5;
    private final static byte CursedBlade = 0;
    private final static byte Corruption = 1;
    private final static byte Shadowburn = 2;
    private final static byte RainofFire = 3;
    private final static byte CurseofExhaustion = 4;
    private final static byte DrainLife = 5;
    private final static byte MindMaze = 6;
    private final static byte CurseofMisfortune = 7;
    private final static byte ChaosBolt = 8;


    public TheAcolyte() {
        super(NAME, "Acolyte", 400, -10.0F, -30.0F, 476.0F, 410.0F, "theDragonkinResources/images/Acolyte.png", -50.0F, 30.0F);
        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(500);
        } else {
            this.setHp(450);
        }

        this.type = EnemyType.BOSS;
        this.dialogX = -200.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        addMove(CursedBlade, Intent.ATTACK_DEBUFF, 20); // 25 Damage + Add a random curse to the players Draw pile
        addMove(Corruption, Intent.DEBUFF, -1); // for 2 turns add a Suffering (Pain but can be blocked) to your hand at the start of your turn
        addMove(Shadowburn, Intent.ATTACK_DEBUFF, 20); // 25 damage + 4 Scorch
        addMove(RainofFire, Intent.ATTACK, 10,4,true);// 10 damage 4 times.
        addMove(CurseofExhaustion, Intent.STRONG_DEBUFF, -1);// for 2 turns  at the start of your turn exhaust a card in your hand, if it's a Curse lose energy.
        addMove(MindMaze, Intent.STRONG_DEBUFF, -1);// Discard your next 2 Draws and replace them with random curses
        addMove(CurseofMisfortune , Intent.STRONG_DEBUFF, -1);//  Shuffle all curses from your exhaust pile into your draw pile player draws 1 more card next turn.
        addMove(DrainLife, Intent.ATTACK_BUFF, 25);// 35 Damage, Boss Heals equal to unblocked damage.
        addMove(ChaosBolt, CurseAttackEnum.CURSE_ATTACK_INTENT, 35);// 50 Damage, +5 Str, +5 Ritual Threshold
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this,this,new DarkRitual(this,0, RitualThreshold)));
        if (AbstractDungeon.ascensionLevel >= 9) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this,this,new CursedPower(this,this,0)));
        }
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, DIALOG[4], 0.5F, 2.0F));
    }
    @Override
    public void takeTurn() {
        DamageInfo info = null;
        int multiplier = 0;
        if(moves.containsKey(this.nextMove)) {
            EnemyMoveInfo emi = moves.get(this.nextMove);
            info = new DamageInfo(this, emi.baseDamage, DamageInfo.DamageType.NORMAL);
            multiplier = emi.multiplier;
        } else {
            info = new DamageInfo(this, 0, DamageInfo.DamageType.NORMAL);
        }
        if(damageInfoSet) {
            info = this.damage.get(0);
            this.damage.remove(0);
            damageInfoSet = false;
            if(info.base > -1) {
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        useFastAttackAnimation();
                    }
                });
            }
        } else {
            info.applyPowers(this, AbstractDungeon.player);
        }
        takeCustomTurn(info, multiplier);

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    @Override
    public void takeCustomTurn(DamageInfo info, int multiplier) {
        if (this.firstTurn) {
            if (AbstractDungeon.player.chosenClass == TheDefault.Enums.THE_DRAGONKIN) {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[3], 0.5F, 2.0F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 0.5F, 2.0F));
            }

            this.firstTurn = false;
        }
        switch(this.nextMove) {
            case CursedBlade:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, info, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy(),1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy(),1));
                if (AbstractDungeon.ascensionLevel >= 9){
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy(),1));
                }
                firstTurn = false;
                if (TurnCoutner >= 4){
                    TurnCoutner = 0;
                } else TurnCoutner ++;
                break;
            case Corruption:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,this,new ShadowCurse(AbstractDungeon.player,this,2)));
                TurnCoutner ++;
                break;
            case Shadowburn:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, info, AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,this,new Scorchpower(AbstractDungeon.player,this,4)));
                TurnCoutner ++;
                break;
            case RainofFire:
                for (int i = 0; i < multiplier; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, info, AbstractGameAction.AttackEffect.FIRE));
                }
                TurnCoutner ++;
                break;
            case CurseofExhaustion:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,this,new Exhaustion(AbstractDungeon.player,this,2)));
                TurnCoutner ++;
                break;
            case MindMaze:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,this,new MindMaze(AbstractDungeon.player,this,2)));
                TurnCoutner ++;
                break;
            case CurseofMisfortune:
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
                if (c.type == AbstractCard.CardType.CURSE){
                    addToBot(new AbstractGameAction() {
                        public void update() {
                            c.unfadeOut();
                            AbstractDungeon.actionManager.addToBottom(new ShowCardAction(c));
                            AbstractDungeon.player.exhaustPile.group.remove(c);
                            AbstractDungeon.player.drawPile.addToRandomSpot(c);
                            isDone = true;
                        }
                    });
                }
            }
            TurnCoutner ++;
            break;
            case DrainLife:
                AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, info, AbstractGameAction.AttackEffect.FIRE));
                TurnCoutner ++;
                break;
            case ChaosBolt:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, info, AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy(),1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy(),1));
                if (AbstractDungeon.ascensionLevel >= 9){
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(AbstractDungeon.returnRandomCurse().makeStatEquivalentCopy(),1));
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this,this,new StrengthPower(this,5),5));
                ((DarkRitual)this.getPower(DarkRitual.POWER_ID)).increaseLimit(5);
                TurnCoutner ++;
                break;
        }
    }


    public void changeState(String stateName) {
        byte var3 = -1;
        switch(stateName.hashCode()) {
            case 1941037640:
                if (stateName.equals("ATTACK")) {
                    var3 = 0;
                }
            default:
                switch(var3) {
                    case 0:
                        this.state.setAnimation(0, "Attack", false);
                        this.state.addAnimation(0, "Idle", true, 0.0F);
                    default:
                }
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
    }

    @Override
    protected void getMove(int num) {
        System.out.println(num);
        if (firstTurn){
            setMoveShortcut(CursedBlade,MOVES[0]);
        }
        else if (this.hasPower(DarkRitual.POWER_ID)){
            if (this.getPower(DarkRitual.POWER_ID).amount >= ((TwoAmountPower)this.getPower(DarkRitual.POWER_ID)).amount2){
                setMoveShortcut(ChaosBolt,MOVES[8]);
            }
            else if (this.getPower(DarkRitual.POWER_ID).amount < RitualThreshold/2){
                if (num < 30){
                    setMoveShortcut(Corruption,MOVES[1]);
                } else if (num > 25 && num < 50){
                    setMoveShortcut(Shadowburn,MOVES[2]);
                } else if (num > 50 && num < 70){
                    setMoveShortcut(RainofFire,MOVES[5]);
                }else if (num > 70 && num < 90) {
                    setMoveShortcut(DrainLife, MOVES[7]);
                } else if (num > 90){
                    setMoveShortcut(MindMaze,MOVES[4]);
                }
            } else if (this.getPower(DarkRitual.POWER_ID).amount >= RitualThreshold/2) {
                if (num < 25){
                    setMoveShortcut(RainofFire,MOVES[5]);
                } else if (num > 30 && num < 50){
                    setMoveShortcut(MindMaze,MOVES[4]);
                } else if (num > 50 && num < 70){
                    setMoveShortcut(DrainLife,MOVES[7]);
                } else if (num > 70 && num < 90){
                    int exhaustedcurses = 0;
                    for (AbstractCard c : AbstractDungeon.player.exhaustPile.group){
                        if (c.type == AbstractCard.CardType.CURSE){
                            exhaustedcurses++;
                        }
                    }
                    if (exhaustedcurses > 4) {
                        setMoveShortcut(CurseofMisfortune, MOVES[3]);
                    } else setMoveShortcut(RainofFire,MOVES[5]);
                } else if (num > 90){
                    setMoveShortcut(CurseofExhaustion,MOVES[6]);
                }
            }
        }
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            super.die();
            this.onBossVictoryLogic();
            this.onFinalBossVictoryLogic();
        }

    }
}