package demonwarrior;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfix;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.Field;

public class IroncladRenamePatch {

    private static final Logger logger = LogManager.getLogger(IroncladRenamePatch.class);
    public static final String NEW_NAME = "Demon Warrior";
    public static final String NEW_NAME_UPPER = "DEMON WARRIOR";

    public static void renameIronclad() {
        try {
            Field csField = CardCrawlGame.class.getDeclaredField("characterStrings");
            csField.setAccessible(true);
            java.util.HashMap<String, CharacterStrings> csMap =
                (java.util.HashMap<String, CharacterStrings>) csField.get(null);
            if (csMap != null && csMap.containsKey("Ironclad")) {
                CharacterStrings cs = csMap.get("Ironclad");
                Field namesField = CharacterStrings.class.getDeclaredField("NAMES");
                namesField.setAccessible(true);
                String[] names = (String[]) namesField.get(cs);
                if (names != null && names.length > 0) {
                    names[0] = NEW_NAME;
                    logger.info("DemonWarriorMod: Renamed Ironclad -> Demon Warrior");
                }
            }
        } catch (Exception e) {
            logger.warn("DemonWarriorMod: rename failed: " + e.getMessage());
        }
    }

    @SpirePatch(clz = Ironclad.class, method = SpirePatch.CONSTRUCTOR)
    public static class PatchConstructor {
        @SpirePostfix
        public static void Postfix(Ironclad __instance) {
            try {
                Field f = com.megacrit.cardcrawl.core.AbstractCreature.class.getDeclaredField("name");
                f.setAccessible(true);
                f.set(__instance, NEW_NAME);
            } catch (Exception e) {
                logger.warn("DemonWarriorMod: constructor patch failed: " + e.getMessage());
            }
        }
    }

    @SpirePatch(clz = Ironclad.class, method = "getName")
    public static class PatchGetName {
        @SpirePostfix
        public static String Postfix(String result, Ironclad __instance) {
            return NEW_NAME;
        }
    }

    @SpirePatch(clz = Ironclad.class, method = "getTitle")
    public static class PatchGetTitle {
        @SpirePostfix
        public static String Postfix(String result, Ironclad __instance) {
            return NEW_NAME_UPPER;
        }
    }
