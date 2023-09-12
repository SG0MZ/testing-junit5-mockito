package guru.springframework.sfgpetclinic.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

	@Mock
	VisitService visitService;
	
	@Spy //Mock
	PetService petService;
	
	@InjectMocks
	VisitController visitController;
	
	@Test
	void loadPetWithVisit() {
		//given
		Map<String, Object> model = new HashMap<>();
		Pet pet = new Pet(12L);
		Pet pet3 = new Pet(3L);
		
		petService.save(pet);
		petService.save(pet3);
		
		given(petService.findById(anyLong())).willCallRealMethod(); //.willReturn(pet);
		
		//when
		Visit visit = visitController.loadPetWithVisit(12L, model);
		
		//then
		assertThat(visit).isNotNull();
		assertThat(visit.getPet()).isNotNull();
		assertThat(visit.getPet()).isEqualTo(12L);
		verify(petService, times(1)).findById(anyLong());
	}

	@Test
	void loadPetWithVisitWithStubbing() {
		//given
		Map<String, Object> model = new HashMap<>();
		Pet pet = new Pet(12L);
		Pet pet3 = new Pet(3L);
		
		petService.save(pet);
		petService.save(pet3);
		
		given(petService.findById(anyLong())).willReturn(pet3);
		
		//when
		Visit visit = visitController.loadPetWithVisit(12L, model);
		
		//then
		assertThat(visit).isNotNull();
		assertThat(visit.getPet()).isNotNull();
		assertThat(visit.getPet()).isEqualTo(3L);
		verify(petService, times(1)).findById(anyLong());
	}
}
