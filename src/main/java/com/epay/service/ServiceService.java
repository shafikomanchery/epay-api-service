package com.epay.service;

import com.epay.entity.Service;
import com.epay.repository.ServiceRepository;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service createService(Service service){
        return serviceRepository.save(service);
    }

    public List<Service> getAllServices(){
        return serviceRepository.findAll();
    }

    public Optional<Service> getServiceById(Long id){
        return serviceRepository.findById(id);
    }

    public Service updateService(Long serviceId, Service updatedService){
        return serviceRepository.findById(serviceId).map(service -> {
            service.setName(updatedService.getName());
            service.setDescription(updatedService.getDescription());
            service.setOrganization(updatedService.getOrganization());
            return serviceRepository.save(service);
        }).orElseThrow(() -> new RuntimeException("Service with ID "+serviceId+" not found."));
    }

    public void deleteServiceById(Long serviceId){
        if(serviceRepository.existsById(serviceId)){
            serviceRepository.deleteById(serviceId);
        }else {
            throw new RuntimeException("Service with ID " + serviceId + "not found.");
        }
    }

    public List<Service> getServicesByOrganizationId(Long organizationId){
        return serviceRepository.findByOrganizationOrganizationId(organizationId);
    }

}
