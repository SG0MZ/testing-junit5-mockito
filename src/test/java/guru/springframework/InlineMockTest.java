package guru.springframework;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InlineMockTest {
	
	@Test
	void testInlineMock() {
		Map mapMock = mock(Map.class);
		
		assertEquals(mapMock.size(),0);
	}
	
	
}
