package edu.hkcc.pacmanrobot.server.config.gui;

import edu.hkcc.pacmanrobot.debug.Debug;
import edu.hkcc.pacmanrobot.server.config.core.GameMonitorSAO;
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


    public boolean canPrev() {
        return hasPrev() && !hasFinishedConfig();
    }

    @Override
    protected void myInit() {
        contents = new Vector<>();
        contents.add(new SetDeviceNameContentPanel());
        contents.add(new SetRobotTypeContentPanel());
        contents.add(new PairControllerRobotContentPanel());
        contents.add(new FlashRobotContentPanel());
        contents.add(new MinimapContentPanel());
        contents.add(new PauseReasonContentPanel());
        contents.add(new PairControllerRobotContentPanel());

        for (int i = 0; i < contents.size(); i++)
            addToCards(contents.get(i), i + "");
    }

    public void next() {
        if (hasNext() && contents.get(currentPage).onLeave())
            //currentPage++;
            switchToCard(currentPage + 1);
    }

    public void prev() {
        if (hasPrev() && contents.get(currentPage).onLeave())
            //currentPage--;
            switchToCard(currentPage - 1);
    }

    public boolean hasNext() {
        return currentPage + 1 < contents.size();
    }

    public boolean isResumePage() {
        return contents.get(currentPage).getClass().equals(MinimapContentPanel.class);
    }

    public boolean isPausedPage() {
        return contents.get(currentPage).getClass().equals(PauseReasonContentPanel.class);
    }

    public boolean canResume() {
        return isPausedPage() && GameMonitorSAO.canResume();
    }

    public boolean canPause() {
        return isResumePage();
    }

    public boolean canFinish() {
        return currentPage == getFirstPageNumber(FlashRobotContentPanel.class);
    }

    public boolean pairControllerRobotPage() {
        return currentPage + 1 >= contents.size();
    }

    public boolean hasPrev() {
        return currentPage > 0;
    }


    @Override
    @Deprecated
    public void switchToCard(String label) {
        Debug.getInstance().printMessage("Content Panel switch view: " + contents.get(Integer.parseInt(label)).getClass().getSimpleName());
        super.switchToCard(label);
    }

    public void switchToCard(int index) {
        Debug.getInstance().printMessage("GUI try leave page");
        if (!contents.get(currentPage).onLeave())
            //current page not completed
            return;
        //current page completed
        Debug.getInstance().printMessage("GUI left page");
        Debug.getInstance().printMessage("Content Panel switch view: " + contents.get(index).getClass().getSimpleName());
        currentPage = index;
        super.switchToCard(index + "");
        contents.get(index).onEnter();
        revalidate();
        updateUI();
    }


    public void switchToCard(Class _class) {
        int id = getFirstPageNumber(_class);
        if (id >= 0)
            switchToCard(id + "");
    }

    public int getFirstPageNumber(Class _class) {
        for (int i = 0; i < contents.size(); i++)
            if (_class.equals(contents.get(i).getClass()))
                return i;
        return -1;
    }

    public boolean hasFinishedConfig() {
        return currentPage > getFirstPageNumber(FlashRobotContentPanel.class);
        //return currentPage >= getFirstPageNumber(PauseReasonContentPanel.class);
    }

    public boolean canStop() {
        return hasFinishedConfig();
    }

    public boolean canNext() {
        return hasNext() && !hasFinishedConfig() && !contents.get(currentPage).getClass().equals(FlashRobotContentPanel.class);
    }
}
