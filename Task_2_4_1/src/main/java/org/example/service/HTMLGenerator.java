package org.example.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.model.BuildResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for generating HTML reports from build results.
 */
public class HTMLGenerator {
    public void generateReport(Map<String, Map<String, BuildResult>> results) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
            
            Template template = cfg.getTemplate("report.ftl");
            
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("results", results);
            
            Writer writer = new FileWriter("result.html");
            template.process(dataModel, writer);
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
} 