package core.game.dialog;

import core.game.structures.environment.GoldVein;
import core.game.structures.environment.Tree;
import core.game.unit.actions.DoGoHome;
import core.helper.Translator;

import java.util.ArrayList;
import java.util.List;

public class CitizenDialog implements Speakable {

    private final Dialog dialog;

    private static List<String> dialogOpenings;

    public CitizenDialog() {
        dialogOpenings = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            dialogOpenings.add(Translator.translate("citizen.dialog.opening." + i));
        }

        dialog = new Dialog(getRandomOpeningDialog());

        String textCollect = Translator.translate("citizen.dialog.collect.question");
        String optionCollect = Translator.translate("citizen.dialog.option.collect");
        Dialog collect = new Dialog(textCollect, optionCollect, dialog, Dialog.Action.TALK);

        String textGoHome = Translator.translate("citizen.dialog.option.goHome");
        Dialog goHome = new Dialog("", textGoHome, dialog, Dialog.Action.DO_NPC_ACTION, new DoGoHome());
        String optionCollectResource = Translator.translate("citizen.dialog.option.resourceCollect");

        String optionWood = String.format(optionCollectResource, Translator.translate("resource.wood.name"));
        collect.addOption(new Dialog("", optionWood, collect, Dialog.Action.DO_NPC_ACTION, Tree.class));

        String optionGold = String.format(optionCollectResource, Translator.translate("resource.gold.name"));
        collect.addOption(new Dialog("", optionGold, collect, Dialog.Action.DO_NPC_ACTION, GoldVein.class));

        collect.addOption(Dialog.returnOption(dialog));

        Dialog quit = Dialog.quitOption(dialog);

        dialog.addOption(collect);
        dialog.addOption(goHome);
        dialog.addOption(quit);
    }

    private String getRandomOpeningDialog() {
        int rand = (int) (Math.random() * dialogOpenings.size());
        return dialogOpenings.get(rand);
    }

    public Dialog speak() {
        return dialog;
    }
}