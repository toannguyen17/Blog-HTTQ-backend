package com.httq.services;

import java.util.Optional;

public interface IGeneralService<T> {
	Iterable<T> findAll();

	void save(T entity);

	Optional<T> findById(Long id);

	void deleteById(Long id);
}
