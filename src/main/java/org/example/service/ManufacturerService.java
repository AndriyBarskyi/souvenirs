package org.example.service;

import java.math.BigDecimal;
import java.util.List;

import org.example.entity.Manufacturer;

public interface ManufacturerService {
    /**
     * Returns a manufacturer with the specified ID.
     *
     * @param id ID of the manufacturer to return
     * @return a manufacturer with the specified ID
     */
    Manufacturer get(Long id);

    /**
     * Saves a manufacturer.
     *
     * @param manufacturer the manufacturer to be saved
     */
    void save(Manufacturer manufacturer);

    /**
     * Deletes the manufacturer with the specified ID.
     *
     * @param id ID of the manufacturer to delete
     */
    void delete(Long id);

    /**
     * Gets all manufacturers.
     *
     * @return the list of all manufacturers in the database
     */
    List<Manufacturer> getAll();

    /**
     * Gets all manufacturers.
     *
     * @return the list of all manufacturers which produce souvenirs cheaper than the specified price
     */
    List<Manufacturer> getAllCheaperThan(BigDecimal price);

    /**
     * Gets all manufacturers.
     *
     * @return the list of all manufacturers which produce souvenirs with the specified name and production year
     */

    List<Manufacturer> getAllBySouvenirNameAndProductionYear(
        String souvenirName, int yearOfProduction);
    /**
     * Updates a manufacturer with the specified ID.
     *
     * @param manufacturer the manufacturer to be updated
     * @param id ID of the manufacturer to update
     */

    void update(Manufacturer manufacturer, Long id);
}
