package com.example.LogisticsCompany.services.implementations;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.repository.ClientRepository;
import com.example.LogisticsCompany.services.interfaces.ClientService;
import com.example.LogisticsCompany.web.dto.CreateClientDTO;
import com.example.LogisticsCompany.web.dto.UpdateClientDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository carRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Client> getClients() {
        return carRepository.findAll();
    }

    @Override
    public Client getClient(long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shop Id:" + id));
    }

    @Override
    public Client create(CreateClientDTO clientDTO) {
        return carRepository.save(modelMapper.map(clientDTO, Client.class));
    }

    @Override
    public Client updateClient(long id, UpdateClientDTO updateClientDTO) {
        Client Client = modelMapper.map(updateClientDTO, Client.class);
        Client.setId(id);
        return carRepository.save(Client);
    }

    @Override
    public void deleteClient(long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Client> getAllClientsByOffice(Office office) {
        return carRepository.getAllClientsByOffice(office);
    }
}
