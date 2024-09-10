package com.api.apireservas.service;

import com.api.apireservas.entity.MesaEntity;
import com.api.apireservas.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaServiceImpl {

    @Autowired
    private MesaRepository mesaRepository;

    // Metodo para listar todas las mesas
    public List<MesaEntity> getAll(){
        return mesaRepository.findAll();
    }

    // Metodo para obtener una mesa por ID
    public Optional<MesaEntity> getById(Long id){
        return mesaRepository.findById(id);
    }

    // Metodo para crear una mesa
    public MesaEntity createMesa(MesaEntity mesa){
        return mesaRepository.save(mesa);
    }

    // Metodo para actualizar una mesa
  /*  public Optional<MesaEntity> updateMesa(MesaEntity mesa){

    }*/
}
