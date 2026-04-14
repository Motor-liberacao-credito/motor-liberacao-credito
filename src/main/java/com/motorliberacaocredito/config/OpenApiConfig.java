package com.motorliberacaocredito.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "openapi")
public class OpenApiConfig {

    private InfoConfig info;
    private List<ServerConfig> servers;
    private List<TagConfig> tags;

    // getters e setters
    public InfoConfig getInfo() { return info; }
    public void setInfo(InfoConfig info) { this.info = info; }
    public List<ServerConfig> getServers() { return servers; }
    public void setServers(List<ServerConfig> servers) { this.servers = servers; }
    public List<TagConfig> getTags() { return tags; }
    public void setTags(List<TagConfig> tags) { this.tags = tags; }

    public static class InfoConfig {
        private String title;
        private String description;
        private String version;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
    }

    public static class ServerConfig {
        private String url;
        private String description;

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class TagConfig {
        private String name;
        private String description;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    @Bean
    public OpenAPI customOpenAPI() {

        OpenAPI openAPI = new OpenAPI();

        // Carrega Info do YAML
        if (info != null) {
            openAPI.setInfo(new Info()
                    .title(info.getTitle())
                    .description(info.getDescription())
                    .version(info.getVersion()));
        }

        // Carrega Servers do YAML
        if (servers != null) {
            openAPI.setServers(
                    servers.stream()
                            .map(s -> new Server()
                                    .url(s.getUrl())
                                    .description(s.getDescription()))
                            .collect(Collectors.toList())
            );
        }

        // Carrega Tags do YAML
        if (tags != null) {
            openAPI.setTags(
                    tags.stream()
                            .map(t -> new Tag()
                                    .name(t.getName())
                                    .description(t.getDescription()))
                            .collect(Collectors.toList())
            );
        }

        return openAPI;
    }
}

