package edu.iu.habahram.ducksservice.controllers;

import edu.iu.habahram.ducksservice.model.DuckData;
import edu.iu.habahram.ducksservice.repository.DucksFileRepository;
import edu.iu.habahram.ducksservice.repository.DucksDBRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/ducks")
public class DuckController {

    private DucksFileRepository ducksFileRepository;
    private DucksDBRepository ducksDBRepository;

    public DuckController(DucksDBRepository ducksDBRepository, DucksFileRepository ducksFileRepository) {
        this.ducksDBRepository = ducksDBRepository;
        this.ducksFileRepository = ducksFileRepository;

    }


   @PostMapping
    public int add(@RequestBody DuckData duck) {
       try {
           return ducksDBRepository.save(duck).getId();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

    @GetMapping
    public Iterable<DuckData> findAll() {
        try {
            return ducksDBRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DuckData> find(@PathVariable int id) {
        try {
            Optional<DuckData> duck = ducksDBRepository.findById(id);
            if(duck.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(duck.get());
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/image")
    public boolean updateImage(@PathVariable int id,
                               @RequestParam MultipartFile file) {
        try {
            return ducksFileRepository.updateImage(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{id}/audio")
    public boolean updateAudio(@PathVariable int id,
                               @RequestParam MultipartFile file) {
        try {
            return ducksFileRepository.updateAudio(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        try {
            byte[] image = ducksFileRepository.getImage(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}/audio")
    public ResponseEntity<?> getAudio(@PathVariable int id) {
        try {
            byte[] image = ducksFileRepository.getAudio(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.valueOf("audio/mp3"))
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<DuckData>> searchByType(@RequestParam String type) {
        try {
            List<DuckData> ducks = ducksDBRepository.findByTypeIgnoreCase(type);
            if (ducks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(ducks);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
