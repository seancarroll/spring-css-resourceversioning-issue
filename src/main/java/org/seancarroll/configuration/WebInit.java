package org.seancarroll.configuration;

import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionTrackingMode;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;

@Order(1)
public class WebInit implements WebApplicationInitializer {
    
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.setDisplayName("Web application");
       rootContext.register(ApplicationConfiguration.class);
        container.addListener(new ContextLoaderListener(rootContext));
        
        Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(rootContext));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/app/*");
        
        FilterRegistration characterEncodingFilter =
                container.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        characterEncodingFilter.setInitParameter("encoding", StandardCharsets.UTF_8.name());
        characterEncodingFilter.
        setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
        
        FilterRegistration sitemesh =
                container.addFilter("sitemesh", SiteMeshFilter.class);
        sitemesh.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR), true, "/*");
        
        EnumSet<DispatcherType> resourceUrlEncodingFilterDispatherTypes = 
                EnumSet.of(DispatcherType.ASYNC, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ERROR);
        
        FilterRegistration resourceUrlEncodingFilter = 
                container.addFilter("resourceUrlEncodingFilter",  ResourceUrlEncodingFilter.class);
        resourceUrlEncodingFilter.addMappingForUrlPatterns(resourceUrlEncodingFilterDispatherTypes, true, "/*");
        
        FilterRegistration urlRewriteFilter =
                container.addFilter("UrlRewriteFilter", UrlRewriteFilter.class);
        urlRewriteFilter.addMappingForUrlPatterns(null, true, "/*");
        

        Set<SessionTrackingMode> set = new HashSet<>();
        set.add(SessionTrackingMode.COOKIE);
        container.setSessionTrackingModes(set);
        container.getSessionCookieConfig().setHttpOnly(true);
    }
}