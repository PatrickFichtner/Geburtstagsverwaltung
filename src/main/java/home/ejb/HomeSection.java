/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.ejb;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author belizbalim
 */
public class HomeSection {
    
    private String label = "";
    private List<HomeTile> tiles = new ArrayList<>();

    public HomeSection() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<HomeTile> getTiles() {
        return tiles;
    }

    public void setTiles(List<HomeTile> tiles) {
        this.tiles = tiles;
    }
}
