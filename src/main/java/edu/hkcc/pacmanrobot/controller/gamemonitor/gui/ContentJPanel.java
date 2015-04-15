package edu.hkcc.pacmanrobot.controller.gamemonitor.gui;

import myutils.gui.cardlayout.AbstractCardJPanel;

import java.util.Vector;

public class ContentJPanel extends AbstractCardJPanel {

    private final GameMonitorJFrame master;
    public int currentPage = 0;
    Vector<GameMonitorContentJPanel> contents;

    public ContentJPanel(GameMonitorJFrame master) {
        super();
        this.master = master;
    }

    @Override
    protected void myInit() {
        contents = new Vector<>();
        contents.add(new SetDeviceName());
        contents.add(new SetDeviceInfo());
        contents.add(new PairControllerRobotJPanel());
        contents.add(new PositionSetting());

        for (int i = 0; i < contents.size(); i++)
            addToCards(contents.get(i), i + "");
    }

    public boolean canNext() {
        //contents[currentPage].
        return hasNext();
    }

    public void next() {
        if (canNext()&&contents.get(currentPage).onLeave())
            currentPage++;
        switchToCard(currentPage + "");
    }

    public void prev() {
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
