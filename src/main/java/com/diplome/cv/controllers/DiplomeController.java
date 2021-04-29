package com.diplome.cv.controllers;

import com.diplome.cv.model.Diplome;
import com.diplome.cv.services.serviceinter.IDiplomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DiplomeController {
    @Autowired
    private final IDiplomeService iDiplomeService;


    public DiplomeController(IDiplomeService iDiplomeService) {
        this.iDiplomeService = iDiplomeService;
    }

    @GetMapping("/diplomes")
    public ResponseEntity<List<Diplome>> getDiplomes() {
        return ResponseEntity.ok().body(iDiplomeService.findAll());
    }

    // recherche d'un diplome par Id
    @GetMapping("/diplomes/{id}")
    public ResponseEntity<?> getId(@PathVariable Long id) {
        Diplome diplome = null;
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            diplome = iDiplomeService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Erreur au moment de faire une consultation à la base de données");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (diplome == null) {
            response.put("message", "Le diplome Id: ".concat(id.toString().concat(" n'existe pas dans la base de données!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Diplome>(diplome, HttpStatus.OK);
    }

    @PostMapping("/diplomes")
    public ResponseEntity<?> create(@Valid @RequestBody Diplome diplome, BindingResult result) {
        Diplome newDiplome = null;
        Map<String, Object> response = new HashMap<String, Object>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "Le champ '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newDiplome = iDiplomeService.save(diplome);
        } catch (DataAccessException e) {
            response.put("message", "Erreur au moment de la création d'une diplome dans la base de données");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Le Diplome a été créer avec satisfaction");
        response.put("diplome", newDiplome);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/diplomes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Diplome diplome, BindingResult result, @PathVariable Long id) {
        Diplome diplomeActuelle = iDiplomeService.findById(id);
        Diplome updateDiplome = null;
        Map<String, Object> response = new HashMap<String, Object>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "Le Champ '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (diplomeActuelle == null) {
            response.put("message", "Erreur: au moment de l'edition du diplome Id: "
                    .concat(id.toString().concat(" n'existe pas dans la base de données!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            diplomeActuelle.setNomDiplome(diplome.getNomDiplome());
            diplomeActuelle.setEcole(diplome.getEcole());
            diplomeActuelle.setVille(diplome.getVille());
            diplomeActuelle.setDescription(diplome.getDescription());
            diplomeActuelle.setAnnee(diplome.getAnnee());

            updateDiplome = iDiplomeService.save(diplomeActuelle);
        } catch (DataAccessException e) {
            response.put("message", "Erreurr au moment de la actualisation du diplome dans la bases de données");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Le diplome a été actualiser correctement");
        response.put("diplome", updateDiplome);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/diplomes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            Diplome diplome = iDiplomeService.findById(id);
            iDiplomeService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Erreur  au moment de l'elimination du diplome dans la bases de données");
            response.put("erreur", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Le diplome a été eliminer avec satisfaction");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
}
