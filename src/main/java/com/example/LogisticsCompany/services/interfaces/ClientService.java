package com.example.LogisticsCompany.services.interfaces;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.web.dto.CreateClientDTO;
import com.example.LogisticsCompany.web.dto.UpdateClientDTO;

import java.util.List;

public interface ClientService {

    List<Client> getClients();

    Client getClient(long id);

    Client create(CreateClientDTO clientDTO);

    Client updateClient(long id, UpdateClientDTO updateClientDTO);

    void deleteClient(long id);

    List<Client> getAllClientsByOffice(Office office);
}
