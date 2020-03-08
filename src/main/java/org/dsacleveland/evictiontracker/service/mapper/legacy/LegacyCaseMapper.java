package org.dsacleveland.evictiontracker.service.mapper.legacy;

import org.dsacleveland.evictiontracker.model.evictiondata.entity.AttorneyEntity;
import org.dsacleveland.evictiontracker.model.evictiondata.entity.CaseEntity;
import org.dsacleveland.evictiontracker.model.evictiondata.entity.PartyEntity;
import org.dsacleveland.evictiontracker.model.evictiondata.legacy.AttorneySet;
import org.dsacleveland.evictiontracker.model.evictiondata.legacy.Defendant;
import org.dsacleveland.evictiontracker.model.evictiondata.legacy.LegacyCase;
import org.dsacleveland.evictiontracker.model.evictiondata.legacy.Plaintiff;
import org.dsacleveland.evictiontracker.service.mapper.DtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(uses = {LegacyAddressMapper.class})
public interface LegacyCaseMapper extends DtoMapper<CaseEntity, LegacyCase> {
    LegacyCaseMapper INSTANCE = Mappers.getMapper(LegacyCaseMapper.class);

    @Override
    @Mapping(target = "id", ignore = true)
    LegacyCase toDto(CaseEntity entity);

    @Override
    CaseEntity toEntity(LegacyCase legacyCase);

    List<PartyEntity> listPlaintiffToEntity(List<Plaintiff> plaintiffs);

    List<PartyEntity> listDefendantToEntity(List<Defendant> defendants);

    @Mapping(source = "attorneySet", target = "attorney")
    PartyEntity plaintiffToEntity(Plaintiff plaintiff);

    @Mapping(source = "attorneySet", target = "attorney")
    PartyEntity defendantToEntity(Defendant defendant);

    default AttorneyEntity listToAttorney(List<AttorneySet> attorneys) {
        return Optional.ofNullable(attorneys)
                       .filter(atts -> atts.size() > 0)
                       .map(attorney -> attorney.get(0))
                       .map(at -> AttorneyEntity.builder().name(at.getName()).build())
                       .orElse(null);
    }
}