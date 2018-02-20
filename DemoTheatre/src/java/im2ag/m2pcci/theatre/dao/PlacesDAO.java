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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.sql.DataSource;

/**
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class PlacesDAO {

    /**
     * Requête pour trouver les places déjà vendues pour un spectacle donné
     */
    private static final String PLACES_VENDUES
            = "SELECT idplace, categorie, rang,  colonne FROM places_vendues NATURAL JOIN places WHERE idspectacle = ?";

    /**
     * Requête pour insérer les données dans la table PLACES_VENDUES
     */
    private static final String ACHETER_PLACE = "INSERT INTO places_vendues (idplace, idspectacle) VALUES (?, ?)";

    /**
     * recherche, pour un spectacle donné, la liste des places qui ont déja été
     * vendues et retourne une liste d'objets Place.
     *
     * @param ds la source de données pour les connexions JDBC
     * @param spectacleId l'identifiant du spectacle
     * @return la liste des places déjà vendue pour le spectacle spectacleId
     * @throws SQLException si problème avec JDBC
     */
    public static List<Place> placesVendues(DataSource ds, int spectacleId) throws SQLException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(PLACES_VENDUES)) {
            pstmt.setInt(1, spectacleId);
            ResultSet rs = pstmt.executeQuery();
            List<Place> places = new ArrayList<>();
            while (rs.next()) {
                places.add(new Place(rs.getInt("idplace"),
                        rs.getInt("rang"),
                        rs.getInt("colonne"),
                        rs.getString("categorie").charAt(0))
                );
            }
            return places;
        }
    }

    /**
     * recherche pour un spectacle donné la liste des places qui ont déja été
     * vendues et la retourne sous la forme d'une chaîne JSON.
     *
     * @param ds la source de données pour les connexions JDBC
     * @param spectacleId l'identifiant du spectacle
     * @return la chaîne JSON
     * @throws SQLException si problème avec JDBC
     */
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
     * achat de places. Permet d'enregistrer dans la base de données les places
     * achetées.
     *
     * @param ds la source de données pour les connexions JDBC
     * @param spectacleId l'identifiant du spectacle
     * @param placesIds les identifiants des places achetée
     * @throws SQLException SQLException si problème avec JDBC
     */
    public static void acheterPlaces(DataSource ds, int spectacleId, int[] placesIds) throws SQLException, AchatConcurrentException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(ACHETER_PLACE)) {
                conn.setAutoCommit(false);  // début d'une transaction
                for (int idPlace : placesIds) {
                    pstmt.setInt(1, idPlace);
                    pstmt.setInt(2, spectacleId);
                    pstmt.addBatch();  // ajoute la requête d'insertion au batch
                }
                pstmt.executeBatch();  // exécute les requêtes d'insertion
                conn.commit();   // valide la transaction
            } catch (SQLException ex) {
                conn.rollback();   // annule la transaction 
                ex = ex.getNextException();  // on prend la cause
                if (ex instanceof SQLIntegrityConstraintViolationException
                        || ex.getMessage().contains("pk_placesvendues")) {  // certains drivers ne supportent pas encore le type SQLIntegrityConstraintViolationException
                    throw new AchatConcurrentException("places déjà achetées ", ex);
                } else {
                    throw ex;
                }
            } finally {
                conn.setAutoCommit(true); // remet la connexion en mode autocommit
            }
        }
    }
}
