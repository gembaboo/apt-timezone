package com.gembaboo.aptz.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FileImporterTest.class)
public class FileImporterTest {


    @Bean
    private FileImporter fileImporter() {
        return new FileImporter();
    }

    @Test
    public void testOpenFile() throws Exception {
        assertTrue(fileImporter().openFile().canRead());
    }
}