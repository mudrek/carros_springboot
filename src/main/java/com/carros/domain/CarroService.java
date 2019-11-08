package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
import com.carros.domain.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {
        return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO getCarrosById(Long id) {
        return rep.findById(id).map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO save(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO updateCarro(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");
        CarroDTO optional = getCarrosById(id);
        if (optional != null) {
            Carro db = new Carro(optional);
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id: " + db.getId());

            rep.save(db);
            return CarroDTO.create(db);
        } else {
            //throw new RuntimeException("Não foi possível atualizar o registro");
            return null;
        }
    }

//    public List<Carro> getCarrosFake() {
//        List<Carro> carros = new ArrayList<>();
//
//        carros.add(new Carro(1L, "Fusca"));
//        carros.add(new Carro(2L, "Punto"));
//        carros.add(new Carro(3L, "Uno"));
//
//        return carros;
//    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
