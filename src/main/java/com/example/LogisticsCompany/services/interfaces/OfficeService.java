package com.example.LogisticsCompany.services.interfaces;


import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.web.dto.CreateOfficeDTO;
import com.example.LogisticsCompany.web.dto.UpdateOfficeDTO;

import java.util.List;

public interface OfficeService {

    List<Office> getOffices();

    Office getOfficeById(long id);

    Office createOffice(CreateOfficeDTO officeDTO);

    Office updateOffice(long id, UpdateOfficeDTO updateOfficeDTO);

    void deleteOffice(long id);
}
