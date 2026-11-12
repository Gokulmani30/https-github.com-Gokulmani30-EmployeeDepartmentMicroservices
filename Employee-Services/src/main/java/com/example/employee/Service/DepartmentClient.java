package com.example.employee.Service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.ResourceAccessException;

@Component
public class DepartmentClient {

    private final RestClient client;

    public DepartmentClient(RestClient departmentRestClient) {
        this.client = departmentRestClient;
    }

    public boolean existsByName(String departmentName) {
        try {
            client.get()
                    .uri("/api/departments/name/{name}", departmentName)
                    .retrieve()
                    .toBodilessEntity();

            return true;

        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode().value() == 404) {
                return false;
            }

            throw ex;

        } catch (ResourceAccessException ex) {
            throw new IllegalStateException(
                    "Cannot connect to Department Service at http://localhost:8082",
                    ex
            );
        }
    }
}