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
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class Place {

    /**
     * le numéro de la place
     */
    private final int noPlace;

    /**
     * le rang de la place
     */
    private final int rang;

    /**
     * la position de la place dans le rang
     */
    private final int colonne;
    
    /**
     * la categorie de la place
     */
    private final char categorie;

    /**
     * 
     * @param noPlace le numéro de la place
     * @param rang   le rang 
     * @param colonne la colonne
     * @param categorie la catégorie
     */
    public Place(int noPlace, int rang, int colonne, char categorie) {
        this.noPlace = noPlace;
        this.rang = rang;
        this.colonne = colonne;
        this.categorie = categorie;
    }

    public int getNoPlace() {
        return noPlace;
    }

    public int getRang() {
        return rang;
    }

    public int getColonne() {
        return colonne;
    }

    public char getCategorie() {
        return categorie;
    }

}
