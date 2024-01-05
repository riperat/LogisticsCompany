package com.example.LogisticsCompany.services.implementations;

import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.repository.ClientRepository;
import com.example.LogisticsCompany.data.repository.OfficeRepository;
import com.example.LogisticsCompany.services.interfaces.OfficeService;
import com.example.LogisticsCompany.web.dto.CreateOfficeDTO;
import com.example.LogisticsCompany.web.dto.UpdateOfficeDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OfficeServiceImpl implements OfficeService {
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Office> getOffices() {
        return officeRepository.findAll();
    }

    @Override
    public Office getOfficeById(long id) {
        return officeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shop Id:" + id));

    }

    @Override
    public Office createOffice(CreateOfficeDTO officeDTO) {
        return officeRepository.save(modelMapper.map(officeDTO, Office.class));
    }

    @Override
    public Office updateOffice(long id, UpdateOfficeDTO updateOfficeDTO) {
        Office Office = modelMapper.map(updateOfficeDTO, Office.class);
        Office.setId(id);
        return officeRepository.save(Office);
    }

    @Override
    public void deleteOffice(long id) {
        officeRepository.deleteById(id);
    }
}
