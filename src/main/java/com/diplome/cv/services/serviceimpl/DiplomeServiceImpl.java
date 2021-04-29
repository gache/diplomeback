package com.diplome.cv.services.serviceimpl;

import com.diplome.cv.model.Diplome;
import com.diplome.cv.repositories.DiplomeRepository;
import com.diplome.cv.services.serviceinter.IDiplomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomeServiceImpl implements IDiplomeService {

    @Autowired
    private final DiplomeRepository diplomeRepository;

    public DiplomeServiceImpl(DiplomeRepository diplomeRepository) {
        this.diplomeRepository = diplomeRepository;
    }
    @Override
    public List<Diplome> findAll() {
        return diplomeRepository.findAll();
    }

    @Override
    public Diplome findById(Long id) {
        return diplomeRepository.findById(id).orElse(null);
    }

    @Override
    public Diplome save(Diplome diplome) {
        return diplomeRepository.save(diplome);
    }

    @Override
    public void delete(Long id) {
        diplomeRepository.deleteById(id);
    }
}
