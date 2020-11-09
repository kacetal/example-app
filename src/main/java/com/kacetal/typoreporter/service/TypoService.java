package com.kacetal.typoreporter.service;

import com.kacetal.typoreporter.service.dto.TypoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.kacetal.typoreporter.domain.Typo}.
 */
public interface TypoService {

    /**
     * Save a typo.
     *
     * @param typoDTO the entity to save.
     * @return the persisted entity.
     */
    TypoDTO save(TypoDTO typoDTO);

    /**
     * Get all the typos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" typo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypoDTO> findOne(Long id);

    /**
     * Delete the "id" typo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
