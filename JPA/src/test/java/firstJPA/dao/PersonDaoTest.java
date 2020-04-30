package firstJPA.dao;

import firstJPA.domain.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonDaoTest {

    @Mock
    EntityManager emMock;
    Person personMock;

    @Mock
    EntityTransaction etMock;

    @InjectMocks
    PersonDao dao = new PersonDao();

    @Test
    void whenGettingByIdMethodsShouldBeCalled() {
        Person mock = mock(Person.class);
        when(emMock.find(any(), anyInt())).thenReturn(mock);

        Person person = dao.getById(1);

        verify(emMock).find(any(), eq(1));
        assertThat(person).isEqualTo(mock);
    }

}