package it.unical.ea.lemubackend.lemu_backend.config;

//importa la classe ModelMapper dalla libreria ModelMapper.
import it.unical.ea.lemubackend.lemu_backend.data.entities.Utente;
import it.unical.ea.lemubackend.lemu_backend.data.entities.WishlistProdotti;
import it.unical.ea.lemubackend.lemu_backend.dto.UtenteDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
//importa l'annotazione @Bean da Spring Framework per creare un bean.(un bean è un componente leggero, un oggetto gestito che Spring crea, configura e inietta in altre parti dell'applicazione in base alle necessità
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configura il ModelMapper per abilitare il matching dei campi basato sui nomi
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Definisce la mappatura tra Utente e UtenteDto per l'email
        modelMapper.typeMap(Utente.class, UtenteDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCredenziali().getEmail(), UtenteDto::setEmail);
        });
        modelMapper.createTypeMap(WishlistProdotti.class, Long.class)
                .setConverter(new Converter<WishlistProdotti, Long>() {
                    @Override
                    public Long convert(MappingContext<WishlistProdotti, Long> context) {
                        // Verifica che non stia tentando di convertire un oggetto intero
                        return context.getSource() != null ? context.getSource().getId() : null;
                    }
                });

        return modelMapper;
    }


}

