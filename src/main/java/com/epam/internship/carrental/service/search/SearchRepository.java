package com.epam.internship.carrental.service.search;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends PagingAndSortingRepository<Search, Long>{
}
