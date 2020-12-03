/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package christmasgifts.UI;

import Interfaces.IObserverPush;
import Interfaces.IUpdateFunction;
import christmasgifts.christmasgifts.ChristmasGift;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Maximiliano Herrera
 */
public class UIComponentObserverPush implements IObserverPush {

    private IUpdateFunction function;

    public UIComponentObserverPush(IUpdateFunction function) {
        this.function = function;
    }

    @Override
    public void update(ArrayList<ChristmasGift> gifts) {
        SwingUtilities.invokeLater(() -> function.update(gifts));
    }
}
