package org.seancarroll.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ComponentScan(basePackages = { "org.seancarroll" })
@Configuration
public class ApplicationConfiguration extends DelegatingWebMvcConfiguration {

    @Inject
    private ConfigurableListableBeanFactory beanFactory;

    @Inject
    private ApplicationEventPublisher springEventPublisher;

    @Inject
    private ApplicationContext ctx;

    @Inject
    private Environment env;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {
        List<ViewResolver> resolvers = new ArrayList<>();
        resolvers.add(internalResourceViewResolver());

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setViewResolvers(resolvers);
        resolver.setContentNegotiationManager(manager);
        return resolver;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String environment = env.getProperty("environment");
        boolean devMode = "dev".equalsIgnoreCase(environment);
        boolean useResourceCache = !devMode;
        Integer cachePeriod = devMode ? 0 : null;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMddyyyyhhmm");
        String version = devMode ? "dev" : DateTime.now().toString(formatter);
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addFixedVersionStrategy(version, "/**/*.js")
                .addContentVersionStrategy("/**/*.css", "/**/*.png", "/**/*.jpg");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(cachePeriod)
                .resourceChain(useResourceCache)
                .addResolver(versionResourceResolver);
                //.addTransformer(new CssLinkResourceTransformer());
    }
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true)
                .ignoreAcceptHeader(false)
                .useJaf(false)
                .defaultContentType(MediaType.TEXT_HTML)
                .mediaType("html", MediaType.TEXT_HTML)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

}