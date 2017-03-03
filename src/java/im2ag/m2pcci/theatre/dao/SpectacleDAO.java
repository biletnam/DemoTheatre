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

import im2ag.m2pcci.theatre.model.Spectacle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class SpectacleDAO {

    private static final String SPECT_QUERY = "SELECT * FROM lesspectacles WHERE idspectacle = ?";
    private static final String ALL_SPECT_QUERY = "SELECT * FROM lesspectacles";

    public static Spectacle getSpectacle(DataSource ds, int idSpectacle) throws SQLException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SPECT_QUERY)) {
            Spectacle res = null;
            pstmt.setInt(1, idSpectacle);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                res = new Spectacle(rs.getInt("idSpectacle"), rs.getString("titre"));
            }
            return res;
        }
    }

    public static List<Spectacle> getAllSpectacle(DataSource ds) throws SQLException {
        try (Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            List<Spectacle> res = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(ALL_SPECT_QUERY);
            while (rs.next()) {
                res.add(new Spectacle(rs.getInt("idSpectacle"), rs.getString("titre")));
            }
            return res;
        }
    }
}
