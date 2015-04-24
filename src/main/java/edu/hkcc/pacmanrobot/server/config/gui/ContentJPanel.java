package edu.hkcc.pacmanrobot.server.config.gui;

import edu.hkcc.pacmanrobot.server.config.gui.content.*;
import myutils.gui.cardlayout.AbstractCardJPanel;

import java.util.Vector;

public class ContentJPanel extends AbstractCardJPanel {

    private final GameMonitorJFrame master;
    public int currentPage = 0;
    Vector<AbstractContentPanel> contents;

    public ContentJPanel(GameMonitorJFrame master) {
        super();
        this.master = master;
    }

    @Override
    protected void myInit() {
        contents = new Vector<>();
        contents.add(new SetDeviceNameContentPanel(master));
        contents.add(new SetRobotTypeContentPanel(master));
        PairControllerRobotContentPanel pairControllerRobotContentPanel = new PairControllerRobotContentPanel(master);
        //GameMonitorSAO.pairControllerRobotJPanel_$eq(pairControllerRobotContentPanel);
        contents.add(pairControllerRobotContentPanel);
        contents.add(new SetRobotPositionContentPanel(master));
        contents.add(new PauseReasonContentPanel(master));
        contents.add(pairControllerRobotContentPanel);

        for (int i = 0; i < contents.size(); i++)
            addToCards(contents.get(i), i + "");
    }

    public boolean canNext() {
        //contents[currentPage].
        return hasNext();
    }

    public void next() {
        if (canNext() && contents.get(currentPage).onLeave())
            currentPage++;
        switchToCard(currentPage + "");
    }

    public void prev() {
        if (canPrev() && contents.get(currentPage).onLeave())
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

    public boolean resumePage() {
        return (currentPage + 2 < contents.size());
    }

    public boolean finish() {
        return (currentPage + 3 >= contents.size()) || (currentPage + 1 >= contents.size());
    }

    public boolean pairControllerRobotPage() {
        return currentPage + 1 >= contents.size();
    }

    public boolean hasPrev() {
        return currentPage > 0;
    }
}
