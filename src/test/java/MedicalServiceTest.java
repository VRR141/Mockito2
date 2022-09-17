import org.junit.Test;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceTest {

    private MedicalService sut;

    @Test
    public void checkBloodPressureTest(){
        String id = "22";

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);

        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);

        Mockito.when(patientInfoRepository.getById(id)).
                thenReturn(new PatientInfo("22","Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        sut = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        sut.checkBloodPressure(id, new BloodPressure(80, 120));

        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        String expected = String.format("Warning, patient with id: %s, need help", id);
        Assertions.assertEquals(expected, argumentCaptor.getValue());
    }

    @Test
    public void checkTemperatureTest(){
        String id = "22";

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);

        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);

        Mockito.when(patientInfoRepository.getById(id)).
                thenReturn(new PatientInfo("22","Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        sut = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        sut.checkTemperature(id, new BigDecimal("34.65"));

        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        String expected = String.format("Warning, patient with id: %s, need help", id);

        Assertions.assertEquals(expected, argumentCaptor.getValue());
    }

    @Test
    public void checkNormalBloodPressureTest(){
        String id = "22";

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);

        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);

        Mockito.when(patientInfoRepository.getById(id)).
                thenReturn(new PatientInfo("22","Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        sut = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        sut.checkBloodPressure(id, new BloodPressure(120, 80));

        String expected = String.format("Warning, patient with id: %s, need help", id);

        Mockito.verify(sendAlertService, Mockito.times(0)).send(expected);
    }

    @Test
    public void checkNormalTemperatureTest(){
        String id = "22";

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);

        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);

        Mockito.when(patientInfoRepository.getById(id)).
                thenReturn(new PatientInfo("22","Иван", "Петров", LocalDate.of(1980, 11, 26),
                        new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

        sut = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        sut.checkTemperature(id, new BigDecimal("35.65"));

        String expected = String.format("Warning, patient with id: %s, need help", id);

        Mockito.verify(sendAlertService, Mockito.times(0)).send(expected);
    }
}
