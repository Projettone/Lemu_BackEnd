package it.unical.ea.lemubackend.lemu_backend.config;

//importa la classe ModelMapper dalla libreria ModelMapper.
import org.modelmapper.ModelMapper;
//importa l'annotazione @Bean da Spring Framework per creare un bean.(un bean è un componente leggero, un oggetto gestito che Spring crea, configura e inietta in altre parti dell'applicazione in base alle necessità
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    //gestirà il ciclo di vita e fornirà questo bean ad altre parti dell'applicazione che ne hanno bisogno
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        //abilita il matching dei campi in base ai nomi dei campi durante il processo di mappatura.
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); //consente a ModelMapper di accedere ai campi privati per la mappatura
        return modelMapper;
    }

}

