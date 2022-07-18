package com.itransition.courseproject.controller;

import com.itransition.courseproject.dto.request.Request;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.service.CRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


public abstract class CRUDController<
        S extends CRUDService,
        ID,
        C extends Request> {

    protected final S service;

    public CRUDController(S service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<APIResponse> create(@RequestBody @Valid C c) {
        return ResponseEntity.ok(service.create(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> get(@PathVariable ID id){
        return ResponseEntity.ok(service.get(id));
    }


    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<APIResponse> update(@PathVariable ID id, @RequestBody @Valid C c){
        return ResponseEntity.ok(service.update(id, c));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<APIResponse> delete(@PathVariable ID id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping()
    public ResponseEntity<APIResponse> getAll(){
        return ResponseEntity.ok(service.getAll());
    }
}
