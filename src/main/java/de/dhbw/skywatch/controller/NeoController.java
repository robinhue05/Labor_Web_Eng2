package de.dhbw.skywatch.controller;

import de.dhbw.skywatch.dto.TrackedObjectResponse;
import de.dhbw.skywatch.dto.nasa.NeoObject;
import de.dhbw.skywatch.mapper.NeoObjectMapper;
import de.dhbw.skywatch.mapper.TrackedObjectMapper;
import de.dhbw.skywatch.service.NasaNeoService;
import de.dhbw.skywatch.service.TrackedObjectService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// Live asteroid data straight from NASA
@RestController
@RequestMapping("/api/neo")
public class NeoController {

    private final NasaNeoService nasaService;
    private final TrackedObjectService trackedObjectService;

    public NeoController(NasaNeoService nasaService, TrackedObjectService trackedObjectService) {
        this.nasaService = nasaService;
        this.trackedObjectService = trackedObjectService;
    }

    // GET /api/neo/today -> today's asteroids, not saved (so id is null)
    @GetMapping("/today")
    public List<TrackedObjectResponse> today() {
        List<NeoObject> raw = nasaService.fetchObjectsForDate(LocalDate.now());
        return raw.stream()
                .map(NeoObjectMapper::toEntity)
                .map(TrackedObjectMapper::toResponse)
                .toList();
    }

    // GET /api/neo/range?start=2026-10-28&end=2026-10-30 -> max 7 days, otherwise 400
    @GetMapping("/range")
    public List<TrackedObjectResponse> range(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return nasaService.fetchObjectsForRange(start, end).stream()
                .map(NeoObjectMapper::toEntity)
                .map(TrackedObjectMapper::toResponse)
                .toList();
    }

    // POST /api/neo/import -> saves today's asteroids in our DB, returns how many
    // note: calling it twice imports them twice
    @PostMapping("/import")
    public ResponseEntity<Integer> importToday() {
        List<NeoObject> raw = nasaService.fetchObjectsForDate(LocalDate.now());
        raw.stream()
                .map(NeoObjectMapper::toEntity)
                .forEach(trackedObjectService::create);
        return ResponseEntity.ok(raw.size());
    }
}
