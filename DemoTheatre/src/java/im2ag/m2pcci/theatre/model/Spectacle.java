/*
 * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package im2ag.m2pcci.theatre.model;

/**
 * Représente un spectacle. 
 * 
 * TODO : rajouter la possibilité de prendre en compte d'éventuelles informations
 * supplémentaires pour un spectacle, par exmple résumé, artistes ....
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class Spectacle {

    /**
     * identifiant du spectacle
     */
    private final int id;

    /**
     * le titre du spectacle
     */
    private final String titre;

    /**
     *
     * @param id identifiant du spectacle dans la BD
     * @param titre le titre du spectacle
     */
    public Spectacle(int id, String titre) {
        this.id = id;
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

}
