/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package christmasgifts.christmasgifts;

import java.util.ArrayList;

/**
 *
 * @author Maximiliano Herrera
 */
@FunctionalInterface
public interface IUpdateFunction {

    void update(ArrayList<ChristmasGift> gifts);
}
