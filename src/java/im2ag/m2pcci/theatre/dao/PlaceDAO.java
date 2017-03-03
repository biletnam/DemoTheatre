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
package im2ag.m2pcci.theatre.dao;

import im2ag.m2pcci.theatre.model.Place;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.sql.DataSource;

/**
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class PlaceDAO {

    private static final String PLACES_VENDUES = "SELECT idplace, categorie, rang,  colonne FROM places_vendues NATURAL JOIN places WHERE idspectacle = ?";

    private static final String ACHETER_PLACE = "INSERT INTO places_vendues (idplace, idspectacle) VALUES (?, ?)";

    public static List<Place> placesVendues(DataSource ds, int spectacleId) throws SQLException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(PLACES_VENDUES)) {
            pstmt.setInt(1, spectacleId);
            ResultSet rs = pstmt.executeQuery();
            List<Place> places = new ArrayList<>();
            while (rs.next()) {
                places.add(new Place(rs.getInt("idplace"), rs.getInt("rang"), rs.getInt("colonne"), rs.getString("categorie").charAt(0)));
            }
            return places;
        }
    }

    public static String placesVenduesAsJSON(DataSource ds, int spectacleId) throws SQLException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(PLACES_VENDUES)) {
            pstmt.setInt(1, spectacleId);
            ResultSet rs = pstmt.executeQuery();
            StringWriter sw = new StringWriter();
            JsonGenerator gen = Json.createGenerator(sw);
            gen.writeStartObject()
                    .writeStartArray("bookings");
            while (rs.next()) {
                gen.writeStartObject()
                        .write("placeId", rs.getInt("idplace"))
                        .write("rang", rs.getInt("rang"))
                        .write("colonne", rs.getInt("colonne"))
                        .writeEnd();
            }
            gen.writeEnd()
                    .writeEnd();
            gen.close();
            return sw.toString();
        }
    }

    /**
     *
     * @param ds
     * @param idSpectacle
     * @param placesIds
     * @throws SQLException
     */
    public static void acheterPlace(DataSource ds, int idSpectacle, int[] placesIds) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(ACHETER_PLACE)) {
                conn.setAutoCommit(false);
                for (int idPlace : placesIds) {
                    pstmt.setInt(1, idPlace);
                    pstmt.setInt(2, idSpectacle);
                    pstmt.addBatch();
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
}
