package org.example.service;

import java.util.List;

import org.example.entity.Souvenir;

public interface SouvenirService {
    /**
     * Saves a souvenir.
     *
     * @param  souvenir  the souvenir to be saved
     */
    void save(Souvenir souvenir);
    /**
     * Returns a souvenir with the specified ID.
     *
     * @param id ID of the souvenir to return
     * @return a souvenir with the specified ID
     */
    Souvenir get(Long id);

    /**
     * Deletes the souvenir with the specified ID.
     * @param id ID of the souvenir to delete
     */
    void delete(Long id);
    /**
     * Gets all souvenirs.
     *
     * @return  the list of all souvenirs in the database by the id of the manufacturer
     */
    List<Souvenir> findByManufacturerId(Long manufacturerId);
    /**
     * Gets all souvenirs.
     *
     * @return  the list of all souvenirs in the database by the name of the manufacturer
     */
    List<Souvenir> findByManufacturerName(String manufacturerName);
    /**
     * Gets all souvenirs.
     *
     * @return  the list of all souvenirs in the database by the country of the manufacturer
     */
    List<Souvenir> findByCountry(String country);
    /**
     * Gets all souvenirs.
     *
     * @return  the list of all souvenirs in the database
     */

    List<Souvenir> getAll();

    /**
     * Updates a souvenir with the specified ID.
     *
     * @param souvenir the souvenir to be updated
     * @param id ID of the souvenir to update
     */
    void update(Souvenir souvenir, Long id);
}
