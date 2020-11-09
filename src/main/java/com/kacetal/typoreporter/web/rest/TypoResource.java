package com.kacetal.typoreporter.web.rest;

import com.kacetal.typoreporter.service.TypoService;
import com.kacetal.typoreporter.web.rest.errors.BadRequestAlertException;
import com.kacetal.typoreporter.service.dto.TypoDTO;
import com.kacetal.typoreporter.service.dto.TypoCriteria;
import com.kacetal.typoreporter.service.TypoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kacetal.typoreporter.domain.Typo}.
 */
@RestController
@RequestMapping("/api")
public class TypoResource {

    private final Logger log = LoggerFactory.getLogger(TypoResource.class);

    private static final String ENTITY_NAME = "typo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypoService typoService;

    private final TypoQueryService typoQueryService;

    public TypoResource(TypoService typoService, TypoQueryService typoQueryService) {
        this.typoService = typoService;
        this.typoQueryService = typoQueryService;
    }

    /**
     * {@code POST  /typos} : Create a new typo.
     *
     * @param typoDTO the typoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typoDTO, or with status {@code 400 (Bad Request)} if the typo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/typos")
    public ResponseEntity<TypoDTO> createTypo(@Valid @RequestBody TypoDTO typoDTO) throws URISyntaxException {
        log.debug("REST request to save Typo : {}", typoDTO);
        if (typoDTO.getId() != null) {
            throw new BadRequestAlertException("A new typo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypoDTO result = typoService.save(typoDTO);
        return ResponseEntity.created(new URI("/api/typos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /typos} : Updates an existing typo.
     *
     * @param typoDTO the typoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typoDTO,
     * or with status {@code 400 (Bad Request)} if the typoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/typos")
    public ResponseEntity<TypoDTO> updateTypo(@Valid @RequestBody TypoDTO typoDTO) throws URISyntaxException {
        log.debug("REST request to update Typo : {}", typoDTO);
        if (typoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypoDTO result = typoService.save(typoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /typos} : get all the typos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typos in body.
     */
    @GetMapping("/typos")
    public ResponseEntity<List<TypoDTO>> getAllTypos(TypoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Typos by criteria: {}", criteria);
        Page<TypoDTO> page = typoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /typos/count} : count all the typos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/typos/count")
    public ResponseEntity<Long> countTypos(TypoCriteria criteria) {
        log.debug("REST request to count Typos by criteria: {}", criteria);
        return ResponseEntity.ok().body(typoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /typos/:id} : get the "id" typo.
     *
     * @param id the id of the typoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/typos/{id}")
    public ResponseEntity<TypoDTO> getTypo(@PathVariable Long id) {
        log.debug("REST request to get Typo : {}", id);
        Optional<TypoDTO> typoDTO = typoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typoDTO);
    }

    /**
     * {@code DELETE  /typos/:id} : delete the "id" typo.
     *
     * @param id the id of the typoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/typos/{id}")
    public ResponseEntity<Void> deleteTypo(@PathVariable Long id) {
        log.debug("REST request to delete Typo : {}", id);
        typoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
