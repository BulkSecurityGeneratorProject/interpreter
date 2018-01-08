package at.meroff.bac.bootstrap;

import at.meroff.bac.service.FieldService;
import at.meroff.bac.service.dto.FieldDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    private ResourceLoader resourceLoader;
    private final FieldService fieldService;

    public Bootstrap(ResourceLoader resourceLoader, FieldService fieldService) {
        this.resourceLoader = resourceLoader;
        this.fieldService = fieldService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

       /* for (int i = 0; i < 17; i++) {

            String format = String.format("%02d", i + 1);

            FieldDTO field01 = new FieldDTO();
            field01.setDescription("Beispiel " + format);

            Resource resourceJpeg = resourceLoader.getResource("classpath:demodata/example_"+ format + "/scene_out.jpg");
            Resource resourceSvg = resourceLoader.getResource("classpath:demodata/example_"+ format + "/scene_out.svg");

            try {
                field01.setOrigImage(IOUtils.toByteArray(resourceJpeg.getInputStream()));
                field01.setSvgImage(IOUtils.toByteArray(resourceSvg.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            field01.setSvgImageContentType("image/svg+xml");
            field01.setOrigImageContentType("image/jpeg");

            fieldService.save(field01);

        }*/

    }
}
