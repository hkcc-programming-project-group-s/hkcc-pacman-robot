package edu.hkcc.pacmanrobot.controller.pccontroller;

/**
 * Created by Winner on 18/4/2015.
 */
import edu.hkcc.pacmanrobot.controller.gamemonitor.gui.GameMonitorContentJPanel;
import edu.hkcc.pacmanrobot.controller.pccontroller.content.PauseInfo;
import edu.hkcc.pacmanrobot.controller.pccontroller.content.PcControllerSetting;
import myutils.gui.cardlayout.AbstractCardJPanel;

import java.util.Vector;

public class PcContentJPanel extends AbstractCardJPanel {

    private final PcControllerJFrame master;
    public int currentPage = 0;
    Vector<PcControllerJPanel> contents;

    public PcContentJPanel(PcControllerJFrame master) {
        super();
        this.master = master;
    }

    @Override
    protected void myInit() {
        contents = new Vector<>();
        contents.add(new PcControllerSetting(master));
        contents.add(new PauseInfo(master));



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

    public void resume() {
        if (canPrev())
            currentPage--;
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
}
