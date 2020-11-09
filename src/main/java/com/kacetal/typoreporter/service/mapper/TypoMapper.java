package com.kacetal.typoreporter.service.mapper;


import com.kacetal.typoreporter.domain.*;
import com.kacetal.typoreporter.service.dto.TypoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Typo} and its DTO {@link TypoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypoMapper extends EntityMapper<TypoDTO, Typo> {



    default Typo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Typo typo = new Typo();
        typo.setId(id);
        return typo;
    }
}
