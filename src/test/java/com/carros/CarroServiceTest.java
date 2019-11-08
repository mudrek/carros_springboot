package com.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import com.carros.domain.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

@SpringBootTest
class CarroServiceTest {

	@Autowired
	private CarroService carroService;

	@Test
	void testeSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");

		CarroDTO c = carroService.save(carro);

		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		//Buscar objeto
		c = carroService.getCarrosById(id);
		assertNotNull(c);

		//Verificar campos
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());

		//Deletar o objeto
		carroService.delete(id);

		//Verificar se deletou
		try {
			assertNull(carroService.getCarrosById(id));
			fail("O carro não foi excluído");
		} catch(ObjectNotFoundException ex) {
			//ok
		}
	}

	@Test
	public void testList() {
		List<CarroDTO> carros = carroService.getCarros();
		assertEquals(30, carros.size());
	}

	@Test
	public void testListPorTipo() {
		assertEquals(10, carroService.getCarrosByTipo("classicos").size());
		assertEquals(10, carroService.getCarrosByTipo("esportivos").size());
		assertEquals(10, carroService.getCarrosByTipo("luxo").size());

		assertEquals(0, carroService.getCarrosByTipo("xx").size());

	}

	@Test
	public void testGet() {
		CarroDTO c = carroService.getCarrosById(11L);

		assertNotNull(c);

		assertEquals("Ferrari FF", c.getNome());
	}

}
