package com.singlero;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/software-engineers")
public class SoftwareEngineerController {

    private final SoftwareEngineerService softwareEngineerService;

    public SoftwareEngineerController (SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping
    public List<SoftwareEngineerDto> getEngineers() {
        return softwareEngineerService.getSoftwareEngineers();
    }

    @PostMapping
    public void addSoftwareEngineer(@RequestBody SoftwareEngineerDto softwareEngineerDto) {
        softwareEngineerService.addSoftwareEngineer(softwareEngineerDto);
    }

    @GetMapping("/{id}")
    public SoftwareEngineerDto getEngineerById(@PathVariable("id") Integer id) {
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEngineerById(@PathVariable("id") Integer id) {
        softwareEngineerService.deleteEngineerById(id);
    }

    @PutMapping("/{id}")
    public void updateEngineerById(@PathVariable("id") Integer id, @RequestBody SoftwareEngineerDto softwareEngineerDto) {
        softwareEngineerService.updateENgineerById(id,softwareEngineerDto);
    }

}
