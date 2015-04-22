package edu.hkcc.pacmanrobot.controller.pccontroller;

/**
 * Created by Winner on 18/4/2015.
 */

import edu.hkcc.pacmanrobot.controller.pccontroller.content.PauseResumable;
import edu.hkcc.pacmanrobot.controller.pccontroller.content.PauseUnresumable;
import edu.hkcc.pacmanrobot.controller.pccontroller.content.PcControllerSetting;
import edu.hkcc.pacmanrobot.controller.pccontroller.content.PcController_contentJPanel;
import myutils.gui.cardlayout.AbstractCardJPanel;

import java.util.Vector;

public class PcController_centerJPanel extends AbstractCardJPanel {

    private final PcControllerJFrame master;
    public int currentPage = 0;
    Vector<PcController_contentJPanel> contents;

    public PcController_centerJPanel(PcControllerJFrame master) {
        super();
        this.master = master;
    }

    @Override
    protected void myInit() {
        contents = new Vector<>();
        contents.add(new PcControllerSetting(master));
        contents.add(new PauseUnresumable(master));
        contents.add(new PauseResumable(master));

        for (int i = 0; i < contents.size(); i++)
            addToCards(contents.get(i), i + "");
    }

    public boolean canNext() {
        //contents[currentPage].
        return hasNext();
    }

    public void pause() {
        if (canNext())
            currentPage++;
        switchToCard(currentPage + "");
    }

    public void playing() {
        if (canPrev())
            currentPage = currentPage - 2;
        switchToCard(currentPage + "");
    }

    public void resume() {
        if (canNext() && canPrev())
            currentPage++;
        else if (canNext() && !canPrev())
            currentPage = currentPage + 2;
        switchToCard(currentPage + "");
    }

    public boolean canPrev() {
        //check content
        return hasPrev();
    }

    public boolean hasNext() {
        return currentPage + 1 < contents.size();
    }

    public boolean hasPrev() {
        return currentPage > 0;
    }

    public boolean unresumePage() {
        return hasNext() && hasPrev();
    }

    public boolean stopPage() {
        return hasPrev() || hasNext();
    }

    public boolean resumePage() {
        return !hasPrev();
    }

}
