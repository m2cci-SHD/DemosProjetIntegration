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

import im2ag.m2pcci.theatre.util.DataSourceDeTest;
import im2ag.m2pcci.theatre.util.PlacesInitialiser;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * test Unitaires pour la DAO PlacesDAO
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class PlacesDAOTest {

    static DataSource ds = new DataSourceDeTest();

    public PlacesDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        int[] placesVendues = {1, 2, 155};
        PlacesInitialiser.initPlacesVendues(ds, 1, placesVendues);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of placesVenduesAsJSON method of class PlacesDAO.
     */
    @Test
    public void testPlacesVendues() throws SQLException {
        System.out.println("placesVenduesAsJSON");
        String res = PlacesDAO.placesVenduesAsJSON(ds, 1);
        System.out.println(res);
        assertEquals("{\"bookings\":[{\"placeId\":1,\"rang\":1,\"colonne\":3},{\"placeId\":2,\"rang\":1,\"colonne\":4},{\"placeId\":155,\"rang\":7,\"colonne\":16}]}",
                res);
    }

    /**
     * Test of acheterPlaces method of class PlacesDAO.
     *
     * @throws SQLException
     */
    @Test
    public void testAcheterPlace() throws SQLException, AchatConcurrentException {
        System.out.println("placesVenduesAsJSON");
        int[] placesAchetees = {15, 157};
        PlacesDAO.acheterPlaces(ds, 1, placesAchetees);
        String res = PlacesDAO.placesVenduesAsJSON(ds, 1);
        System.out.println(res);
        assertEquals("{\"bookings\":[{\"placeId\":1,\"rang\":1,\"colonne\":3},{\"placeId\":2,\"rang\":1,\"colonne\":4},"
                + "{\"placeId\":15,\"rang\":1,\"colonne\":19},"
                + "{\"placeId\":155,\"rang\":7,\"colonne\":16},"
                + "{\"placeId\":157,\"rang\":7,\"colonne\":20}]}",
                res);
    }

    @Test
    public void testAcheterPlace2() throws SQLException {
        System.out.println("placesVenduesAsJSON");
        int[] placesAchetees = {15, 155, 158};
        try {
            PlacesDAO.acheterPlaces(ds, 1, placesAchetees);
            fail("une exception aurait du être lancée");
        } catch (AchatConcurrentException ex) {
            String res = PlacesDAO.placesVenduesAsJSON(ds, 1);
            System.out.println(res);
            assertEquals("{\"bookings\":[{\"placeId\":1,\"rang\":1,\"colonne\":3},{\"placeId\":2,\"rang\":1,\"colonne\":4},{\"placeId\":155,\"rang\":7,\"colonne\":16}]}",
                    res);
        } catch (Exception ex) {
            fail("Mauvais type d'exception lancée " + ex.getMessage());
        }
    }

}
