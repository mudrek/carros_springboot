package com.carros.api;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity get() {
        //return ResponseEntity.ok(carroService.getCarros());
        return new ResponseEntity<>(carroService.getCarros(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        CarroDTO c = carroService.getCarrosById(id);
        return new ResponseEntity(c, HttpStatus.OK);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDTO> c = carroService.getCarrosByTipo(tipo);

        return c.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable Long id, @RequestBody Carro carro) {
        CarroDTO c = carroService.updateCarro(carro, id);
        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity post(@RequestBody Carro carro) {
        CarroDTO c = carroService.save(carro);
        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }

}
