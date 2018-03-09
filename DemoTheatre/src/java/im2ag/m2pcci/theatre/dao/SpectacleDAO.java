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
 * DAO proposant différentes méthodes concernant les spectacles proposés.
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class SpectacleDAO {

    private static final String SPECT_QUERY = "SELECT * FROM lesspectacles WHERE idspectacle = ?";
    private static final String ALL_SPECT_QUERY = "SELECT * FROM lesspectacles";

    /**
     * Retourne les informations détaillées concernant un spectale donné
     * TODO : rajouter la prise en compte d'informations supplémentaires pour 
     * un spectacle, par exmple résumé, artistes ....
     *
     * @param ds la datasource pour les connexions à la BD
     * @param idSpectacle l'idnetifiant du spectale
     * @return l'objet Spectacle contenant les informations du spectacle
     * @throws SQLException si un problème avec la BD.
     */
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

    /**
     * Retourne une liste de tous les spectacles présent dans la base, avec pour chaque
     * spectacle une information minimale (identifiant, titre).
     * 
     * @param ds la datasource pour les connexions à la BD
     * @return La liste des Spectacles avec uniquement leur titre et numéro
     * @throws SQLException si un problème avec la BD.
     */
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
