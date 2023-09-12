package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

	@Mock
	SpecialtyRepository specialtyRepository;
	
	@InjectMocks
	SpecialitySDJpaService service;
	
	@Test
	void findByIdTest() {
		Speciality speciality = new Speciality();
		
		when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
		
		Speciality foundSpecialty = service.findById(1L);
		
		assertThat(foundSpecialty).isNotNull();
		
		verify(specialtyRepository).findById(1L);
	}
	
	@Test
	void deleteById() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, times(2)).deleteById(1L);
	}
	
	@Test
	void deleteByIdAtLeast() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, atLeastOnce()).deleteById(1L);
	}
	
	@Test
	void deleteByIdAtMost() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, atMost(5)).deleteById(1L);
	}
	
	@Test
	void deleteByIdNever() {
		service.deleteById(1L);
		service.deleteById(1L);
		
		verify(specialtyRepository, atLeastOnce()).deleteById(1L);
		
		verify(specialtyRepository, never()).deleteById(5L);;
	}
	
	@Test
	void testDelete() {
		service.delete(new Speciality());
	}

}
