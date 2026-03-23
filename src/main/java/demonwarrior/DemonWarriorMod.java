package demonwarrior;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class DemonWarriorMod implements PostInitializeSubscriber, EditStringsSubscriber {

    public static final Logger logger = LogManager.getLogger(DemonWarriorMod.class);

    public DemonWarriorMod() {
        BaseMod.subscribe(this);
        logger.info("DemonWarriorMod: subscribed.");
    }

    public static void initialize() {
        new DemonWarriorMod();
    }

    @Override
    public void receivePostInitialize() {
        IroncladRenamePatch.renameIronclad();
    }

    @Override
    public void receiveEditStrings() {}
}
Commit.
