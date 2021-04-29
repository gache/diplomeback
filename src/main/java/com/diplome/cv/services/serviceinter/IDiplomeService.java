package com.diplome.cv.services.serviceinter;

import com.diplome.cv.model.Diplome;

import java.util.List;

public interface IDiplomeService {
    List<Diplome> findAll();

    public Diplome findById(Long id);

    Diplome save(Diplome diplome);

    public void delete(Long id);
}
