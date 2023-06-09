package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.veiw.dto.TagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDTO(Tag tag);

    Tag toModel(TagDTO tagDTO);
}
