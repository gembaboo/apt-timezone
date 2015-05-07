package com.gembaboo.aptz.main;

import com.gembaboo.aptz.fileloader.FileLoader;
import com.gembaboo.aptz.fileloader.FileLoaderFactory;
import com.gembaboo.aptz.main.beans.JmxInvoker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import(Jmx.class)
public class SpringConfiguration {

    @Bean
    public FileLoader getFileLoader() {
        return FileLoaderFactory.createMongoFileLoader();
    }

    @Bean
    public JmxInvoker getJmxInvoker(){
        JmxInvoker jmxInvoker = new JmxInvoker();
        jmxInvoker.setFileLoader(getFileLoader());
        return jmxInvoker;
    }
}
