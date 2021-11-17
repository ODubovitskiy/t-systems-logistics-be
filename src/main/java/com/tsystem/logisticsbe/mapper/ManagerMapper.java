package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.ManagerDTO;
import com.tsystem.logisticsbe.entity.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper implements Mapper<Manager, ManagerDTO> {
    @Override
    public Manager mapToEntity(ManagerDTO dto) {
        return null;
    }

    @Override
    public ManagerDTO mapToDTO(Manager entity) {
        if (entity == null) {
            return null;
        }
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName(entity.getName());
        managerDTO.setLastName(entity.getLastName());

        return managerDTO;
    }

    @Override
    public void updateEntity(Manager source, Manager destination) {

    }
}
