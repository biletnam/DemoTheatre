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
package im2ag.m2pcci.theatre.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * intialise la tables des places à partir du fichier de description.
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class PlacesInitialiser {

    final static Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * Requête pour insérer un plzce dans la table PLACES
     */
    private static final String INSERT_PLACE_QUERY = "INSERT INTO places (idplace, categorie, rang, colonne) VALUES (?, ?, ?, ?)";

    /**
     * intialise la table des des places à partir du fichier texte donnan la
     * carte de la salle de spectacle.
     *
     * @param ds la source de données pour les connexions jdbc
     * @param filePath le nom du fichier texte contenant la carte de la salle
     * @throws IOException
     * @throws SQLException
     */
    public static void initPlaces(DataSource ds, String filePath) throws
            IOException, SQLException {

        Path path = Paths.get(filePath);
        try (BufferedReader reader = Files.newBufferedReader(path, ENCODING);
                Connection conn = ds.getConnection()) {

            try (PreparedStatement pstmt = conn.prepareStatement(INSERT_PLACE_QUERY)) {
                String line;
                int noPlace = 1;
                int noRang = 1;
                conn.setAutoCommit(false);
                while ((line = reader.readLine()) != null) {
                    for (int noCol = 0; noCol < line.length(); noCol++) {
                        if (line.charAt(noCol) != '_') {
                            pstmt.setInt(1, noPlace);
                            pstmt.setString(2, line.substring(noCol, noCol + 1));
                            pstmt.setInt(3, noRang);
                            pstmt.setInt(4, noCol + 1);
                            pstmt.addBatch();
                            noPlace++;
                        }
                    }
                    noRang++;
                }
                pstmt.executeBatch();
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            DataSource ds = new TestDataSource();
            initPlaces(ds, "test/data/carte.txt");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            System.out.println("\ncause mère:");
            ex.getNextException().printStackTrace(System.err);
        }
    }
}
