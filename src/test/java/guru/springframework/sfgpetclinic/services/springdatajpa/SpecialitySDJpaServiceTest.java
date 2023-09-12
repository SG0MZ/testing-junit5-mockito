package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.lenient;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
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
	void testDeleteByObject() {
		
		//given
		Speciality speciality = new Speciality();
		
		//when
		service.delete(speciality);
		
		//then
		then(specialtyRepository).should().delete(any(Speciality.class));
//		verify(specialtyRepository).delete(any(Speciality.class));
	}
	
//	@Test
//	void findByIdTest() {
//		Speciality speciality = new Speciality();
//		
//		when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
//		
//		Speciality foundSpecialty = service.findById(1L);
//		
//		assertThat(foundSpecialty).isNotNull();
//		
//		verify(specialtyRepository).findById(anyLong());
//	}
	
	@Test
	void findByIdBddTest() {
		
		//given
		Speciality speciality = new Speciality();
		given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));
		
		//when
		Speciality foundSpecialty = service.findById(1L);
		
		//then
		assertThat(foundSpecialty).isNotNull();
//		verify(specialtyRepository).findById(anyLong());
		then(specialtyRepository).should().findById(anyLong());
//		then(specialtyRepository).should(times(1)).findById(anyLong());
		then(specialtyRepository).shouldHaveNoMoreInteractions();
	}
	
	@Test
	void deleteById() {
		//given - none
		
		//when
		service.deleteById(1L);
		service.deleteById(1L);
		
		//then
		then(specialtyRepository).should(times(2)).deleteById(1L);
//		verify(specialtyRepository, times(2)).deleteById(1L);
	}
	
	@Test
	void deleteByIdAtLeast() {
		
		//given
		
		//when
		service.deleteById(1L);
		service.deleteById(1L);
		
		//then
		then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
//		verify(specialtyRepository, atLeastOnce()).deleteById(1L);
	}
	
	@Test
	void deleteByIdAtMost() {
		
		//when
		service.deleteById(1L);
		service.deleteById(1L);
		
		//then
		then(specialtyRepository).should(atMost(5)).deleteById(1L);
//		verify(specialtyRepository, atMost(5)).deleteById(1L);
	}
	
	@Test
	void deleteByIdNever() {
		
		//when
		service.deleteById(1L);
		service.deleteById(1L);
		
		//then
		then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
//		verify(specialtyRepository, atLeastOnce()).deleteById(1L);
		then(specialtyRepository).should(never()).deleteById(5L);
//		verify(specialtyRepository, never()).deleteById(5L);;
	}
	
	@Test
	void testDelete() {
		
		//when
		service.delete(new Speciality());
		
		//then
		then(specialtyRepository).should().delete(any());
	}

	@Test
	void testDoThrow() {
		doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());
	
		assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
		
		verify(specialtyRepository).delete(any());
	}
	
	@Test
	 void testFindByIdThrows() {
		given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("boom"));
		
		assertThrows(RuntimeException.class, () -> service.findById(1L));
		
		then(specialtyRepository).should().findById(1L);
	}
	
	@Test
	void testDeleteBDD() {
		willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());
		
		assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
		
		then(specialtyRepository).should().delete(any());
	}
	
	@Test
	void testSaveLambda() {
		//given
		final String MATCH_ME = "MATCH_ME";
		Speciality speciality = new Speciality();
		speciality.setDescription(MATCH_ME);
		
		Speciality savedSpeciality = new Speciality();
		savedSpeciality.setId(1L);
		
		//need mock to only return on match MATCH_ME string
		given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);
		
		//when
		Speciality returnedSpecialty = service.save(speciality);
		
		//then
		assertThat(returnedSpecialty.getId()).isEqualTo(1L);
	}
	
	@Disabled
	@Test
	void testSaveLambdaNoMatch() {
		//given
		final String MATCH_ME = "MATCH_ME";
		Speciality speciality = new Speciality();
		speciality.setDescription("Not a match");
		
		Speciality savedSpeciality = new Speciality();
		savedSpeciality.setId(1L);
		
		//need mock to only return on match MATCH_ME string
		given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);
		
		//when
		Speciality returnedSpecialty = service.save(speciality);
		
		//then
		assertNull(returnedSpecialty);
	}
	
}
