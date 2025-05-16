package com.singlero;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerService {

    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final AiService aiService;

    public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository, AiService aiService) {
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.aiService = aiService;
    }

    public List<SoftwareEngineerDto> getSoftwareEngineers() {
        return softwareEngineerRepository.findAll().stream().map(softwareEngineer ->
                new SoftwareEngineerDto(
                        softwareEngineer.getName(),
                        softwareEngineer.getTechStack(),
                        softwareEngineer.getRecommendation()
                )).toList();
    }

    public void addSoftwareEngineer(SoftwareEngineerDto softwareEngineerDto) {

        String prompt = """
                Based on the programming tech stack %s that %s has given
                Provide a full learning path and recommendations for this person.
                """.formatted(
                        softwareEngineerDto.techStack(),
                        softwareEngineerDto.name());

        String chatResponse = aiService.chat(prompt);

        softwareEngineerRepository.save(
                new SoftwareEngineer(
                        null,
                        softwareEngineerDto.name(),
                        softwareEngineerDto.techStack(),
                        chatResponse
                ));
    }

    public SoftwareEngineerDto getSoftwareEngineerById(Integer id) {
        SoftwareEngineer softwareEngineer = softwareEngineerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(id + " not found")
        );
        return new SoftwareEngineerDto(softwareEngineer.getName(),softwareEngineer.getTechStack(),softwareEngineer.getRecommendation());
    }

    public void deleteEngineerById(Integer id) {
        boolean exists = softwareEngineerRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException(
                    id + " not found"
            );
        }
        softwareEngineerRepository.deleteById(id);
    }

    public void updateENgineerById(Integer id, SoftwareEngineerDto softwareEngineerDto) {
        SoftwareEngineer softwareEngineer = softwareEngineerRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(id + " not found")
        );
        softwareEngineer.setName(softwareEngineerDto.name());
        softwareEngineer.setTechStack(softwareEngineerDto.techStack());
        softwareEngineerRepository.save(softwareEngineer);
    }
}
