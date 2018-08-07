package com.epam.internship.carrental.service.alert;

import com.epam.internship.carrental.service.search.Search;
/**
 * Defines the methods, which has to be used when managing subscriptions.
 */
public interface AlertService {
    /**
     * Saves the user to the Search into the repository.
     * @param search the search to be saved
     */
    void subscribeUser(Search search);
}
