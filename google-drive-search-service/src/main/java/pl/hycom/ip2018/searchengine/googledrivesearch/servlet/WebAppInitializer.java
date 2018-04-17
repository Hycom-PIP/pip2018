package pl.hycom.ip2018.searchengine.googledrivesearch.servlet;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

//public class WebAppInitializer implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.register(WebMvcConfigure.class);
//        context.setServletContext(servletContext);
//
//        ServletRegistration.Dynamic googleDriveServlet = servletContext.addServlet("googleDriveServlet", new GoogleDriveServlet());
////        googleDriveServlet.setLoadOnStartup(1);
////        googleDriveServlet.addMapping("/");
//
//        ServletRegistration.Dynamic googleDriveServletCallback = servletContext.addServlet("googleDriveServletCallback", new GoogleDriveServletCallback());
////        googleDriveServletCallback.setLoadOnStartup(2);
////        googleDriveServlet.addMapping("/");
//    }
//}
