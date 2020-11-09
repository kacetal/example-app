package com.kacetal.typoreporter.service.impl;

import com.kacetal.typoreporter.service.TypoService;
import com.kacetal.typoreporter.domain.Typo;
import com.kacetal.typoreporter.repository.TypoRepository;
import com.kacetal.typoreporter.service.dto.TypoDTO;
import com.kacetal.typoreporter.service.mapper.TypoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Typo}.
 */
@Service
@Transactional
public class TypoServiceImpl implements TypoService {

    private final Logger log = LoggerFactory.getLogger(TypoServiceImpl.class);

    private final TypoRepository typoRepository;

    private final TypoMapper typoMapper;

    public TypoServiceImpl(TypoRepository typoRepository, TypoMapper typoMapper) {
        this.typoRepository = typoRepository;
        this.typoMapper = typoMapper;
    }

    @Override
    public TypoDTO save(TypoDTO typoDTO) {
        log.debug("Request to save Typo : {}", typoDTO);
        Typo typo = typoMapper.toEntity(typoDTO);
        typo = typoRepository.save(typo);
        return typoMapper.toDto(typo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Typos");
        return typoRepository.findAll(pageable)
            .map(typoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TypoDTO> findOne(Long id) {
        log.debug("Request to get Typo : {}", id);
        return typoRepository.findById(id)
            .map(typoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Typo : {}", id);
        typoRepository.deleteById(id);
    }
}
