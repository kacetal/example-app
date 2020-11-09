package com.kacetal.typoreporter.repository;

import com.kacetal.typoreporter.domain.Typo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Typo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypoRepository extends JpaRepository<Typo, Long>, JpaSpecificationExecutor<Typo> {
}
