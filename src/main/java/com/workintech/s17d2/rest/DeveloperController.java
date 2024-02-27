package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.DeveloperFactory;
import com.workintech.s17d2.model.Experience;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private Map<Integer, Developer> developers;
    private Taxable taxable;

    @PostConstruct
    private void init() {
        developers = new HashMap<>();
        developers.put(1, DeveloperFactory.createDeveloper(new Developer(1, "Anıl", 100000d, Experience.SENIOR), taxable));
        developers.put(2, DeveloperFactory.createDeveloper(new Developer(2, "Emrah", 150000d, Experience.SENIOR), taxable));
        developers.put(3, DeveloperFactory.createDeveloper(new Developer(3, "Aytekin", 70000d, Experience.MID), taxable));
    }

    @Autowired
    public DeveloperController(Taxable taxable) {
        this.taxable = taxable;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeveloperResponse save(@RequestBody Developer developer) {
        Developer createdDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        if (Objects.nonNull(createdDeveloper))
            developers.put(createdDeveloper.getId(), createdDeveloper);
        return new DeveloperResponse(createdDeveloper.getId(), createdDeveloper.getName(),
                createdDeveloper.getSalary(), createdDeveloper.getExperience());
    }

    @GetMapping
    public List<DeveloperResponse> getAll() {
        List<DeveloperResponse> developersResponse = new ArrayList<>();
        developers.values().forEach(developer -> developersResponse.add(new DeveloperResponse(developer.getId(), developer.getName(), developer.getSalary(), developer.getExperience())));
        return developersResponse;
    }

    @GetMapping("/{id}")
    public DeveloperResponse get(@PathVariable Integer id) {
        Developer developer = developers.get(id);
        if (Objects.isNull(developer))
            return null;
        return new DeveloperResponse(developer.getId(), developer.getName(), developer.getSalary(), developer.getExperience());
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse delete(@PathVariable Integer id) {
        Developer developer = developers.remove(id);
        if (Objects.isNull(developer))
            return null;
        return new DeveloperResponse(developer.getId(), developer.getName(), developer.getSalary(), developer.getExperience());
    }

    @PutMapping("/{id}")
    public DeveloperResponse update(@PathVariable Integer id, @RequestBody Developer developer) {
        if (Objects.isNull(developer))
            return null;
        Developer foundDeveloper = developers.get(id);
        if (Objects.isNull(foundDeveloper))
            return null;
        Developer updatedDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        developers.put(id, updatedDeveloper);
        return new DeveloperResponse(updatedDeveloper.getId(), updatedDeveloper.getName(), updatedDeveloper.getSalary(), updatedDeveloper.getExperience());

    }
}
